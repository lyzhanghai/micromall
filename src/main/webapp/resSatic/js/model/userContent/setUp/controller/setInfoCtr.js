/**
 * Created by kangdaye on 16/5/15.
 */
app.controller('setInfoCtr',["$scope","$rootScope","$timeout","Upload","messageFactory","setInfoService", function($scope,$rootScope,$timeout,Upload,messageFactory,setInfoService) {
    $scope.userInfo = {};
    var watchUserInfoData = $scope.$watch('userInfoData',function(newVal){
        if(newVal){
            angular.extend($scope.userInfo,$rootScope.userInfoData);
            console.log($scope.userInfo);
        }
    });

    $scope.$on('$destroy', function(){
        watchUserInfoData();
    });

    $scope.upload = function (file) {
        Upload.upload({
            url: servicePath + 'member/update_avatar',
            data: {file: file, 'username': $scope.username}
        }).then(function (resp) {
            $scope.userInfo.avatar = resp.data;
        });
    };

    $scope.submit = function(){
        setInfoService.updateBasisinfo($scope.userInfo,function(){
            $rootScope.userInfoData = angular.copy($scope.userInfo);
        })
    };
}]);
