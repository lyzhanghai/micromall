/**
 * Created by kangdaye on 16/5/24.
 */
app.service('payService',["$http", function($http) {
    this.pay = function (postData,callback) {
        $http.post(servicePath + 'order/pay',postData).success(callback);
    };
}]);
