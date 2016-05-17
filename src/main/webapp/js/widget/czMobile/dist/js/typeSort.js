/**
 * Created by chenmingkang on 16/3/1.
 */

;(function(){
    'use strict';

    angular.module('cz-type-sort',[])
        .directive('typeSort', ['$window', '$timeout','$compile',function($window, $timeout,$compile) {
            function getEleUl(){
                return document.getElementById('type-sort-text');
            }
            return {
                restrict : 'EA',
                replace : false,
                link : function(scope, elm, attrs) {
                    $timeout(function(){
                        var itemChid;
                        var typeDiv = document.getElementsByClassName("hot-title");
                        var forTargetDiv = document.getElementById(elm.attr('for-target-id'));

                        var template = '<div class="type-sort-text {{typeSortTexe ? \'show\' : \'\'}}" id="type-sort-text"><div class="bj"></div><h1 class="text">{{typeSortTexe}}</h1></div>';
                        var getElm = getEleUl();
                        if(!getElm){
                            angular.element(document.getElementsByTagName('body')).append($compile(template)(scope));
                            getElm = getEleUl();
                            scope.$apply();
                        }

                        var move = function(evt,model){
                            scope.$apply(function(){
                                var offsetTop = parseInt(elm.attr('offset-top') || elm[0].offsetTop) || 0;
                                var parOffsetTop = parseInt(elm.attr('par-offset-top')) || 0;
                                var elmLi = elm.find("li");
                                var evtY = 0;

                                if(model == 'end'){
                                    scope.typeSortTexe = '';
                                    evt.preventDefault();
                                    return false;
                                }

                                evtY = (evt.y || evt.touches[0].clientY) + parOffsetTop;  //toucheEnd的时候evt.y || evt.touches[0].clientY都不存在

                                angular.forEach(elmLi,function(item,index){
                                    if((evtY - offsetTop >= item.offsetTop) && evtY <= item.offsetTop + evtY){   //算出他距离顶部的距离＋上高度
                                        itemChid = item;
                                    }
                                });
                                //if((evt.y || evt.touches[0]) < itemChid.offsetHeight + itemChid.offsetTop){
                                angular.forEach(typeDiv,function(item,index){
                                    if(item.textContent == itemChid.textContent){
                                        if(item.offsetTop > 0 || index == 0){
                                            if(forTargetDiv){
                                                forTargetDiv.scrollTop = item.offsetTop
                                            }else{
                                                $window.scrollTo(0,item.offsetTop);
                                            }
                                        }
                                        scope.typeSortTexe = itemChid.textContent;
                                    }
                                });
                                //}
                                evt.preventDefault();
                            });
                        };

                        scope.$on('$destroy', function(){
                            elm.off();
                            angular.element(getElm).remove();
                            getElm = null;
                            scope.typeSortTexe = '';
                        });

                        elm.bind('touchstart', function(evt) {
                            move(evt,'start')
                        }).bind('click', function(evt) {
                            move(evt,'start')
                        }).bind('touchmove', function(evt) {
                            move(evt,'move')
                        }).bind('mousemove', function(evt) {
                            move(evt,'move')
                        }).bind('touchend', function(evt) {
                            move(evt,'end')
                        }).bind('mouseout', function(evt) {
                            move(evt,'end')
                        });
                    },0)
                }
            }
        }]);
}());
