/**
 * Created by kangdaye on 16/5/15.
 */
app.service('userCollectService',["$http", function($http) {
    this.favoriteList = function (callback) {
        $http.get(servicePath + 'favorite/list').success(callback);
    };

    this.favoriteDeleteItem = function (postData,callback) {
        $http.post(servicePath + 'favorite/delete',postData).success(callback);
    };

    this.favoriteDeleteAll = function (callback) {
        $http.post(servicePath + 'favorite/delete_all').success(callback);
    };

}]);
