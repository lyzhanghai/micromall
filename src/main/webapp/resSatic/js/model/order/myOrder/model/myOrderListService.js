/**
 * Created by kangdaye on 16/5/24.
 */
app.service('myOrderListService',["$http", function($http) {
    this.ordersList = function (postData,callback) {
        $http.post(servicePath + 'my_orders/orders',postData).success(callback);
    };

    this.closeOrder = function (postData,callback) {
        $http.post(servicePath + 'my_orders/close',postData).success(callback);
    };

    this.confirmDelivery = function (postData,callback) {
        $http.post(servicePath + 'my_orders/confirm_delivery',postData).success(callback);
    };

    this.applyRefund = function (postData,callback) {
        $http.post(servicePath + 'my_orders/apply_refund',postData).success(callback);
    };


}]);
