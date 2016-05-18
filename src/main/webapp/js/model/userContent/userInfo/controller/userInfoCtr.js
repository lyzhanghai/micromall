app.controller('userInfoCtr',["$scope","userInfoCacheFactory","userInfoService", function($scope,userInfoCacheFactory,userInfoService) {
    $scope.orderNum = {};
    $scope.navTab = userInfoCacheFactory.navTab;

    $scope.navTabClick = function(id){
        $scope.navTabSelectId = id;
    };

    userInfoService.orderNum(function(data){
        $scope.orderNum = data.data;
    });
    $scope.navTabClick($scope.navTab[0].id);
}]);
