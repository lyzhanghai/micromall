/**
 * Created by kangdaye on 16/5/20.
 */
app.service('shopCartService',["$http", function($http) {
    this.cartList = function (callback) {
        $http.post(servicePath + 'cart/list').success(callback);
    };

    this.cartAdd = function (postData,callback) {
        $http.post(servicePath + 'cart/join',postData).success(callback);
    };

    this.cartUpNum= function (postData,callback) {
        $http.post(servicePath + 'cart/update_buyNumber',postData).success(callback);
    };

    this.cartDelete= function (postData,callback) {
        $http.post(servicePath + 'cart/delete',postData).success(callback);
    };

    this.cartDeleteAll= function (callback) {
        $http.post(servicePath + 'cart/delete_all').success(callback);
    };
}]);
