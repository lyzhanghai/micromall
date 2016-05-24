/**
 * Created by kangdaye on 16/5/24.
 */
app.service('myOrderListService',["$http", function($http) {
    this.ordersList = function (postData,callback) {
        $http.post(servicePath + 'my_orders/orders',postData).success(callback);
    };
}]);
