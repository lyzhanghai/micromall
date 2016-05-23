/**
 * Created by kangdaye on 16/5/23.
 */
app.controller('distributorRecordCtr',["$scope","$rootScope","distributorRecordService", function($scope,$rootScope,distributorRecordService) {
    $scope.listData = [];

    distributorRecordService.records({},function(data){
        $scope.listData = data.data;
    });
}]);
