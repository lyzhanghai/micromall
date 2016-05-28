/**
 * Created by kangdaye on 16/5/15.
 */
app.factory('userInfoCacheFactory', function($http) {
    return {
        navTab : [
            {id:1,name:'普通会员'},
            {id:2,name:'分销会员'}
        ],
        orderState : [
            {href : 'order/myOrder/myOrderList.html',key : "waitPay",name : '待付款',icon : 'icon-daifukuan'},
            {href : 'order/myOrder/myOrderList.html',key : "waitDelivery",name : '待发货',icon : 'icon-daifahuo'},
            {href : 'order/myOrder/myOrderList.html',key : "waitReceive",name : '待收货',icon : 'icon-daishouhuo'},
            {href : 'order/myOrder/myOrderList.html',key : "complete",name : '已完成',icon : 'icon-yiwanchengdingdan'},
            {href : 'order/myOrder/myOrderList.html',key : "refund_closed",name : '退款/取消',icon : 'icon-tixian'}
        ]
    }
});
