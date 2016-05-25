/**
 * Created by kangdaye on 16/5/24.
 */
app.service('createOrderService',["$http", function($http) {
    this.logistics = function (postData,callback) {
        $http.post(servicePath + 'my_orders/logistics',postData).success(callback);
    };
}]);
