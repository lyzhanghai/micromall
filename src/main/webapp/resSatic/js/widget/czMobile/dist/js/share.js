/**
 * Created by chenmingkang on 16/3/1.
 */
;(function(){
    'use strict';
    angular.module('cz-share',[]).factory('shareFactory', ['$rootScope','messageFactory','partnerUrlFactory',function($rootScope,messageFactory,partnerUrlFactory) {
        return function(option){
            var o = {
                title: '', // 分享标题
                desc: '原来国外这么便宜！100%正品保障，抢先下载还能领券哦！', // 分享描述
                link: location.href, // 分享链接
                imgUrl:  partnerUrlFactory.bizChannel ? 'http://m.zhefengle.cn/img/partner/logo.png' : 'http://m.zhefengle.cn/img/logo.png', // 分享图标
                type: '',
                dataUrl: '',
                success: function () {

                },
                cancel: function () {
                    messageFactory({text:'已取消分享'});
                    $scope.$apply();
                }
            };
            angular.extend(o,option);

            if($rootScope.browser.v.weixin){
                wx.ready(function(){
                    wx.onMenuShareAppMessage(o);
                    wx.onMenuShareTimeline(o);
                    wx.onMenuShareQQ(o);
                    wx.onMenuShareWeibo(o);
                });
            }
        };
    }]);
}());