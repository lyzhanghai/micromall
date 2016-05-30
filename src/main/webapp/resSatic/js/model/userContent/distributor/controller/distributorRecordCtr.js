/**
 * Created by kangdaye on 16/5/23.
 */
app.controller('distributorRecordCtr',["$scope","$rootScope","distributorCacheFactory","distributorRecordService", function($scope,$rootScope,distributorCacheFactory,distributorRecordService) {
    $scope.listData = [];
    $scope.tab = distributorCacheFactory.recordTab;
    $scope.getData = {};

    $scope.selectTav = function(status){
        $scope.getData.status = status;
        distributorRecordService.records($scope.getData,function(data){
            $scope.listData = [];
            $scope.listData = data.data;
        });
    };

    $scope.selectTav($scope.tab[0].status);
}]);
