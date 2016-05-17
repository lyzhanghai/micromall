app.controller('userInfoCtr',["$scope","userInfoCacheFactory","userInfoService", function($scope,userInfoCacheFactory,userInfoService) {
    $scope.navTab = userInfoCacheFactory.navTab;

    $scope.navTabClick = function(id){
        $scope.navTabSelectId = id;
    };

    userInfoService.userInfo(function(data){
        console.log(data)
    });

    $scope.navTabClick($scope.navTab[0].id);
}]);
