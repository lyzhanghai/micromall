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
