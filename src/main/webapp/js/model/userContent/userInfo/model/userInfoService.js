app.service('userInfoService',["$http", function($http) {
    this.userInfo = function (callback) {
        $http.get(servicePath + 'member/userinfo').success(callback);
    };
}]);
