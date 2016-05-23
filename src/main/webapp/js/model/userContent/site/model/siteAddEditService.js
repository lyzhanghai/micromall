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
