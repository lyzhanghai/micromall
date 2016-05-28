/**
 * Created by kangdaye on 16/5/20.
 */
app.service('identityService',["$http", function($http) {
    this.certification = function (postData,callback) {
        $http.post(servicePath + 'member/certification',postData).success(callback);
    };
}]);

