var gulp = require('gulp');

// 引入组件
var jshint = require('gulp-jshint');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var rename = require('gulp-rename');
var minifyCss = require('gulp-minify-css');
var templateCache = require('gulp-angular-templatecache');


var gulpFlieInitConfig = function(){
    var minPath = './webapp/resSatic/resMin/',
        static = './webapp/resSatic/';

    var jsPath = static + 'js/';
    var jsLibsPath = jsPath + 'libs/';
    var jsWidgetPath = jsPath + 'widget/';


    var jsMinPath = minPath + 'js/';


    var libsMix = [
        jsLibsPath + 'angular.js',
        jsLibsPath + '**/**.js',

        '!' + jsWidgetPath + 'czMobile/{dist,GulpFile.js,GruntFile.js}',
        jsWidgetPath + '**/*.js',
        jsWidgetPath + 'czMobile/src/js/czMobile.all.js'
    ];

    return{
        lint : function(){
            gulp.src(jsPath)
                .pipe(jshint())
                .pipe(jshint.reporter('default'));
        },
        templates : function () {
            gulp.src(static + 'view/**/**/*.html')
                .pipe(templateCache('templates.js',{
                    root : '/view/',
                    module: "app"
                }))
                .pipe(gulp.dest(jsMinPath))
                .pipe(rename('templates.all.min.js'))
                .pipe(uglify())
                .pipe(gulp.dest(jsMinPath));
        },
        scripts : function() {
            gulp.src(libsMix)                       //基础库
                .pipe(concat('libs.all.js'))
                .pipe(gulp.dest(jsMinPath + 'libs/'))
                .pipe(rename('libs.all.min.js'))
                .pipe(uglify())
                .pipe(gulp.dest(jsMinPath + 'libs/'));


            gulp.src([    //每个模块的model+controller
                    jsPath + 'httpPath.js',
                    jsPath + 'app.js',
                    jsPath + 'controller.js',
                    jsPath + 'directive.js',
                    jsPath + 'factory.js',
                    jsPath + 'filter.js',
                    jsPath + 'run/**/**.js',
                    jsPath + 'model/**/**/**/**/**.js'
                ])
                .pipe(concat('model.all.js'))
                .pipe(gulp.dest(jsMinPath))
                .pipe(rename('model.all.min.js'))
                .pipe(uglify())
                .pipe(gulp.dest(jsMinPath));
        },
        minicss : function () {
            var cssPath = static + 'css/';
            var cssMinPath = minPath + 'css/';
            gulp.src([
                    cssPath + 'iconfont.css',
                    jsWidgetPath + '**/**/**.css',
                    jsLibsPath + '**/**/**.css',
                    cssPath + 'theme.css'
                ])
                .pipe(concat('all.css'))
                .pipe(gulp.dest(cssMinPath))
                .pipe(rename('all.min.css'))
                .pipe(minifyCss())
                .pipe(gulp.dest(cssMinPath));
        },
        copy : function(){
            var jsMinLibsPath = jsMinPath + 'libs/';
            gulp.src(static + 'images/**/**').pipe(gulp.dest(minPath + 'images'));
            gulp.src(static + 'font/**').pipe(gulp.dest(minPath + 'font'));
            gulp.src(jsLibsPath + 'imgJsLibs/**/**').pipe(gulp.dest(jsMinLibsPath + 'imgJsLibs'));
        }
    }
}();


for(var i in gulpFlieInitConfig){
    gulp.task(i,gulpFlieInitConfig[i]);
}

// 默认任务
gulp.task('default', function(){
    gulp.run('lint','templates', 'scripts', 'minicss','copy');

    // // 监听文件变化
    // gulp.watch('./js/*.js', function(){
    //     gulp.run('lint', 'sass', 'scripts');
    // });
});

