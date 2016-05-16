/**
 * Created by kangdaye on 16/5/15.
 */
app.service('htlistService',["$http", function($http) {
    this.list = function (getData,callback) {
        $http.get(servicePath + 'goods/search',{params : getData}).success(callback);
    };
}]);
