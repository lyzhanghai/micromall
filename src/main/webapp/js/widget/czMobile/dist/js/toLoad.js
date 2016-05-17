/**
 * Created by chenmingkang on 16/3/1.
 */
;(function(){
    'use strict';
    angular.module('cz-to-load',[]).directive('topLoad',['$rootScope','$document','$window',function($rootScope,$document,$window) {
        return function(scope, elm, attr) {
            if($rootScope.browser.v.weixin){    //判断是否QQ浏览器
                return;
            }
            var touchStart = 0;
            var touchEnd = 0;
            var touchStartX = 0;
            var touchEndX = 0;
            var time;
            var scroll = window.scrollY;
            var windowHeight = $window.innerHeight;
            var upLoad = -50;     //上拉的距离到一定距离在显示下拉刷新
            var unclaspLoad = -80;     //上拉的距离到一定距离在现实松开刷新
            var elmTop = function(elm,start){
                if(touchEnd< upLoad && start == 'move'){
                    elm.css({
                        'margin-top': -(touchEnd / 4) + 'px'
                    });
                    $document.find('body').css({
                        'overflow': 'hidden',
                        height: windowHeight + 'px',
                        '-webkit-overflow-scrolling': 'hidden'
                    })
                }else{
                    elm.css({
                        'margin-top': 0
                    });
                    $document.find('body').css({
                        'overflow-y':'auto',
                        height:'auto',
                        '-webkit-overflow-scrolling': 'touch'
                    })
                }
            };
            elm.bind('touchmove', function(evt) {
                scope.$apply(function() {
                    clearTimeout(time)
                    var event = evt.changedTouches[0];
                    scroll = window.scrollY;
                    if(Math.abs(touchEndX) > 10){   //判断是否侧滑往下啦并且左右滑动，如果是的话，我就让他还原，否则左右还是可以促发下面事件
                        if($rootScope.browser.v.ios){    //在跟左滑又滑一起用的时候有个bug
                            elm.css({
                                '-webkit-transform': ''
                            })
                        }
                        return;
                    }
                    if(scroll == 0 && scope.asyns == false){   //滚动条是0   必须是数据返回回来之后在执行
                        if(!touchStart){   //判断是不是第一次
                            touchStart = event.pageY;
                            touchStartX = event.pageX;
                            return;
                        }
                        touchEnd = touchStart - event.pageY;
                        touchEndX = touchStartX - event.pageX;
                        if(touchEnd > 0){   //如果小于0 代表是往上拉了
                            return;
                        }
                        if(parseInt(elm.css('margin-top')) > 0 && parseInt(elm.css('margin-top')) > upLoad){  //小于这个再给他拉回去
                            scope.upmove = false;
                            scope.loadmove = false;
                            elm.css({
                                'margin-top': -(touchEnd / 4) + 'px'
                            });
                        }
                        if(touchEnd < upLoad) {   //到一定高度在给他下啦
                            if (touchEnd > unclaspLoad) {
                                scope.upmove = true;
                                scope.loadmove = false;
                            } else {
                                scope.upmove = false;
                                scope.loadmove = true;
                            }
                            elmTop(elm,'move');
                        }
                    }
                });
            });

            elm.bind('touchend', function(evt) {
                scope.$apply(function() {
                    scroll = window.scrollY;
                    scope.loadmove = false;
                    scope.upmove = false;
                    elmTop(elm,'end');
                    if(scroll == 0 && scope.asyns == false) {
                        if (touchEnd < 0 && touchEnd <= unclaspLoad) {   //滚动条是0   touchEnd下啦距离要下拉到70高度
                            scope.$eval(attr.topLoad);
                        }
                    }
                    touchStart = 0;
                    touchEnd = 0;
                    touchStartX = 0;
                    touchEndX = 0;
                });
            });

        };
    }]);
}());
