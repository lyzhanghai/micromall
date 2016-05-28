/**
 * Created by kangdaye on 16/5/24.
 */
app.controller('myOrderDetailCtr',["$scope","$rootScope","$stateParams","myOrderDetailService","confirmFactory","messageFactory",function($scope,$rootScope,$stateParams,myOrderDetailService,confirmFactory,messageFactory) {
    var defaultData = {
        orderNo : $stateParams.orderNo
    };
    $scope.orderDetail = {};


    myOrderDetailService.detail(defaultData,function(data){
        $scope.orderDetail = data.data;
    });

    $scope.confirmDelivery = function(){
        confirmFactory({
            scope:$scope,
            text:'确定收到物品？',
            option:{
                go:function(){
                    myOrderDetailService.confirmDelivery(defaultData,function(){
                        messageFactory({text : '确认收货成功'});
                        $scope.orderDetail.status = 3;
                    });
                }
            }
        });
    };
}]);
