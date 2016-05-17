/**
 * Created by chenmingkang on 16/3/8.
 */
app.factory('authHttpResponseInterceptor',['$q','$location','$rootScope','$timeout',function($q,$location,$rootScope,$timeout){
    return {
        request: function (config) {
            if(config.url.indexOf('-') > -1){
                return config;
            }
            if(config.method == 'GET'){
                config.params = config.params || {};
                config.params.debugAuth = 'debugAuth';
                config.params['PAJAX'] = 'true';

            }else{
                config.data = config.data || {};
                config.data.debugAuth = 'debugAuth';
                config.data['PAJAX'] = 'true';
            }
            return config;
        },
        response: function(response){
            // if (typeof response.data === 'object' && (response.status != 200 || response.data.code != 'success')) {
            //
            //     if (response.data.code === 'ERR_TOKEN_EXPIRED') {
            //         if(response.config.headers.hash === location.hash){       //response.config.headers.hash可能会是上一个路由的sevice,所以校验是否相当
            //             messageFactory({text:'请登录'});
            //             location.href = $rootScope.prefix + 'user/login';
            //         }
            //     }else{
            //         messageFactory({text:response.data.message});
            //     }
            //     $rootScope.loading = false;
            //     return $q.reject(response);
            // }
            return response || $q.when(response);
        }
    }
}]);
