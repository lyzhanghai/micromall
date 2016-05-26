/**
 * Created by kangdaye on 16/5/24.
 */
app.service('createOrderService',["$http", function($http) {
    this.getCartGoods = function (postData,callback) {
        $http.post(servicePath + 'cart/settle',postData).success(callback);
    };

    this.calculateFreight = function (postData,callback) {
        $http.post(servicePath + 'cart/settle/calculateFreight',postData).success(callback);
    };

    this.buy = function (postData,callback) {
        $http.post(servicePath + 'cart/settle/buy',postData).success(callback);
    };
}]);
