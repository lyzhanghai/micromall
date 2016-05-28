/**
 * Created by chenmingkang on 16/3/1.
 */
;(function(){
    'use strict';
    angular.module('cz-countdown',[]).directive('countdown', ['$rootScope', '$window', '$timeout','$document',function($rootScope, $window, $timeout,$document) {
        return {
            restrict : 'EA',
            scope:{
                countdownData:'='
            },
            link : function(scope, elm, attrs) {
                var elmText = elm.attr('countdown-end') || '商品已下架';
                var elmTextStart = elm.attr('countdown-start') || '';
                var time;
                scope.$on('$destroy', function(){
                    clearInterval(time);
                });
                time = setInterval(function(){
                    if(!!scope.countdownData){
                        var date = new Date(new Date(scope.countdownData.replace(/-/g, '/')).getTime() - new Date().getTime());
                        var getDate = date / (60000*60) /24;
                        var getHours = (getDate - Math.floor(getDate)) * 24;
                        var getMinutes = (getHours - Math.floor(getHours)) * 60;
                        var getSeconds = Math.floor((getMinutes - Math.floor(getMinutes)) * 60);

                        var getDateText = getDate > 1 ? Math.floor(getDate) + '天' : '';
                        var getHoursText =getHours > 1 ?  Math.floor(getDate) + '时' : '';

                        if(getDate <= 0){
                            elm.html(elmText);
                            clearInterval(time);
                        }else{
                            elm.html(elmTextStart + getDateText + getHoursText + Math.floor(getMinutes) + '分' + getSeconds + '秒');
                        }
                    }
                },1000)
            }
        }
    }]);
}());
