/**
 * Created by chenmingkang on 16/3/1.
 */

;(function() {
    'use strict';
    angular.module('cz-mobile', [
        'cz-bottom-scroll',      //滚动加载
        'cz-countdown',          //倒计时
        'cz-back-top',           //返回顶部
        'cz-form-validate',      //表单校验
        'cz-mask',               //dialog 之类弹层,添加背景上,禁用滚动条
        'cz-content-for',        //头部,只需要改变文字,或者隐藏.

        'cz-message',            //消息提示
        'cz-share',              //分享三端,微信,QQ,微博
        'cz-confirm',            //确定,取消弹出框
        'cz-cache',              //数据缓存-可以缓存类似service层的数据
        'cz-cookie',             //cookie
        'cz-util'            //节点流
    ]).run(['$rootScope',function($rootScope){
        $rootScope.bodyInfo = bodyInfo();

        function bodyInfo(){
            return{
                width : document.body.clientWidth < 750 ? document.body.clientWidth : 750,
                height : document.documentElement.clientHeight > 0 ? document.documentElement.clientHeight : window.innerHeight
            };
        };

    }]);

    angular.module('cz-util', [
        'cz-throttle'            //节点流
    ]);
}());