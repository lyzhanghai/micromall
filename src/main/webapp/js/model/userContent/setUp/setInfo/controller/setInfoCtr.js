/**
 * Created by kangdaye on 16/5/15.
 */
app.controller('setInfoCtr',["$scope","$rootScope","$timeout","messageFactory", function($scope,$rootScope,$timeout,messageFactory) {
    var watchUserInfoData = $scope.$watch('userInfoData',function(newVal){
        if(newVal){
            $scope.userInfo = angular.extend({},$rootScope.userInfoData);
            console.log($scope.userInfo);
        }
    });


    $scope.$on('$destroy', function(){
        watchUserInfoData();
    });

}]);
