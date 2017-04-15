'use strict';

const gulp = require('gulp');
const webserver = require('gulp-webserver');
const jshint = require('gulp-jshint');
const stylish = require('jshint-stylish');
const inject = require('gulp-inject');
const less = require('gulp-less');
const lesshint = require('gulp-lesshint');
const angularFilesort = require('gulp-angular-filesort');
const mainBowerFiles = require('main-bower-files');
const sourcemaps = require('gulp-sourcemaps');
const concat = require('gulp-concat');
const cleanCSS = require('gulp-clean-css');
const gutil = require('gulp-util');
const connect = require('gulp-connect');
const copy = require('gulp-copy');
const gulpWatch = require('gulp-watch');
const wiredep = require('wiredep').stream;

const JS_PATTERN = './src/app/**/*.js';
const LESS_PATTERN = './src/app/**/*.less';
const HTML_PATTERN = './src/app/**/*.html';

const WATCHED_FILES = new Array(JS_PATTERN, LESS_PATTERN, HTML_PATTERN);

const CSS_PATTERN = './src/app/styles/**/*.css';

const INDEX_HTML = './src/app/index.html';
const APP_DIR = './src/app';
const STYLES_DIR = './src/app/styles';

const DIST = '.tmp/app';

let config = {
    production: !!gutil.env.production
};


gulp.task('copy-dist', function() {
    gulp.src(APP_DIR + '/**/*', {base: APP_DIR})
        .pipe(gulpWatch(APP_DIR, {base: APP_DIR}))
        .pipe(gulp.dest(DIST));
});

gulp.task('default', () => {
    gulp.start('serve');
});

gulp.task('js-lint', () => {
    gulp.src(JS_PATTERN)
        .pipe(jshint())
        .pipe(jshint.reporter(stylish))
        .pipe(jshint.reporter('fail'));
});

gulp.task('styles-lint', () => {
    gulp.src(LESS_PATTERN)
        .pipe(lesshint({
            // options
        }))
        .pipe(lesshint.reporter())
        .pipe(lesshint.failOnError());
});

gulp.task('serve', ['inject', 'watch'], () => {
    connect.server({
        root: [APP_DIR],
        port: 8585,
        livereload: true,
        middleware: function(connect) {
            return [connect().use('/bower_components', connect.static('bower_components'))];
        }
    });
});

gulp.task('watch', () => {
    gulp.watch(WATCHED_FILES, ['compile-less', 'inject', 'copy-dist', 'reload']);
});

gulp.task('reload', () => {
    gulp.src(WATCHED_FILES)
        .pipe(connect.reload());
});

gulp.task('inject', ['compile-less'], () => {
    gulp.src(INDEX_HTML)
        .pipe(inject(gulp.src(mainBowerFiles(), {read: false}), {name: 'bower', relative: true}))
        //.pipe(wiredep())
        .pipe(inject(gulp.src([CSS_PATTERN, JS_PATTERN], {read: false}), {relative: true})) //don't use read option with angularFileSort()
        .pipe(gulp.dest(APP_DIR));
});

gulp.task('compile-less', () => {
    gulp.src(LESS_PATTERN)
        .pipe(sourcemaps.init())
        .pipe(less())
        .pipe(concat('main.css'))
        .pipe(config.production ? cleanCSS() : gutil.noop())
        .pipe(sourcemaps.write('.'))
        .pipe(gulp.dest(STYLES_DIR));
});

