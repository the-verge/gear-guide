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
const gulpSequence = require('gulp-sequence');
const traceur = require('gulp-traceur');
const babel = require('gulp-babel');

const APP_DIR = './src/app';
const DIST_DIR = './.tmp/app';
const STYLES_DIR = './.tmp/app/styles';

const APP = {
    index: APP_DIR + '/index.html',
    html: APP_DIR + '/**/*.html',
    js: APP_DIR + '/**/*.js',
    less: APP_DIR + '/**/*.less',
    css: APP_DIR + '/styles/**/*.css'
};

const DIST = {
    index: DIST_DIR + '/index.html',
    html: DIST_DIR + '/**/*.html',
    js: DIST_DIR + '/**/*.js',
    less: DIST_DIR + '/**/*.less',
    css: DIST_DIR + '/styles/**/*.css'
};

const WATCHED_FILES = new Array(APP.js, APP.less, '!./src/app/styles', APP.html);

let config = {
    production: !!gutil.env.production
};

gulp.task('default', () => {
    gulp.start('package');
});

gulp.task('package', () => {
    console.log('packaging...');
});

gulp.task('js-lint', () => {
    gulp.src(APP.js)
        .pipe(jshint())
        .pipe(jshint.reporter(stylish))
        .pipe(jshint.reporter('fail'));
});

gulp.task('styles-lint', () => {
    gulp.src(APP.less)
        .pipe(lesshint({
            // options
        }))
        .pipe(lesshint.reporter())
        .pipe(lesshint.failOnError());
});

gulp.task('serve', ['prepare-dist', 'watch'], () => {
    connect.server({
        root: [DIST_DIR],
        port: 8585,
        livereload: true,
        middleware: function(connect) {
            return [connect().use('/bower_components', connect.static('bower_components'))];
        }
    });
});

gulp.task('watch', () => {
    gulp.watch(WATCHED_FILES, ['refresh']);
});

gulp.task('reload', () => {
    gulp.src(WATCHED_FILES)
        .pipe(connect.reload());
});

gulp.task('inject', ['compile-less'], () => {
    return gulp.src(DIST.index)
        .pipe(inject(gulp.src(mainBowerFiles(), {read: false}), {name: 'bower', relative: true}))
        //.pipe(wiredep())
        .pipe(inject(gulp.src([DIST.js, DIST.css], {read: false}), {relative: true})) //don't use read option with angularFileSort()
        .pipe(gulp.dest(DIST_DIR));
});

gulp.task('compile-less', () => {
    gulp.src(APP.less)
        .pipe(sourcemaps.init())
        .pipe(less())
        .pipe(concat('main.css'))
        .pipe(config.production ? cleanCSS() : gutil.noop())
        .pipe(sourcemaps.write('.'))
        .pipe(gulp.dest(STYLES_DIR));
});

gulp.task('refresh', (callback) => {
    gulpSequence('prepare-dist', 'reload') (callback);
});

gulp.task('prepare-dist', (callback) => {
    gulpSequence('copy-dist', 'transpile', 'inject') (callback);
});

gulp.task('copy-dist', () => {
    return gulp.src(APP_DIR + '/**/*', {base: APP_DIR})
        //.pipe(gulpWatch(APP_DIR, {base: APP_DIR}))
        .pipe(gulp.dest(DIST_DIR));
});

gulp.task('transpile', () => {
    return gulp.src(DIST.js)
        .pipe(traceur())
        .pipe(gulp.dest(DIST_DIR));
});
