/**
 * Created by kangdaye on 16/5/24.
 */
app.controller('createOrderCtr',["$scope","$rootScope","$stateParams","createOrderService",function($scope,$rootScope,$stateParams,createOrderService) {
    $scope.createOrderData = {};
    $scope.leaveMessage = '';

    createOrderService.getCartGoods($stateParams,function(data){
        $scope.createOrderData = data.data;
        if(sessionStorage.selectAddress){
            data.data.address = JSON.parse(sessionStorage.selectAddress);
            sessionStorage.selectAddress = '';
        }
        if(data.data.address){
            $scope.calculateFreight();
        }
    });
    
    $scope.calculateFreight = function(){
        createOrderService.calculateFreight({
            addressId : $scope.createOrderData.address.id,
            settleId : $scope.createOrderData.settle.settleId
        },function(data){
            $scope.createOrderData.settle.freight = data.data.freight;
            $scope.createOrderData.settle.totalAmount = data.data.totalAmount;
        });
    };

    $scope.selectAddress = function(){
        location.href = $rootScope.prefix + 'userContent/site/list.html?isSelectAddress=true';
    };

    $scope.goPay = function(){
        createOrderService.buy({
            settleId : $scope.createOrderData.settle.settleId,
            leaveMessage : $scope.leaveMessage
        },function(data){
            var getData = {
                orderNo : data.data.orderNo,
                price : data.data.amount
            };
            var goUrl = location.origin + $rootScope.prefix + 'order/pay.html?' + angular.param(getData);
            location.href = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa66a636535c987db&redirect_uri='+ encodeURIComponent(goUrl) +'&response_type=code&scope=snsapi_base&state=123#wechat_redirect';
        });
    }
}]);
