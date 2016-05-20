/**
 * Created by chenmingkang on 16/3/8.
 *
 *  {
 *       name : 路由的名称,
 *       url: 路由的url,
 *
 *       views 里面的参数
 *            uiView : 页面上uiView的名字,嵌套路由或者新路由需要传入
 *            templateUrl : 模版路径,
 *            controller: 控制器
 *       }
 *    }
 */
;(function(app){
    //.state('brandSele', {
    //    url: '/brandSele',   //品牌街
    //    views: {
    //        'appState': {
    //            templateUrl: 'brandSele.html',
    //            controller: "brandSele"
    //        }
    //    }
    //})

    var route = [
        {name : 'index', url: '/indexMain.html',templateUrl:'view/indexMain.html', controller: "indexCtr",tabSelectIndex : 1},
        {name : 'goodsList', url: '/goodsList.html?:{searchText || categoryId}',templateUrl:'view/goodsList.html', controller: "goodsListCtr",tabSelectIndex : 2},
        {name : 'detail', url: '/detail.html?:goodsId',templateUrl:'view/detail.html', controller: "detailCtr",tabSelectIndex : 2},
        {name : 'userInfo/info', url: '/userContent/info.html',templateUrl:'view/userContent/info.html', controller: "userInfoCtr",tabSelectIndex : 3},
        {name : 'userInfo/collect', url: '/userContent/collect.html',templateUrl:'view/userContent/collect.html', controller: "userCollectCtr",tabSelectIndex : 3},
        {name : 'userInfo/setUp', url: '/userContent/setUp.html',templateUrl:'view/userContent/setUp.html', controller: "",tabSelectIndex : 3},
        {name : 'userInfo/setInfo', url: '/userContent/setUp/setInfo.html',templateUrl:'view/userContent/setUp/setInfo.html', controller: "setInfoCtr",tabSelectIndex : 3},
        {name : 'userInfo/siteList', url: '/userContent/site/list.html',templateUrl:'view/userContent/site/siteList.html', controller: "siteListCtr",tabSelectIndex : 3},
        {name : 'userInfo/siteAddEdit', url: '/userContent/site/addEdit.html?:{type || id}',templateUrl:'view/userContent/site/siteAddEdit.html', controller: "siteAddEditCtr",tabSelectIndex : 3}

    ];

    app.config(["$stateProvider","$urlRouterProvider","$httpProvider","lazyImgConfigProvider","czContentForConfigProvider",function($stateProvider, $urlRouterProvider,$httpProvider,lazyImgConfigProvider,czContentForConfigProvider) {

        lazyImgConfigProvider.setOptions({
            startClass: 'startImg',
            successClass: 'loadSuccessImg'
        });
        //
        // czContentForConfigProvider.setOptions({
        //     czYieldHideBeforeCallback : function(scope,element){
        //         if(browser.v.zhefengle){
        //             element.remove();
        //         }
        //     }
        // });

        $httpProvider.interceptors.push('authHttpResponseInterceptor');

        //$httpProvider.defaults.withCredentials = true;
        $httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
        $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
        $httpProvider.defaults.headers.common['Accept'] = 'application/json, text/plain, */*';
        // $httpProvider.defaults.headers.common['PAJAX'] = 'true';
        //$httpProvider.defaults.headers.common['DNT'] = '1';

        $httpProvider.defaults.transformRequest = [function(data) {
            /**
             * The workhorse; converts an object to x-www-form-urlencoded serialization.
             * @param {Object} obj
             * @return {String}
             */
            return angular.isObject(data) && String(data) !== '[object File]' ? angular.param(data) : data;
        }];

        route.forEach(function(item){
            var params = {
                name : item.name,
                url: item.url,
                tabSelectIndex : item.tabSelectIndex,
                views : {}
            };
            params.views[item.uiView || 'appState'] = {
                templateUrl : item.templateUrl,
                controller : item.controller
            };
            $stateProvider.state(params);
        });

        $urlRouterProvider.otherwise('/indexMain.html');

    }]);
})(app);

