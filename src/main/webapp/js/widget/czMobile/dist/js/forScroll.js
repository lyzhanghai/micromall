/**
 * Created by chenmingkang on 16/3/1.
 */
;(function(){
    'use strict';

    angular.module('cz-for-scroll',[]).factory('forScrollListFactory',function(){
        function List(data){
            this.listData = data;
        };

        List.prototype.getFirstData = function(){
            return this.listData[0];
        };

        List.prototype.getLastData = function(){
            return this.listData[this.listData.length];
        };

        List.prototype.getData = function(){
            return this.listData;
        };

        return function(data){
            return new List(data);
        }
    }).directive('forScroll',["$timeout","forScrollListFactory",function($timeout,forScrollListFactory) {
        return {
            restrict: 'EA',
            scope:{
                'forScrollData' : '=',
                'forScrollTargetData' : '='
            },
            link : function(scope, elm, attrs) {
                $timeout(function(){
                    var elm2Html = elm[0].outerHTML,
                        elm2,
                        elmParent = elm.parent();

                    //elm.parent().append(elm2Html);

                    //elm2 = elm[0].nextElementSibling;

                    //elm2.css({top:elmParent[0].offsetHeight});

                    //function marquee(){ // 若第一遍的内容已全部显示完毕，则重新开始显示
                    //    var top = elm2.attr('top');
                    //    elm2.css({
                    //        top: top + 1
                    //    });
                    //    if(top > elmParent[0].offsetHeight + elm2){
                    //
                    //    }
                    //}

                    function marquee(){ // 若第一遍的内容已全部显示完毕，则重新开始显示
                        if(elmParent[0].scrollHeight - (elmParent[0].scrollTop + elmParent[0].offsetHeight) <= 0) {
                            elmParent[0].scrollTop = 0;
                        }
                        else {
                            elmParent[0].scrollTop++;
                        } // 否则向上滚动1个像素的量。
                    }

                    var myMarquee = setInterval(marquee,50); // 按一定的速度滚动
                },0);

                //var time,
                //    position = attrs.forPosition || 'top',
                //    second = attrs.forScrollTime * 1000,
                //    listFn = forScrollListFactory(scope.forScrollTargetData);
                //
                //function reformData(){
                //    var shiftData,lastTargetData;
                //    if(position == 'top'){
                //        shiftData = scope.forScrollData.shift();
                //        lastTargetData = scope.forScrollTargetData.pop();
                //
                //        scope.forScrollTargetData.unshift(shiftData);
                //        scope.forScrollData.push(lastTargetData);
                //    }else{
                //        shiftData = scope.forScrollData.pop();
                //        lastTargetData = scope.forScrollTargetData.shift();
                //
                //        scope.forScrollTargetData.push(shiftData);
                //        scope.forScrollData.unshift(lastTargetData);
                //    }
                //    timeFn();
                //}
                //
                //function timeFn(){
                //    time = $timeout(function(){
                //        if(attrs.forScrollSuccess){
                //            scope.$parent.$eval(attrs.forScrollSuccess)(listFn.getFirstData(),reformData)
                //        }else{
                //            reformData();
                //        }
                //    },second);
                //};
                //
                //timeFn();
                //
                //scope.$on('$destroy',function(){
                //    $timeout.cancel(time);
                //});
            }
        }
    }]);
}());
