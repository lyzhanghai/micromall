//Main
app.controller('mainController',["$scope","$rootScope","$timeout", function($scope,$rootScope,$timeout) {
    $rootScope.loading = false;  //加载

    $rootScope.prefix = "/webapp/";

}]);
