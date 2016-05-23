/**
 * Created by kangdaye on 16/5/15.
 */
app.controller('distributorDrawCtr',["$scope","$rootScope","distributorDrawService", function($scope,$rootScope,distributorDrawService) {
    $scope.getData = {
        channel : 'WECHAT'
    };

    $scope.submit = function(){
        distributorDrawService.apply($scope.getData,function(){
            location.href = $rootScope.prefix + 'userContent/distributor/distributorRecord.html';
        });
    };
}]);
