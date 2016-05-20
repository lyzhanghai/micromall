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
}]);
