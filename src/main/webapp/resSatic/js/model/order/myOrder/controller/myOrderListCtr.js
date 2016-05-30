/**
 * Created by kangdaye on 16/5/24.
 */
app.controller('myOrderListCtr',["$scope","$rootScope","$stateParams","myOrderListService","orderCacheFactory","confirmFactory","authorFactory","messageFactory",function($scope,$rootScope,$stateParams,myOrderListService,orderCacheFactory,confirmFactory,authorFactory,messageFactory) {
    var empty = false;
     $scope.applyEfundMoadl = false;
    $scope.getData = {
        status : $stateParams.status,
        page : 1,
        limit : 10
    };
    $scope.applyEfundItemData = {
        orderNo : 0,
        refundReason : ''
    };

    $scope.navTab = orderCacheFactory.myOrderListNavTab;
    $scope.async = false;
    $scope.listData = [];

    $scope.load = function(){
        if(empty || $scope.async){
            return
        }
        $scope.async = true;
        myOrderListService.ordersList($scope.getData,function(data){
            if(data.data.length < $scope.getData.limit){
                empty = true;
            }
            data.data.forEach(function(item){
                $scope.listData.push(item);
            });
            $scope.getData.page++;
            $scope.async = false;
        });
    };

    $scope.orderClose = function(orderNo,index){
        confirmFactory({
            scope:$scope,
            text:'确定要关闭订单？',
            option:{
                go:function(){
                    myOrderListService.closeOrder({
                        orderNo : orderNo
                    },function(){
                        messageFactory({text : '关闭订单成功'});
                        $scope.listData.splice(index,1);
                    });
                }
            }
        });
    };

    $scope.confirmDelivery = function(orderNo,index){
        confirmFactory({
            scope:$scope,
            text:'确定收到物品？',
            option:{
                go:function(){
                    myOrderListService.confirmDelivery({
                        orderNo : orderNo
                    },function(){
                        messageFactory({text : '确认收货成功'});
                        $scope.listData.splice(index,1);
                    });
                }
            }
        });
    };

    $scope.goPay = function(item){
        authorFactory({
            href : 'order/pay.html',
            data : {
                orderNo : item.orderNo,
                price : item.totalAmount
            }
        });
    };

    $scope.applyEfund = function(orderNo){
        $scope.applyEfundMoadl = true;
        $scope.applyEfundItemData = {
            orderNo : orderNo
        };
    };

    $scope.efundMoadlTot = function(){
        $scope.applyEfundMoadl = !$scope.applyEfundMoadl;
    };

    $scope.applyEfundSuc = function(){
        myOrderListService.applyRefund($scope.applyEfundItemData,function(){
            messageFactory({text : '退款申请提交成功'});
            $scope.navTabClick(4);
            $scope.efundMoadlTot();
        });
    };

    $scope.load();
}]);
