/**
 * Created by kangdaye on 16/5/24.
 */
app.service('payService',["$http", function($http) {
    this.detail = function (postData,callback) {
        $http.post(servicePath + 'my_orders/details',postData).success(callback);
    };

    this.confirmDelivery = function (postData,callback) {
        $http.post(servicePath + 'my_orders/confirm_delivery',postData).success(callback);
    };

}]);
