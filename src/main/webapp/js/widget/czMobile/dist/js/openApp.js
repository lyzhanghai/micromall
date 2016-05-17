/**
 * Created by chenmingkang on 16/3/1.
 */
;(function(){
    'use strict';
    angular.module('cz-open-app',[]).factory('openAppFactory',['$rootScope','confirmFactory',function($rootScope,confirmFactory) {
        var indexUrl = 'zhefengle:m.zhefengle.cn';
        var scope = $rootScope.$new();
        var option = {};
        var ua = navigator.userAgent,
            loadIframe,
            win = window;

        var confirm = {  //设置微信里面跳转APP信息
            text:'亲，请选择要去的地方',
            closeText:'去下载',
            goText:'去app',
            option:{
                close:function(){
                    location.href = option.wxAndroidUrl;
                },
                go:function(){
                    var goHref = encodeURIComponent(option.appurl);
                    location.href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa6ddfd2bf5f24af7&redirect_uri='+ goHref +'&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect';
                }
            }
        };
        var init = function(){
            option = {
                appurl : indexUrl,
                wxAndroidUrl : "http://a.app.qq.com/o/simple.jsp?pkgname=com.vanwell.module.zhefengle.app",
                androidUrl :  "http://7u2fm4.com1.z0.glb.clouddn.com/084131b4-dc0f-46e0-8bf6-b7cbc6779bda.apk"
            };
        };
        init();

        scope.$on('$destroy', function(){
            init();  //路由跳转都默认设置回去
        });

        function getIntentIframe(){
            if(!loadIframe){
                var iframe = document.createElement("iframe");
                iframe.style.cssText = "display:none;width:0px;height:0px;";
                document.body.appendChild(iframe);
                loadIframe = iframe;
            }
            return loadIframe;
        }

        function getChromeIntent(url){
            return  url;
        }
        var appInstall = {
            isChrome:ua.match(/Chrome\/([\d.]+)/) || ua.match(/CriOS\/([\d.]+)/),
            isAndroid:ua.match(/(Android);?[\s\/]+([\d.]+)?/),
            timeout:500,
            /**
             * 尝试跳转appurl,如果跳转失败，进入h5url   //appurl于后台对接好的接口,//h5url 没有的时候去下载页面   //wxurl 微信专属跳到应用宝页面
             */
            open:function(){
                var t = Date.now();
                appInstall.openApp(option.appurl);
                setTimeout(function(){
                    if(Date.now() - t < appInstall.timeout+100){
                        appInstall.openH5();
                    }
                },appInstall.timeout)
            },
            openApp:function(){
                if(appInstall.isChrome){
                    if(appInstall.isAndroid){
                        win.location.href = getChromeIntent(option.appurl);
                    }else{
                        win.location.href = option.appurl;
                    }
                }else{
                    getIntentIframe().src = option.appurl;
                }
            },
            openH5:function(){
                win.location.href = option.wxAndroidUrl;
            }
        };


        function setOption(o){
            angular.extend(option,o);
        };

        function open(){

            if($rootScope.browser.v.weixin){
                if(option.appurl == indexUrl){
                    location.href = option.wxAndroidUrl;
                    return;
                }
                confirmFactory(confirm);
            }else{
                if(($rootScope.browser.v.iPhone && $rootScope.browser.v.QQ) || ($rootScope.browser.v.webApp) || ($rootScope.browser.v.weibo)){
                    //messageFactory({text:'该浏览器不支持，请在其他浏览器打开'});
                    location.href = option.wxAndroidUrl;   //跳应用宝
                }else{
                    appInstall.open();
                }
            }
        };

        return function(){
            return{
                setOption : setOption,
                open : open
            }
        }

    }]);
}());
