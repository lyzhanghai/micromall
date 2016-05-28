/**
 * Created by kangdaye on 16/5/18.
 */
app.service('distributorRecordService',["$http", function($http) {
    this.records = function (postData,callback) {
        $http.post(servicePath + 'withdraw/records',postData).success(callback);
    };
}]);