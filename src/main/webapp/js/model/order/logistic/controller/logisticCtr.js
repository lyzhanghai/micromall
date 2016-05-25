/**
 * Created by kangdaye on 16/5/24.
 */
app.controller('logisticCtr',["$scope","$stateParams","logisticsService",function($scope,$stateParams,logisticsService) {
    logisticsService.logistics({
        orderNo : $stateParams.orderNo
    },function(data){
        console.log(data);
    });
}]);
