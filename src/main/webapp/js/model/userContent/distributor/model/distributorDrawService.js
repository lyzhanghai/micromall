/**
 * Created by kangdaye on 16/5/18.
 */
app.service('distributorDrawService',["$http", function($http) {
    this.apply = function (postData,callback) {
        $http.post(servicePath + 'withdraw/apply',postData).success(callback);
    };
}]);