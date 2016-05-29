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
            {hrefStatus : 0,key : "waitPay",name : '待付款',icon : 'icon-daifukuan'},
            {hrefStatus : 1,key : "waitDelivery",name : '待发货',icon : 'icon-daifahuo'},
            {hrefStatus : 2,key : "waitReceive",name : '待收货',icon : 'icon-daishouhuo'},
            {hrefStatus : 3,key : "complete",name : '已完成',icon : 'icon-yiwanchengdingdan'},
            {hrefStatus : 4,key : "refund_closed",name : '退款/取消',icon : 'icon-tixian1'}
        ]
    }
});
