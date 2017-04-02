'use strict';

var gulp = require('gulp');
var webserver = require('gulp-webserver');
var jshint = require('gulp-jshint');
var stylish = require('jshint-stylish');
var minify = require('gulp-minify');

gulp.task('default', function() {
    gulp.start('serve');
});

gulp.task('build', ['minify'], function () {

});

gulp.task('lint', function () {
    return gulp.src('./src/app/**/*.js')
        .pipe(jshint())
        .pipe(jshint.reporter(stylish))
        .pipe(jshint.reporter('fail'));
});

gulp.task('minify', ['lint'], function() {
    gulp.src('./src/app/**/*.js')
        .pipe(minify({
            ext:{
                noSource: true,
                min:'-min.js'
            },
            exclude: [],
            ignoreFiles: []
        }))
        .pipe(gulp.dest('dist'))
});

gulp.task('serve', function() {
    gulp.src('./src/app')
        .pipe(webserver({
            host: '0.0.0.0',
            port: 8585,
            livereload: true,
            fallback: 'index.html',
            open: true
        }));
});
