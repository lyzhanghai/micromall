/**
 * Created by kangdaye on 16/5/15.
 */
app.service('detailService',["$http", function($http) {
    this.detailData = function (getData,callback) {
        $http.get(servicePath + '/goods/details',{params:getData}).success(callback);
    };
}]);
