/**
 * Created by kangdaye on 16/5/15.
 */
app.factory('myOrderCacheFactory', function($http) {
    return {
        myOrderListNavTab : [
            {id:0,name:'待付款'},
            {id:1,name:'待发货'},
            {id:2,name:'待收货'},
            {id:3,name:'已完成'},
            {id:4,name:'已取消'}
        ]
    }
});