/**
 * Created by chenmingkang on 16/3/8.
 */
app.factory('authHttpResponseInterceptor',['$q','$location','$rootScope','$timeout',function($q,$location,$rootScope,$timeout){
    return {
        request: function (config) {
            if(config.url.indexOf('.html') > -1 || config.url.indexOf('json') > -1){
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
            if (typeof response.data === 'object' && response.status != 200) {
                switch (response.data.code){
                    case '-1':
                        messageFactory({text:'请登录'});
                        location.href = response.data.data;
                        break;
                    case '1':
                        messageFactory({text: response.data.msg});
                }
                $rootScope.loading = false;
                return $q.reject(response);
            }
            return response || $q.when(response);
        }
    }
}]);
