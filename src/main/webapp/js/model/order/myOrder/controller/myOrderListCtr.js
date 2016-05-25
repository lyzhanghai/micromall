/**
 * Created by kangdaye on 16/5/24.
 */
app.controller('myOrderListCtr',["$scope","$rootScope","myOrderListService","orderCacheFactory","confirmFactory","messageFactory",function($scope,$rootScope,myOrderListService,orderCacheFactory,confirmFactory,messageFactory) {
    var empty = false;
     $scope.applyEfundMoadl = false;
    $scope.getData = {
        status : 0,
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

    $scope.navTabClick = function(id){
        $scope.getData.status = id;
        $scope.getData.page = 1;
        empty = false;
        $scope.listData = [];
        $scope.load();
    };

    $scope.orderClose = function(orderNo,index){
        myOrderListService.closeOrder({
            orderNo : orderNo
        },function(){
            messageFactory({text : '关闭订单成功'});
            $scope.listData.splice(index,1);
        })
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
