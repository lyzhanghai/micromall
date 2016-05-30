app.factory("authorFactory",['$rootScope', function($rootScope) {   //
    return function(o){
        var goUrl = location.origin + $rootScope.prefix + o.href +'?' + angular.param(o.data || {});
        location.href = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa66a636535c987db&redirect_uri='+ encodeURIComponent(goUrl) +'&response_type=code&scope=snsapi_base&state=123#wechat_redirect';
    };
}]);