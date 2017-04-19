'use strict';

const gulp = require('gulp');
const jshint = require('gulp-jshint');
const stylish = require('jshint-stylish');
const inject = require('gulp-inject');
const less = require('gulp-less');
const lesshint = require('gulp-lesshint');
const sourcemaps = require('gulp-sourcemaps');
const concat = require('gulp-concat');
const cleanCSS = require('gulp-clean-css');
const connect = require('gulp-connect');
const proxy = require('http-proxy-middleware');
const wiredep = require('wiredep').stream;
const gulpSequence = require('gulp-sequence');
const es2015 = require('babel-preset-es2015');
const browserify = require('browserify');
const source = require('vinyl-source-stream');
const babelify = require('babelify');
const htmlmin = require('gulp-htmlmin');
const templateCache = require('gulp-angular-templatecache');
const buffer = require('vinyl-buffer');
const uglify = require('gulp-uglify');
const annotate = require('gulp-ng-annotate');
const merge = require('merge-stream');
const useref = require('gulp-useref');
const gulpif = require('gulp-if');

const SRC = {
    entryPoint: './src/app/app.module.js',
    js: './src/app/**/*.js',
    less: './src/app/**/*.less',
    html: './src/app/**/*.html',
    index: './src/app/index.html',
};

const TMP = {
    root: './.tmp/app',
    css: './.tmp/app/styles/**/*.css',
    js: './.tmp/app/scripts/**/*.js',
    cache: './.tmp/app/templates/**/*.js',
    scripts: './.tmp/app/scripts',
    templates: './.tmp/app/templates',
    styles: './.tmp/app/styles',
    index: './.tmp/app/index.html'
};

const DIST = {
    root: './target/dist/app',
};

const WATCHED_FILES = [SRC.js, SRC.less, SRC.html];

gulp.task('default', () => {
    gulp.start('package');
});


gulp.task('package', ['js-lint', 'styles-lint', 'prepare-tmp'], () => {
    return gulp.src(TMP.index)
        .pipe(useref())
        .pipe(gulpif('*.js', uglify()))
        .pipe(gulpif('*.css', cleanCSS()))
        .pipe(gulp.dest(DIST.root));
});

gulp.task('js-lint', () => {
    gulp.src(SRC.js)
        .pipe(jshint({
            esversion: 6
        }))
        .pipe(jshint.reporter(stylish))
        .pipe(jshint.reporter('fail'));
});

gulp.task('styles-lint', () => {
    gulp.src(SRC.less)
        .pipe(lesshint({
            // options
        }))
        .pipe(lesshint.reporter())
        .pipe(lesshint.failOnError());
});

gulp.task('serve', ['prepare-tmp', 'watch'], () => {
    connect.server({
        root: [TMP.root],
        port: 8585,
        livereload: true,
        middleware: (connect) => {
            return [
                connect().use('/bower_components', connect.static('bower_components')),
                proxy('/gear-guide-server/api', {
                    target: 'http://localhost:8080',
                    changeOrigin: true
                })
            ];
        }
    });
});

gulp.task('serve-dist', () => {
    connect.server({
        root: [DIST.root],
        port: 8585,
        livereload: true,
        middleware: () => {
            return [
                proxy('/gear-guide-server/api', {
                    target: 'http://localhost:8080',
                    changeOrigin: true
                })
            ];
        }
    });
});

gulp.task('watch', () => {
    gulp.watch(WATCHED_FILES, ['refresh']);
});

gulp.task('refresh', (callback) => {
    gulpSequence('prepare-tmp', 'reload') (callback);
});

gulp.task('reload', () => {
    gulp.src(WATCHED_FILES)
        .pipe(connect.reload());
});

gulp.task('prepare-tmp', ['js', 'less', 'html'], () => {
    return gulp.src(TMP.index)
        .pipe(wiredep())
        .pipe(inject(gulp.src([TMP.js, TMP.cache, TMP.css], {read: false}), {relative: true}))
        .pipe(gulp.dest(TMP.root));
});

gulp.task('js', () => {
    return browserify(SRC.entryPoint)
        .transform('babelify', {presets: ['es2015']})
        .bundle()
        .on('error', function (err) {
            gutil.log(err.toString());
            this.emit('end')
        })
        .pipe(source('app.js'))
        .pipe(annotate())
        .pipe(gulp.dest(TMP.scripts));
});

gulp.task('less', () => {
    return gulp.src(SRC.less)
        .pipe(sourcemaps.init())
        .pipe(less())
        .pipe(concat('app.css'))
        .pipe(sourcemaps.write('.'))
        .pipe(gulp.dest(TMP.styles));
});

gulp.task('html', () => {
    gulp.src(SRC.index)
        .pipe(gulp.dest(TMP.root));

    gulp.src(SRC.html)
        .pipe(htmlmin({collapseWhitespace: true}))
        .pipe(templateCache('template.cache.js', {
            module: 'app',
            root: 'app'
        }))
        .pipe(gulp.dest(TMP.templates));
});

