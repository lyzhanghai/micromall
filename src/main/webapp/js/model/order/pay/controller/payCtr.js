app.controller('payCtr',["$scope","$rootScope","$stateParams","userService","$compile","historyFactory",function($scope,$rootScope,$stateParams,userService,$compile,historyFactory) {
    // var orderNum = $stateParams.orderNum;
    //
    // $rootScope.loading = false;
    //
    // $scope.price = $stateParams.price;
    // $scope.text = '';
    // $scope.payOption = '';
    //
    //
    // function onBridgeReady(datas){
    //     WeixinJSBridge.invoke('getBrandWCPayRequest', {
    //             appId : "wxa66a636535c987db",     //公众号名称，由商户传入
    //             timeStamp: datas.timeStamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
    //             nonceStr: datas.nonceStr, // 支付签名随机串，不长于 32 位
    //             package: 'prepay_id=' + datas.prepay_id, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
    //             signType: 'MD5', // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
    //             paySign: datas.paySign // 支付签名
    //         },
    //         function(res){
    //             if(res.err_msg == "get_brand_wcpay_request:ok" || res.err_msg == "get_brand_wcpay_request:cancel" ) {  //如果付款成功或者取消付款都去订单详情页面
    //                 //location.href = $rootScope.prefix + 'order/statusDetail&' + orderNum;
    //                 historyFactory.back();
    //             }else{
    //                 alert('发生未知错误');
    //             }
    //         }
    //     );
    // }
    //
    // function bindEvent(data){
    //     if (typeof WeixinJSBridge == "undefined"){
    //         if( document.addEventListener ){
    //             document.addEventListener('WeixinJSBridgeReady', function(){
    //                 onBridgeReady(data)
    //             }, false);
    //         }else if (document.attachEvent){
    //             document.attachEvent('WeixinJSBridgeReady', function(){
    //                 onBridgeReady(data)
    //             });
    //             document.attachEvent('onWeixinJSBridgeReady', function(){
    //                 onBridgeReady(data)
    //             });
    //         }
    //     }else{
    //         onBridgeReady(data);
    //     }
    // }
    //
    // $scope.submit = function(){
    //     var hrefCode = location.href;
    //     var hrefCodeIndex = hrefCode.indexOf('code') + 5;
    //     var hrefCodeIndex2 = hrefCode.indexOf('&state');
    //     userService.h5({
    //         platform:'auth',
    //         code:hrefCode.substr(hrefCodeIndex,hrefCodeIndex2 - hrefCodeIndex)
    //     },function(data){
    //         userService.h5({
    //             platform : 'pay',
    //             orderNum : orderNum,
    //             url : location.href.split('#')[0],
    //             openId:data.model.openid
    //         },function(data){
    //             $rootScope.loading = false;
    //             bindEvent(data.model)
    //         });
    //     });
    // };


}]);