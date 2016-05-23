/**
 * Created by kangdaye on 16/5/15.
 */
app.controller('distributorOrderCtr',["$scope","$rootScope","distributorOrderService", function($scope,$rootScope,distributorOrderService) {
    $scope.data = {};
    $scope.distributorTabList = {};
    $scope.tabSelectId = 'lv1';

    $scope.tab = [
        {id:'lv1',name:'一级分销商'},
        {id:'lv2',name:'二级分销商'}
    ];

    $scope.distributorListToggle = function(model){
        $scope.tabSelectId = model;
        $scope.distributorTabList = $scope.data[model];
    };

    distributorOrderService.distributorsStat(function(data){
        $scope.data = data.data;
        $scope.distributorListToggle($scope.tabSelectId);
    });
}]);
