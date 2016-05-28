/**
 * Created by kangdaye on 16/5/18.
 */
app.service('distributorOrderService',["$http", function($http) {
    this.distributorsStat = function (callback) {
        $http.get(servicePath + 'distribution/commission_stat').success(callback);
    };
}]);