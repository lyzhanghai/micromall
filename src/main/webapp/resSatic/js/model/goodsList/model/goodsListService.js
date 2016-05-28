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
