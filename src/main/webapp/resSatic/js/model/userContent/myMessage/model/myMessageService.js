/**
 * Created by kangdaye on 16/5/20.
 */
app.service('myMessageService',["$http", function($http) {
    this.list = function (postData,callback) {
        $http.post(servicePath + 'message/list',postData).success(callback);
    };
}]);

