/**
 * Created by kangdaye on 16/5/20.
 */
app.service('siteAddEditService',["$http", function($http) {
    this.addressAdd = function (postData,callback) {
        $http.post(servicePath + 'address/add',postData).success(callback);
    };
}]);
