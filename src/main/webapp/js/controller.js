//Main
app.controller('mainController',["$scope","$rootScope","userInfoService", function($scope,$rootScope,userInfoService) {
    $rootScope.loading = false;  //加载

    $rootScope.prefix = "/";

    $rootScope.userInfoData = {};

    userInfoService.userInfo(function(data){
        $rootScope.userInfoData = data.data;
    });

}]);
