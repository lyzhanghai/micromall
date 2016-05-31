/*后台发布静态替换文件*/
var servicePath = 'http://www.u-non.com/api/';


var app = angular.module('app', [
    'ui.router',
    'ngSanitize',     //把ng-bind-html之类模版指令拆出来了
    'angularLazyImg',
    'angular-carousel',
    'ngFileUpload',
    'ngModal',
    'china-area-selector',
    // 'china-area-selector',
    // 'angularDateBinder',
    'cz-mobile'
],['$compileProvider','$locationProvider',function($compileProvider,$locationProvider){
    $locationProvider.html5Mode(true).hashPrefix('!');
    $compileProvider.aHrefSanitizationWhitelist(/^\s*((https?|ftp|tel|sms|mailto|file|javascript|chrome-extension):)|#/);
    //$compileProvider.urlSanitizationWhitelist()  //angular1.2以前

}]);

//Main
app.controller('mainController',["$scope","$rootScope","userInfoService", function($scope,$rootScope,userInfoService) {
    $rootScope.loading = false;  //加载

    $rootScope.prefix = "/";

    $rootScope.userInfoData = {};

    userInfoService.userInfo(function(data){
        $rootScope.userInfoData = data.data;
    });

}]);

app.directive('back',['$timeout',function($timeout){
    return {
        restrict : 'EA',
        link : function(scope, elm, attrs) {
            $timeout(function(){
                scope.$on('$destroy', function(){
                    elm.off();
                });

                elm.bind('click',function(){
                    window.history.go(-1);
                    console.log(1)
                });
            },0);
        }
    }
}]);
app.factory("authorFactory",['$rootScope', function($rootScope) {   //
    return function(o){
        var goUrl = location.origin + $rootScope.prefix + o.href +'?' + angular.param(o.data || {});
        location.href = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa66a636535c987db&redirect_uri='+ encodeURIComponent(goUrl) +'&response_type=code&scope=snsapi_base&state=123#wechat_redirect';
    };
}]);
app.filter("dateDay", function() {
    return function(date){
        var currentDates = new Date().getTime() - new Date(date).getTime(),
            currentDay = parseInt(currentDates / (60000*60) -1) //减去1小时
        if(currentDay >= 24*3){
            var datas = new Date(date);
            currentDay = datas.getFullYear() + '-' + (datas.getMonth()+1) + '-' + datas.getDate();
        }else if(currentDay >= 24){
            currentDay = parseInt(currentDay / 24) + "天前";
        }else if(currentDay == 0 ){
            var currentD = parseInt(currentDates / 60000);
            if(currentD >= 60){
                currentDay = "1小时前"
            }else{
                currentDay = currentD + "分钟前"
            }
        }else{
            currentDay = currentDay + "小时前"
        }

        return currentDay
    };
}).filter("cut", function() {
    var i = 0;
    var text = '';
    var returns = true;
    return function(date,findIndex){
        angular.forEach(date,function(item,index){
            if(returns){
                if(item === ';'){
                    if(i === findIndex){
                        returns = false;
                    }else{
                        i++;
                    }
                    return;
                }
                if(i >= findIndex){
                    text += item;
                }
            }
        });
        return text;
    };
}).filter("encodeUrl", function() {
    return function(data){
        return encodeURIComponent(data)
    };
}).filter("toFixed", function() {
    return function(data){
        if(!!data){
            return data.toFixed(2);
        }else{
            return data;
        }
    };
}).filter('to_trusted', ['$sce', function ($sce) {
    return function (text) {
        return $sce.trustAsHtml(text);
    };
}])

//app.filter("privateFixed",['$timeout', function($timeout) {
//    var reg = /@[\s\S]+?\[[0-9]+?\]/g;
//    return function(data){
//        var content = '';
//        if(data){
//            var ask = data.match(reg);   //匹配出@人的数组
//            var contents = (data.replace(reg,'||||')).split('||||');//匹配出@人的内容，并且转换成数组
//
//            if(ask){
//                if(!contents[0]){
//                    contents.splice(0,1); //去掉第一个空数组
//                }
//                angular.forEach(contents,function(childContent,childIndex){
//                    var askItem = ask[childIndex];
//                    var askRegIndex = askItem.indexOf('[');
//                    var askName = askItem.substr(0,askRegIndex);
//                    var askId = askItem.substr(askRegIndex + 1,askItem.length - askRegIndex - 2);
//                    content += '<a href='+ askId +'>'+ askName +'</a>'+ childContent +'';
//                });
//            }else{
//                content = contents[0];
//            }
//            return content;
//        }
//    };
//}]);



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
            if (typeof response.data === 'object' && response.data.code && typeof response.data.code === 'number') {
                switch (response.data.code){
                    case -1:
                        messageFn({text:'请登录'});
                        location.href = response.data.data;
                        break;
                    case 1:
                        messageFn({text: response.data.msg});
                        break;
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

/**
 * Created by chenmingkang on 16/3/8.
 */
if (browser.v.ios9) {
    app.config(['$provide', function($provide) {
        $provide.decorator('$browser', ['$delegate', function($delegate){
            var origUrl = $delegate.url;
            var pendingHref = null;
            var pendingHrefTimer = null;

            var newUrl = function (url, replace, state) {
                if (url) {
                    // setter
                    var result = origUrl(url, replace, state);

                    if (window.location.href == url || pendingHref == url) {
                        return;
                    }

                    pendingHref = url;

                    if (pendingHrefTimer){
                        clearTimeout(pendingHrefTimer);
                    }

                    pendingHrefTimer = setTimeout(function () {
                        if (window.location.href == pendingHref) {
                            pendingHref = null;
                        }
                        pendingHrefTimer = null;
                    }, 0);

                    return result;
                } else {
                    // getter
                    if (pendingHref == window.location.href) {
                        pendingHref = null;
                    }

                    return pendingHref || origUrl(url, replace, state);
                }
            };

            $delegate.url = newUrl;
            return $delegate;
        }]);
    }]);
}

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
        {name : 'shopCart', url: '/shopCart.html',templateUrl:'view/shopCart.html', controller: "shopCartCtr",tabSelectIndex : 4},
        {name : 'userInfo/info', url: '/userContent/info.html',templateUrl:'view/userContent/info.html', controller: "userInfoCtr",tabSelectIndex : 3},
        {name : 'userInfo/myMessage', url: '/userContent/myMessage.html',templateUrl:'view/userContent/myMessage.html', controller: "myMessageCtr",tabSelectIndex : 3},
        {name : 'userInfo/collect', url: '/userContent/collect.html',templateUrl:'view/userContent/collect.html', controller: "userCollectCtr",tabSelectIndex : 3},
        {name : 'userInfo/setUp', url: '/userContent/setUp.html',templateUrl:'view/userContent/setUp.html', controller: "",tabSelectIndex : 3},
        {name : 'userInfo/setInfo', url: '/userContent/setUp/setInfo.html',templateUrl:'view/userContent/setUp/setInfo.html', controller: "setInfoCtr",tabSelectIndex : 3},
        {name : 'userInfo/identity', url: '/userContent/setUp/identity.html',templateUrl:'view/userContent/setUp/identity.html', controller: "identityCtr",tabSelectIndex : 3},
        {name : 'userInfo/siteList', url: '/userContent/site/list.html?:isSelectAddress',templateUrl:'view/userContent/site/siteList.html', controller: "siteListCtr",tabSelectIndex : 3},
        {name : 'userInfo/siteAddEdit', url: '/userContent/site/addEdit.html?:id',templateUrl:'view/userContent/site/siteAddEdit.html', controller: "siteAddEditCtr",tabSelectIndex : 3},

        {name : 'userInfo/myDistributor', url: '/userContent/distributor/myDistributor.html',templateUrl:'view/userContent/distributor/myDistributor.html', controller: "myDistributorCtr",tabSelectIndex : 3},
        {name : 'userInfo/distributorOrder', url: '/userContent/distributor/distributorOrder.html',templateUrl:'view/userContent/distributor/distributorOrder.html', controller: "distributorOrderCtr",tabSelectIndex : 3},
        {name : 'userInfo/distributorDraw', url: '/userContent/distributor/distributorDraw.html',templateUrl:'view/userContent/distributor/distributorDraw.html', controller: "distributorDrawCtr",tabSelectIndex : 3},
        {name : 'userInfo/distributorRecord', url: '/userContent/distributor/distributorRecord.html',templateUrl:'view/userContent/distributor/distributorRecord.html', controller: "distributorRecordCtr",tabSelectIndex : 3},

        {name : 'order/createOrder', url: '/order/createOrder.html?:goodsIds&:buyNumber&:cart',templateUrl:'view/order/createOrder.html', controller: "createOrderCtr",tabSelectIndex : 3},
        {name : 'order/pay', url: '/order/pay.html?:orderNo&:price',templateUrl:'view/order/pay.html', controller: "payCtr",tabSelectIndex : 3},
        {name : 'order/myOrderList', url: '/order/myOrder/myOrderList.html?:status',templateUrl:'view/order/myOrder/myOrderList.html', controller: "myOrderListCtr",tabSelectIndex : 3},
        {name : 'order/myOrderDetail', url: '/order/myOrder/myOrderDetail.html?:orderNo',templateUrl:'view/order/myOrder/myOrderDetail.html', controller: "myOrderDetailCtr",tabSelectIndex : 3},
        {name : 'order/logistic', url: '/order/logistic.html?:orderNo',templateUrl:'view/order/logistic.html', controller: "logisticCtr",tabSelectIndex : 3}
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


/**
 * Created by chenmingkang on 16/3/8.
 */

app.run(["$rootScope","$http","$timeout",function($rootScope,$http,$timeout) {
    $rootScope.$on('$stateChangeSuccess', function(evt, current, previous) {
        if(current.tabSelectIndex){   //埋点
            $rootScope.targetScope = current.tabSelectIndex;
        }
    });
}]);

/**
 * Created by kangdaye on 16/5/15.
 */
app.factory('orderCacheFactory', function() {
    return {
        myOrderListNavTab : [
            {id:0,name:'待付款'},
            {id:1,name:'待发货'},
            {id:2,name:'待收货'},
            {id:3,name:'已完成'},
            {id:4,name:'已取消'}
        ]
    }
});
/**
 * Created by kangdaye on 16/5/15.
 */
app.controller('htlistCrt',["$scope","$rootScope","$stateParams","htlistService", function($scope,$rootScope,$stateParams,htlistService) {
   $scope.$parent.empty = false;
   $scope.$parent.async = false;
   $scope.$parent.listData = [];

   $scope.load = function(){
      if(!$scope.empty && !$scope.async){
         $scope.async = true;
         htlistService.list($scope.getData,function (data) {
            if(data.data.length < $scope.getData.limit){
               $scope.empty = true;
            }
            data.data.forEach(function(item){
               $scope.listData.push(item);
            });
            $scope.async = false;
            $scope.getData.page++;
         });
      }
   };

   $scope.tabFilter = function(up,dowm){
      $scope.empty = false;
      $scope.getData.page = 1;
      if($scope.getData.sort == up){
         $scope.getData.sort = dowm;
      }else{
         $scope.getData.sort = up;
      }
      $scope.load();
   };

   $scope.load();
}]);

/**
 * Created by kangdaye on 16/5/15.
 */
app.service('htlistService',["$http", function($http) {
    this.list = function (getData,callback) {
        $http.get(servicePath + 'goods/search',{params : getData}).success(callback);
    };
}]);

/**
 * Created by kangdaye on 16/5/15.
 */
app.controller('goodsListCtr',["$scope","$rootScope","$stateParams","goodsListCacheFactory","goodsListService", function($scope,$rootScope,$stateParams,goodsListCacheFactory,goodsListService) {
   var empty = false;
   var async = false;

   $scope.bannerConfData = {};
   $scope.tabData = goodsListCacheFactory.navTab;
   $scope.category = goodsListCacheFactory.category;
   $scope.listData = [];
   $scope.getData = {
      query : $stateParams.searchText,
      categoryId : $stateParams.categoryId,
      sort : 'volume_desc',
      page : 1,
      limit : 10
   };

   $scope.searchList = function () {
      location.href = $rootScope.prefix + "goodsList.html?searchText="+ $scope.search;
   };

   goodsListService.indexAdConfig(function (data) {
      $scope.bannerConfData = data.data.banner;
   });

   $scope.load = function(){
      if(!empty && !async){
         async = true;
         goodsListService.searchList($scope.getData,function (data) {
            if(data.data.length < $scope.getData.limit){
               empty = true;
            }
            data.data.forEach(function(item){
               $scope.listData.push(item);
            });
            async = false;
            $scope.getData.page++;
         });
      }
   };

   $scope.tabFilter = function(up,dowm){
      empty = false;
      $scope.getData.page = 1;
      $scope.listData = [];
      if($scope.getData.sort == up){
         $scope.getData.sort = dowm;
      }else{
         $scope.getData.sort = up;
      }
      $scope.load();
   };

}]);

/**
 * Created by kangdaye on 16/5/15.
 */
app.factory('goodsListCacheFactory', function() {
    return {
        navTab : [
            {name:'按销量',up:'volume_desc',dowm:'volume_asc'},
            {name:'按价格',up:'price_desc',dowm:'price_asc'},
            {name:'按上架',up:'time_desc',dowm:'time_asc'}
        ],
        category : [
            {id:101,name:'大米杂粮'},
            {id:102,name:'各地特产'},
            {id:103,name:'手作食材'},
            {id:104,name:'时令水果'},
            {id:105,name:'特价促销'}
        ]
    }
});

/**
 * Created by kangdaye on 16/5/15.
 */
app.service('goodsListService',["$http", function($http) {
    this.searchList = function (getData,callback) {
        $http.get(servicePath + 'goods/search',{params : getData}).success(callback);
    };

    this.indexAdConfig = function (callback) {
        $http.get(servicePath + 'index_ad_config',{}).success(callback);
    };

}]);

/**
 * Created by kangdaye on 16/5/15.
 */
app.controller('detailCtr',["$scope","$rootScope","$stateParams","detailService","messageFactory", function($scope,$rootScope,$stateParams,detailService,messageFactory) {
   var defaultData = {
      goodsId : $stateParams.goodsId
   };
   var async = false;

   $scope.buyDialog = false;
   $scope.detailData = {};
   $scope.getData = angular.extend({buyNumber : 1,cart : false},defaultData);

   detailService.detailData(defaultData,function (data) {
      $scope.detailData = data.data;
   });

   $scope.addFavorite  = function(){
      if(async){
         return;
      }
      async = true;
      if(!$scope.detailData.favorite){
         detailService.addFavorite(defaultData,function (data) {
            $scope.detailData.favorite = !$scope.detailData.favorite;
            messageFactory({text : '收藏成功'});
            async = false;
         });
      }else {
         detailService.deleteFavorite(defaultData,function (data) {
            $scope.detailData.favorite = !$scope.detailData.favorite;
            messageFactory({text : '取消收藏'});
            async = false;
         });
      }
   };

   $scope.numOption = function(model){      //＋＋＋＋  －－－－－
      if(model == 'redu'){
         if($scope.getData.buyNumber <= 0){
            return;
         }
         $scope.getData.buyNumber--;
      }else{
         $scope.getData.buyNumber++;
      }
   };

   $scope.shopChangeNum = function(){
      var res = new RegExp(/^(\d)*$/);
      if($scope.getData.buyNumber > $scope.detailData.inventory){    //是否比存量大
         $scope.getData.buyNumber = $scope.detailData.inventory;
      }

      if(!res.test($scope.getData.buyNumber)){  //是否是数字
         $scope.getData.buyNumber = 1;
      }
   };

   $scope.shopBlurNum = function(){
      $scope.getData.buyNumber = parseInt($scope.getData.buyNumber);
      if($scope.getData.buyNumber <= 0 || !$scope.getData.buyNumber){
         $scope.getData.buyNumber = 1;
      }
   };

   $scope.buyDialogToggle = function(){
      $scope.buyDialog = !$scope.buyDialog;
   };

   $scope.go = function(isAddCart){
      $scope.getData.cart = isAddCart;
      $scope.buyDialogToggle();
   };

   $scope.placeOrder = function(){
      var getData = {
         goodsIds : defaultData.goodsId,
         buyNumber : $scope.getData.buyNumber,
         cart : $scope.getData.cart
      };

      if(!$scope.getData.cart){
         location.href = $rootScope.prefix + 'order/createOrder.html?' + angular.param(getData);
      }else{
         detailService.addCart($scope.getData,function (data) {
            $scope.buyDialogToggle();
            messageFactory({text : '加入购物车成功'});
         });
      }
   };
}]);

/**
 * Created by kangdaye on 16/5/15.
 */
app.service('detailService',["$http", function($http) {
    this.detailData = function (getData,callback) {
        $http.get(servicePath + 'goods/details',{params:getData}).success(callback);
    };

    this.addFavorite = function (postData,callback) {
        $http.post(servicePath + 'favorite/join',postData).success(callback);
    };

    this.deleteFavorite = function (postData,callback) {
        $http.post(servicePath + 'favorite/delete',postData).success(callback);
    };

    this.addCart = function (postData,callback) {
        $http.post(servicePath + 'cart/join',postData).success(callback);
    };
}]);

/**
 * Created by kangdaye on 16/5/15.
 */
app.controller('indexCtr',["$scope","$rootScope","indexService", function($scope,$rootScope,indexService) {
   $scope.indexConfData = {};
   $scope.indexRecommend = {};
   $scope.getData = {
      sort : 'volume_desc',
      page : 1,
      limit : 10
   };

   indexService.indexAdConfig(function (data) {
      $scope.indexConfData = data.data;
   });

   indexService.indexList({
      promotion : true,
      limit : 3
   },function (data) {
      $scope.indexRecommend = data.data;
   });

   $scope.searchList = function () {
      location.href = $rootScope.prefix + "goodsList.html?searchText="+ $scope.search;
   };
}]);

/**
 * Created by kangdaye on 16/5/15.
 */
app.service('indexService',["$http", function($http) {
    this.indexAdConfig = function (callback) {
        $http.get(servicePath + 'index_ad_config',{}).success(callback);
    };

    this.indexList = function (getData,callback) {
        $http.get(servicePath + 'goods/search',{params : getData}).success(callback);
    };
}]);

/**
 * Created by kangdaye on 16/5/20.
 */
app.service('shopCartService',["$http", function($http) {
    this.cartList = function (callback) {
        $http.post(servicePath + 'cart/list').success(callback);
    };

    this.cartAdd = function (postData,callback) {
        $http.post(servicePath + 'cart/join',postData).success(callback);
    };

    this.cartUpNum= function (postData,callback) {
        $http.post(servicePath + 'cart/update_buyNumber',postData).success(callback);
    };

    this.cartDelete= function (postData,callback) {
        $http.post(servicePath + 'cart/delete',postData).success(callback);
    };

    this.cartDeleteAll= function (callback) {
        $http.post(servicePath + 'cart/delete_all').success(callback);
    };
}]);

/**
 * Created by chenmingkang on 15/7/14.
 */
app.controller('shopCartCtr',["$scope","$rootScope","shopCartService","confirmFactory","messageFactory",function($scope,$rootScope,shopCartService,confirmFactory,messageFactory) {
    $scope.shopInfo = {
        total:0,
        selectLength:0,
        selectArray:[]
    };
    $scope.shopData = [];

    var itemLonNum = 0;   //改变了之后的数量
    var itemFocusNum = 0; //获取焦点之后的数量
    var upShopService = function(item){    //修改的service
        var upShopData ={
            buyNumber:item.buyNumber,
            goodsId:item.goodsId
        };
        shopCartService.cartUpNum(upShopData,function(data){
            $rootScope.loading = false;
        });
    };

    var isAllSelect = function(){      //是不是只有一个没选中
        if($scope.shopData.length == $scope.shopInfo.selectLength){
            $scope.allCheckSelect = true;
        }else{
            $scope.allCheckSelect = false;
        }
    };

    var itemTotalCount = function(item){   //求出商品多少钱
        item.itemTotal = parseFloat(item.itemCurPrice * item.buyNumber);
    };

    var totalCount = function(model,num){    //算出总价格
        if (model == 'add') {
            $scope.shopInfo.total = $scope.shopInfo.total + num;
        } else {
            $scope.shopInfo.total = $scope.shopInfo.total - num;
        }
    };

    var isSelectTotal = function(model,isSelect,num){  //如果有勾选的话 算出勾选的中价格
        if(isSelect){
            totalCount(model,num);
        }
    };


    $scope.upShopRedu = function(parItem,item){    //－－－－－－的方法
        if(item.buyNumber > 1){
            item.buyNumber--;
            isSelectTotal('less',item.checkSelect,item.price); //如果有勾选的话 算出勾选的中价格
            if(item.checkSelect){
                parItem.total -= parseFloat(item.price); //店铺的总价格
            }
            upShopService(item);
            itemTotalCount(item); //算出商品价格
        }
    };

    $scope.upShopPuls = function(parItem,item){      //＋＋＋＋的方法
        if(item.buyNumber < item.inventory){
            item.buyNumber++;
            isSelectTotal('add',item.checkSelect,item.price); //如果有勾选的话 算出勾选的中价格
            if(item.checkSelect){
                parItem.total += parseFloat(item.price); //店铺的总价格
            }
            upShopService(item);
            itemTotalCount(item); //算出商品价格
        }
    };

    $scope.upShopNum = function(parItem,item){    //num数量改变的方法
        var res = new RegExp(/^(\d)*$/);
        var itemCurPrice = parseInt(item.price);
        if(item.buyNumber > item.inventory){    //是否比存量大
            item.buyNumber = item.inventory;
        }

        if(!res.test(item.buyNumber)){  //是否是数字
            item.buyNumber = 1;
        }

        if(item.checkSelect){
            parItem.total = parseFloat(parItem.total - (itemLonNum * itemCurPrice) + (item.buyNumber * itemCurPrice));    //店铺的总价格
            $scope.shopInfo.total = $scope.shopInfo.total - (itemLonNum * itemCurPrice) + (item.buyNumber * itemCurPrice);   //用旧的减去 在加上新的
        }

        itemTotalCount(item); //算出商品价格

        itemLonNum = item.buyNumber;   //把个数存下来
    };

    $scope.upShopFocusNum = function(item){ //num数量移入促发的事件
        itemLonNum = itemFocusNum = item.buyNumber;    //吧个数存下来
    };

    $scope.upShopBlurNum = function(item){    //num数量移开促发的事件
        item.buyNumber = parseInt(item.buyNumber);
        if(itemFocusNum != item.buyNumber){
            if(!item.buyNumber){
                item.buyNumber = 1;
                itemTotalCount(item);
            }
            upShopService(item);
        }
    };

    $scope.checkSelect = function(parItem,item){  //每个商品全选选择
        if(item.checkSelect){
            $scope.shopInfo.selectLength++;
            totalCount('add',item.price);    //计算总价格
            $scope.shopInfo.selectArray.push(item.goodsId);
        }else{
            $scope.shopInfo.selectLength--;
            totalCount('less',item.price);   //计算总价格
            var itemIndex = angular.inArray(item.goodsId,$scope.shopInfo.selectArray);
            if(itemIndex > -1){
                $scope.shopInfo.selectArray.splice(itemIndex,1);
            }
        }
        isAllSelect();//判断是不是全部选中
    };


    $scope.allSelect = function(){
        $scope.shopInfo.selectArray = [];
        $scope.shopInfo.total = 0;
        angular.forEach($scope.shopData,function(item,i){   //如果他长度等于一 那就把它父节点给清除了
            if($scope.allCheckSelect){   //全部选中
                item.checkSelect = true;
                $scope.shopInfo.selectLength++;
                totalCount('add',item.price);
                $scope.shopInfo.selectArray.push(item.goodsId);
            }else{
                $scope.shopInfo.selectLength = 0;
                item.checkSelect = false;
            }
        });
    };


    $scope.deleteShop = function(item,index){
        confirmFactory({
            scope:$scope,
            text:'你确定从购物车删除该物品吗？',
            option:{
                go:function(){
                    shopCartService.cartDelete({
                        goodsId : item.goodsId
                    },function(data){
                        var itemIndex = angular.inArray(item.goodsId,$scope.shopInfo.selectArray);
                        if(itemIndex > -1){
                            $scope.shopInfo.selectArray.splice(itemIndex,1);
                            $scope.shopInfo.selectLength--;  //总的长度减1
                            isSelectTotal('less',item.checkSelect,item.itemTotal);
                        }
                        $scope.shopData.splice(index,1);
                        isAllSelect();
                        messageFactory({text : '删除成功'});
                    });
                }
            }
        });
    };

    $scope.deleteAll = function(){
        confirmFactory({
            text:'你确定从购物车删除这些物品吗？',
            option:{
                go:function(){
                    shopCartService.cartDeleteAll(function(data){
                        $scope.allCheckSelect = false;
                        $scope.shopInfo.total = 0;
                        $scope.shopInfo.selectArray = [];
                        $scope.shopData = [];
                    });
                }
            }
        });
    };


    $scope.submit = function(){  //结算
        var  getData = {
            goodsIds : $scope.shopInfo.selectArray.join(','),
            cart : true
        };

        location.href = $rootScope.prefix + 'order/createOrder.html?' + angular.param(getData);
    };

    shopCartService.cartList(function(data){
        $scope.shopData = data.data;
    });

}]);

/**
 * Created by kangdaye on 16/5/15.
 */
app.factory('distributorCacheFactory', function() {
    return {
        myDistributorNavTab : [
            {name:'全部'},
            {level:'lv1',name:'一级'},
            {level:'lv2',name:'二级'}
        ],
       recordTab : [
           {name : '全部'},
           {status : 'through',name : '已通过'},
           {status : 'not_through',name : '未通过'},
           {status : 'audit',name : '审核中'}
       ]
    }
});

/**
 * Created by kangdaye on 16/5/24.
 */
app.controller('createOrderCtr',["$scope","$rootScope","$stateParams","authorFactory","createOrderService",function($scope,$rootScope,$stateParams,authorFactory,createOrderService) {
    $scope.createOrderData = {};
    $scope.leaveMessage = '';

    createOrderService.getCartGoods($stateParams,function(data){
        $scope.createOrderData = data.data;
        if(sessionStorage.selectAddress){
            data.data.address = JSON.parse(sessionStorage.selectAddress);
            sessionStorage.selectAddress = '';
        }
        if(data.data.address){
            $scope.calculateFreight();
        }
    });
    
    $scope.calculateFreight = function(){
        createOrderService.calculateFreight({
            addressId : $scope.createOrderData.address.id,
            settleId : $scope.createOrderData.settle.settleId
        },function(data){
            $scope.createOrderData.settle.freight = data.data.freight;
            $scope.createOrderData.settle.totalAmount = data.data.totalAmount;
        });
    };

    $scope.selectAddress = function(){
        location.href = $rootScope.prefix + 'userContent/site/list.html?isSelectAddress=true';
    };

    $scope.goPay = function(){
        createOrderService.buy({
            settleId : $scope.createOrderData.settle.settleId,
            leaveMessage : $scope.leaveMessage
        },function(data){
            authorFactory({
                href : 'order/pay.html',
                data : {
                    orderNo : data.data.orderNo,
                    price : data.data.amount
                }
            });
        });
    }
}]);

/**
 * Created by kangdaye on 16/5/24.
 */
app.service('createOrderService',["$http", function($http) {
    this.getCartGoods = function (postData,callback) {
        $http.post(servicePath + 'cart/settle',postData).success(callback);
    };

    this.calculateFreight = function (postData,callback) {
        $http.post(servicePath + 'cart/settle/calculateFreight',postData).success(callback);
    };

    this.buy = function (postData,callback) {
        $http.post(servicePath + 'cart/settle/buy',postData).success(callback);
    };
}]);

/**
 * Created by kangdaye on 16/5/24.
 */
app.controller('logisticCtr',["$scope","$stateParams","logisticsService",function($scope,$stateParams,logisticsService) {
    $scope.logisticData = {};

    logisticsService.logistics({
        orderNo : $stateParams.orderNo
    },function(data){
        $scope.logisticData = data.data;
    });
}]);

/**
 * Created by kangdaye on 16/5/24.
 */
app.service('logisticsService',["$http", function($http) {
    this.logistics = function (postData,callback) {
        $http.post(servicePath + 'my_orders/logistics',postData).success(callback);
    };
}]);

/**
 * Created by kangdaye on 16/5/24.
 */
app.controller('myOrderDetailCtr',["$scope","$rootScope","$stateParams","myOrderDetailService","confirmFactory","authorFactory","messageFactory",function($scope,$rootScope,$stateParams,myOrderDetailService,confirmFactory,authorFactory,messageFactory) {
    var defaultData = {
        orderNo : $stateParams.orderNo
    };
    $scope.orderDetail = {};


    myOrderDetailService.detail(defaultData,function(data){
        $scope.orderDetail = data.data;
    });

    $scope.goPay = function(item){
        authorFactory({
            href : 'order/pay.html',
            data : {
                orderNo : $scope.orderDetail.item.orderNo,
                price : $scope.orderDetail.totalAmount
            }
        });
    };

    $scope.confirmDelivery = function(){
        confirmFactory({
            scope:$scope,
            text:'确定收到物品？',
            option:{
                go:function(){
                    myOrderDetailService.confirmDelivery(defaultData,function(){
                        messageFactory({text : '确认收货成功'});
                        $scope.orderDetail.status = 3;
                    });
                }
            }
        });
    };
}]);

/**
 * Created by kangdaye on 16/5/24.
 */
app.controller('myOrderListCtr',["$scope","$rootScope","$stateParams","myOrderListService","orderCacheFactory","confirmFactory","authorFactory","messageFactory",function($scope,$rootScope,$stateParams,myOrderListService,orderCacheFactory,confirmFactory,authorFactory,messageFactory) {
    var empty = false;
     $scope.applyEfundMoadl = false;
    $scope.getData = {
        status : $stateParams.status,
        page : 1,
        limit : 10
    };
    $scope.applyEfundItemData = {
        orderNo : 0,
        refundReason : ''
    };

    $scope.navTab = orderCacheFactory.myOrderListNavTab;
    $scope.async = false;
    $scope.listData = [];

    $scope.load = function(){
        if(empty || $scope.async){
            return
        }
        $scope.async = true;
        myOrderListService.ordersList($scope.getData,function(data){
            if(data.data.length < $scope.getData.limit){
                empty = true;
            }
            data.data.forEach(function(item){
                $scope.listData.push(item);
            });
            $scope.getData.page++;
            $scope.async = false;
        });
    };

    $scope.orderClose = function(orderNo,index){
        confirmFactory({
            scope:$scope,
            text:'确定要关闭订单？',
            option:{
                go:function(){
                    myOrderListService.closeOrder({
                        orderNo : orderNo
                    },function(){
                        messageFactory({text : '关闭订单成功'});
                        $scope.listData.splice(index,1);
                    });
                }
            }
        });
    };

    $scope.confirmDelivery = function(orderNo,index){
        confirmFactory({
            scope:$scope,
            text:'确定收到物品？',
            option:{
                go:function(){
                    myOrderListService.confirmDelivery({
                        orderNo : orderNo
                    },function(){
                        messageFactory({text : '确认收货成功'});
                        $scope.listData.splice(index,1);
                    });
                }
            }
        });
    };

    $scope.goPay = function(item){
        authorFactory({
            href : 'order/pay.html',
            data : {
                orderNo : item.orderNo,
                price : item.totalAmount
            }
        });
    };

    $scope.applyEfund = function(orderNo){
        $scope.applyEfundMoadl = true;
        $scope.applyEfundItemData = {
            orderNo : orderNo
        };
    };

    $scope.efundMoadlTot = function(){
        $scope.applyEfundMoadl = !$scope.applyEfundMoadl;
    };

    $scope.applyEfundSuc = function(){
        myOrderListService.applyRefund($scope.applyEfundItemData,function(){
            messageFactory({text : '退款申请提交成功'});
            $scope.navTabClick(4);
            $scope.efundMoadlTot();
        });
    };

    $scope.load();
}]);

/**
 * Created by kangdaye on 16/5/24.
 */
app.service('myOrderDetailService',["$http", function($http) {
    this.detail = function (postData,callback) {
        $http.post(servicePath + 'my_orders/details',postData).success(callback);
    };

    this.confirmDelivery = function (postData,callback) {
        $http.post(servicePath + 'my_orders/confirm_delivery',postData).success(callback);
    };

}]);

/**
 * Created by kangdaye on 16/5/24.
 */
app.service('myOrderListService',["$http", function($http) {
    this.ordersList = function (postData,callback) {
        $http.post(servicePath + 'my_orders/orders',postData).success(callback);
    };

    this.closeOrder = function (postData,callback) {
        $http.post(servicePath + 'my_orders/close',postData).success(callback);
    };

    this.confirmDelivery = function (postData,callback) {
        $http.post(servicePath + 'my_orders/confirm_delivery',postData).success(callback);
    };

    this.applyRefund = function (postData,callback) {
        $http.post(servicePath + 'my_orders/apply_refund',postData).success(callback);
    };


}]);

/**
 * Created by kangdaye on 16/5/24.
 */
app.service('payService',["$http", function($http) {
    this.pay = function (postData,callback) {
        $http.post(servicePath + 'order/pay',postData).success(callback);
    };
}]);

app.controller('payCtr',["$scope","$rootScope","$stateParams","payService",function($scope,$rootScope,$stateParams,payService) {
    $scope.data = $stateParams;
    // var orderNum = $stateParams.orderNum;
    //
    // $rootScope.loading = false;
    //
    // $scope.price = $stateParams.price;
    // $scope.text = '';
    // $scope.payOption = '';
    //
    //


    // appId : "wxa66a636535c987db",     //公众号名称，由商户传入
    //     timeStamp: datas.timeStamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
    //     nonceStr: datas.nonceStr, // 支付签名随机串，不长于 32 位
    //     package: 'prepay_id=' + datas.prepay_id, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
    //     signType: 'MD5', // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
    //     paySign: datas.paySign // 支付签名

    function onBridgeReady(data){
        WeixinJSBridge.invoke('getBrandWCPayRequest',data,function(res){
                if(res.err_msg == "get_brand_wcpay_request:ok" || res.err_msg == "get_brand_wcpay_request:cancel" ) {  //如果付款成功或者取消付款都去订单详情页面
                    location.href = $rootScope.prefix + 'order/myOrder/myOrderList.html?status=1';
                }else{
                    alert('发生未知错误');
                }
            }
        );
    }

    function bindEvent(data){
        if (typeof WeixinJSBridge == "undefined"){
            if( document.addEventListener ){
                document.addEventListener('WeixinJSBridgeReady', function(){
                    onBridgeReady(data)
                }, false);
            }else if (document.attachEvent){
                document.attachEvent('WeixinJSBridgeReady', function(){
                    onBridgeReady(data)
                });
                document.attachEvent('onWeixinJSBridgeReady', function(){
                    onBridgeReady(data)
                });
            }
        }else{
            onBridgeReady(data);
        }
    }

    $scope.submit = function(){
        payService.pay({
            orderNo : $scope.data.orderNo
        },function(data){
            bindEvent(data.data)
        });
    };


}]);
/**
 * Created by kangdaye on 16/5/15.
 */
app.controller('distributorDrawCtr',["$scope","$rootScope","distributorDrawService", function($scope,$rootScope,distributorDrawService) {
    $scope.getData = {
        channel : 'WECHAT'
    };

    $scope.submit = function(){
        distributorDrawService.apply($scope.getData,function(){
            location.href = $rootScope.prefix + 'userContent/distributor/distributorRecord.html';
        });
    };
}]);

/**
 * Created by kangdaye on 16/5/15.
 */
app.controller('distributorOrderCtr',["$scope","$rootScope","distributorOrderService", function($scope,$rootScope,distributorOrderService) {
    $scope.data = {};
    $scope.distributorTabList = {};
    $scope.tabSelectId = 'lv1';

    $scope.tab = [
        {id:'lv1',name:'一级分销商'},
        {id:'lv2',name:'二级分销商'}
    ];

    $scope.distributorListToggle = function(model){
        $scope.tabSelectId = model;
        $scope.distributorTabList = $scope.data[model];
    };

    distributorOrderService.distributorsStat(function(data){
        $scope.data = data.data;
        $scope.distributorListToggle($scope.tabSelectId);
    });
}]);

/**
 * Created by kangdaye on 16/5/23.
 */
app.controller('distributorRecordCtr',["$scope","$rootScope","distributorCacheFactory","distributorRecordService", function($scope,$rootScope,distributorCacheFactory,distributorRecordService) {
    $scope.listData = [];
    $scope.tab = distributorCacheFactory.recordTab;
    $scope.getData = {};

    $scope.selectTav = function(status){
        $scope.getData.status = status;
        distributorRecordService.records($scope.getData,function(data){
            $scope.listData = [];
            $scope.listData = data.data;
        });
    };

    $scope.selectTav($scope.tab[0].status);
}]);

/**
 * Created by kangdaye on 16/5/15.
 */
app.controller('myDistributorCtr',["$scope","$rootScope","myDistributorService","distributorCacheFactory", function($scope,$rootScope,myDistributorService,distributorCacheFactory) {
    var empty = false;
    $scope.async = false;
    $scope.distributorsData = {
        startData : {},
        listData : []
    };
    $scope.getData = {
        level : 'lv1',
        page : 1,
        limit : 10
    };

    $scope.navTab = distributorCacheFactory.myDistributorNavTab;

    myDistributorService.distributorsStat(function(data){
        $scope.distributorsData.startData = data.data;
    });

    $scope.load = function(){
        if(!$scope.async && !empty){
            $scope.async = true;
            myDistributorService.distributorsList($scope.getData,function(data){
                data.data.forEach(function(item){
                    $scope.distributorsData.listData.push(item);
                    if(data.data.length < $scope.getData.limit){
                        empty = true;
                    }
                    $scope.async = false;
                });
            });
        }
    };

    $scope.navTabClick = function(level){
        $scope.distributorsData.listData = [];
        empty = false;
        if(level){
            $scope.getData.level = level;
        }else{
            delete $scope.getData.level;
        }
        $scope.load();
    }
}]);

/**
 * Created by kangdaye on 16/5/18.
 */
app.service('distributorDrawService',["$http", function($http) {
    this.apply = function (postData,callback) {
        $http.post(servicePath + 'withdraw/apply',postData).success(callback);
    };
}]);
/**
 * Created by kangdaye on 16/5/18.
 */
app.service('distributorOrderService',["$http", function($http) {
    this.distributorsStat = function (callback) {
        $http.get(servicePath + 'distribution/commission_stat').success(callback);
    };
}]);
/**
 * Created by kangdaye on 16/5/18.
 */
app.service('distributorRecordService',["$http", function($http) {
    this.records = function (postData,callback) {
        $http.post(servicePath + 'withdraw/records',postData).success(callback);
    };
}]);
/**
 * Created by kangdaye on 16/5/18.
 */
app.service('myDistributorService',["$http", function($http) {
    this.distributorsStat = function (callback) {
        $http.get(servicePath + 'distribution/lower_distributors_stat').success(callback);
    };

    this.distributorsList = function (postData,callback) {
        $http.post(servicePath + 'distribution/lower_distributors_list',postData).success(callback);
    };
}]);
/**
 * Created by kangdaye on 16/5/15.
 */
app.controller('myMessageCtr',["$scope","$rootScope","myMessageService", function($scope,$rootScope,myMessageService) {
    var getData = {
        page : 1,
        limit : 10
    };
    var async = false;

    $scope.listData = [];

    $scope.load = function(){
        if(async){
            return;
        }
        async = true;
        myMessageService.list(getData,function(data){
            data.data.forEach(function(item){
                $scope.listData.push(item);
            });
            if(data.data.length >= getData.limit){
                async = false;
            }
            getData.page++;
        });
    };

    $scope.load();
}]);

/**
 * Created by kangdaye on 16/5/20.
 */
app.service('myMessageService',["$http", function($http) {
    this.list = function (postData,callback) {
        $http.post(servicePath + 'message/list',postData).success(callback);
    };
}]);


/**
 * Created by kangdaye on 16/5/15.
 */
app.controller('identityCtr',["$scope","$rootScope","Upload","messageFactory","identityService", function($scope,$rootScope,Upload,messageFactory,identityService) {
    var watch;

    $scope.isVlidate = !$scope.userInfoData.certifiedInfo || $scope.userInfoData.certifiedInfo.status == 9 || $scope.userInfoData.certifiedInfo.auditlog
    $scope.getData = {};

    $scope.upload = function (model,file) {
        if($scope.isVlidate){
            return;
        }
        Upload.upload({
            url: servicePath + 'member/upload_certificate',
            data: {file: file, 'username': $scope.username}
        }).then(function (resp) {
            if(model === 1){
                $scope.getData.idCarImage1 = resp.data;
            }else{
                $scope.getData.idCarImage0 = resp.data;
            }
        });
    };

    $scope.submit = function(){
        identityService.certification($scope.getData,function(){
            messageFactory({text : '正在提交审核'});
            $rootScope.userInfoData.certifiedInfo.status = 0;
        });
    };

    watch = $scope.$watch('userInfoData.certifiedInfo',function(newVal){
        if(newVal){
            angular.extend($scope.getData,newVal);
        }
    });

    $scope.$on('$destroy', function(){
        watch();
    });

}]);

/**
 * Created by kangdaye on 16/5/15.
 */
app.controller('setInfoCtr',["$scope","$rootScope","$timeout","Upload","messageFactory","setInfoService", function($scope,$rootScope,$timeout,Upload,messageFactory,setInfoService) {
    $scope.userInfo = {};
    var watchUserInfoData = $scope.$watch('userInfoData',function(newVal){
        if(newVal){
            angular.extend($scope.userInfo,$rootScope.userInfoData);
            console.log($scope.userInfo);
        }
    });

    $scope.$on('$destroy', function(){
        watchUserInfoData();
    });

    $scope.upload = function (file) {
        Upload.upload({
            url: servicePath + 'member/update_avatar',
            data: {file: file, 'username': $scope.username}
        }).then(function (resp) {
            $scope.userInfo.avatar = resp.data;
        });
    };

    $scope.submit = function(){
        setInfoService.updateBasisinfo($scope.userInfo,function(){
            $rootScope.userInfoData = angular.copy($scope.userInfo);
        })
    };
}]);

/**
 * Created by kangdaye on 16/5/20.
 */
app.service('identityService',["$http", function($http) {
    this.certification = function (postData,callback) {
        $http.post(servicePath + 'member/certification',postData).success(callback);
    };
}]);


/**
 * Created by kangdaye on 16/5/20.
 */
app.service('setInfoService',["$http", function($http) {
    this.updateBasisinfo = function (postData,callback) {
        $http.post(servicePath + 'member/update_basisinfo',postData).success(callback);
    };
}]);


/**
 * Created by kangdaye on 16/5/20.
 */
app.controller('siteAddEditCtr',["$scope","$stateParams","siteAddEditService","messageFactory", function($scope,$stateParams,siteAddEditService,messageFactory) {
    var id = $stateParams.id;

    $scope.toggleModal = function() {
        $scope.modalShown = !$scope.modalShown;
    };

    $scope.addressItemData = {
        defaul : false
    };
    $scope.address = {};

    if(id){
        $scope.addressItemData.id = id;
        siteAddEditService.addressGet($scope.addressItemData,function(data){
            $scope.addressItemData = data.data;
            angular.extend($scope.address,$scope.addressItemData);
        });
    }

    $scope.siteSelect =function(){ //选择地区成功
        angular.extend($scope.addressItemData,$scope.address);
        $scope.toggleModal();
    };

    $scope.submit = function(){ //提交
        if(id){
            siteAddEditService.addressUpdate($scope.addressItemData,function(){
                window.history.go(-1);
            });
        }else{
            siteAddEditService.addressAdd($scope.addressItemData,function(){
                window.history.go(-1);
            });
        }
    };
}]);

/**
 * Created by kangdaye on 16/5/20.
 */
app.controller('siteListCtr',["$scope","$stateParams","siteListService","messageFactory", function($scope,$stateParams,siteListService,messageFactory) {
    $scope.listData = [];
    
    siteListService.addressList(function(data){
        $scope.listData = data.data;
    });

    $scope.deleteItem = function(id,index){
        siteListService.delete({
            id : id
        },function(){
            $scope.listData.splice(index,1);
            messageFactory({text:'删除成功'});
        })
    };

    $scope.defaulAddress = function(item){
        if($stateParams.isSelectAddress){
            sessionStorage.selectAddress = JSON.stringify(item);
            window.history.back();
            return;
        }
        if(item.defaul){
            return;
        }
        siteListService.defaulAddress(item,function(){
            messageFactory({text : '设为默认地址成功'});
            $scope.listData.forEach(function(selectItem){
                if(selectItem.id != item.id){
                    selectItem.defaul = false;
                }
            });
        })
    };
}]);

/**
 * Created by kangdaye on 16/5/20.
 */
app.service('siteAddEditService',["$http", function($http) {
    this.addressAdd = function (postData,callback) {
        $http.post(servicePath + 'address/add',postData).success(callback);
    };

    this.addressUpdate = function (postData,callback) {
        $http.post(servicePath + 'address/update',postData).success(callback);
    };

    this.addressGet = function (postData,callback) {
        $http.post(servicePath + 'address/get',postData).success(callback);
    };
}]);

/**
 * Created by kangdaye on 16/5/20.
 */
app.service('siteListService',["$http", function($http) {
    this.addressList = function (callback) {
        $http.get(servicePath + 'address/list').success(callback);
    };

    this.delete = function (postData,callback) {
        $http.post(servicePath + 'address/delete',postData).success(callback);
    };

    this.defaulAddress = function (postData,callback) {
        $http.post(servicePath + 'address/update',postData).success(callback);
    };
}]);

/**
 * Created by kangdaye on 16/5/15.
 */
app.controller('userCollectCtr',["$scope","userCollectService","messageFactory", function($scope,userCollectService,messageFactory) {
    $scope.listData = [];

    userCollectService.favoriteList(function(data){
        $scope.listData = data.data;
    });

    $scope.deleteItem = function(goodsId,index){
        userCollectService.favoriteDeleteItem({
            goodsId : goodsId
        },function(){
            $scope.listData.splice(index,1);
            messageFactory({text : '删除成功'});
        })
    };

    $scope.deleteAll = function(goodsId,index){
        userCollectService.favoriteDeleteAll(function(){
            $scope.listData = [];
            messageFactory({text : '删除全部成功'});
        })
    }
}]);

/**
 * Created by kangdaye on 16/5/15.
 */
app.service('userCollectService',["$http", function($http) {
    this.favoriteList = function (callback) {
        $http.get(servicePath + 'favorite/list').success(callback);
    };

    this.favoriteDeleteItem = function (postData,callback) {
        $http.post(servicePath + 'favorite/delete',postData).success(callback);
    };

    this.favoriteDeleteAll = function (callback) {
        $http.post(servicePath + 'favorite/delete_all').success(callback);
    };

}]);

app.controller('userInfoCtr',["$scope","userInfoCacheFactory","userInfoService", function($scope,userInfoCacheFactory,userInfoService) {
    $scope.orderNum = {};
    $scope.navTab = userInfoCacheFactory.navTab;
    $scope.orderStateData = userInfoCacheFactory.orderState;

    $scope.navTabClick = function(id){
        $scope.navTabSelectId = id;
    };

    userInfoService.orderNum(function(data){
        $scope.orderNum = data.data;
    });
    $scope.navTabClick($scope.navTab[0].id);
}]);

/**
 * Created by kangdaye on 16/5/15.
 */
app.factory('userInfoCacheFactory', function() {
    return {
        navTab : [
            {id:1,name:'普通会员'},
            {id:2,name:'分销会员'}
        ],
        orderState : [
            {hrefStatus : 0,key : "waitPay",name : '待付款',icon : 'icon-daifukuan'},
            {hrefStatus : 1,key : "waitDelivery",name : '待发货',icon : 'icon-daifahuo'},
            {hrefStatus : 2,key : "waitReceive",name : '待收货',icon : 'icon-daishouhuo'},
            {hrefStatus : 3,key : "complete",name : '已完成',icon : 'icon-yiwanchengdingdan'},
            {hrefStatus : 4,key : "refund_closed",name : '退款/取消',icon : 'icon-tixian1'}
        ]
    }
});

app.service('userInfoService',["$http", function($http) {
    this.userInfo = function (callback) {
        $http.get(servicePath + 'member/userinfo').success(callback);
    };

    this.orderNum = function (callback) {
        $http.get(servicePath + 'my_orders/statistics').success(callback);
    };
}]);
