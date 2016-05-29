/**
 * Created by chenmingkang on 16/3/8.
 */
app.factory('authHttpResponseInterceptor',['$q','$location','$rootScope','$injector',function($q,$location,$rootScope,$injector){
    var responseLoad = false;

    function resFn(){
        responseLoad = true;
        $rootScope.loading = false;
    }

    return {
        request: function (config) {
            var config = config || {};
            config.timeout = 10000;
            config.load = config.load || true;
            responseLoad = false;

            if(config.url.indexOf('.html') > -1 || config.url.indexOf('json') > -1){
                return config;
            }
            if (config.load && !responseLoad){
                $rootScope.loading = true;
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
            var messageFn = $injector.get('messageFactory');
            if (typeof response.data === 'object' && response.status != 200) {
                switch (response.data.code){
                    case '-1':
                        messageFn({text:'请登录'});
                        location.href = response.data.data;
                        break;
                    case '1':
                        messageFn({text: response.data.msg});
                }
                resFn();
                return $q.reject(response);
            }
            resFn();
            return response || $q.when(response);
        },
        responseError: function(err){
            var messageFn = $injector.get('messageFactory');
            resFn();
            messageFn({text : '服务器出错请求出错'});
            return $q.reject(err);
        }
    }
}]);
