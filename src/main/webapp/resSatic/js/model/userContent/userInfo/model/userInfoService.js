app.service('userInfoService',["$http", function($http) {
    this.userInfo = function (callback) {
        $http.get(servicePath + 'member/userinfo').success(callback);
    };

    this.orderNum = function (callback) {
        $http.get(servicePath + 'my_orders/statistics').success(callback);
    };
}]);
