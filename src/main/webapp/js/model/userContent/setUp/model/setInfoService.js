/**
 * Created by kangdaye on 16/5/20.
 */
app.service('setInfoService',["$http", function($http) {
    this.updateBasisinfo = function (postData,callback) {
        $http.post(servicePath + 'member/update_basisinfo',postData).success(callback);
    };
}]);

