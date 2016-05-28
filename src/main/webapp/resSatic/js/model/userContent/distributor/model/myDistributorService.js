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