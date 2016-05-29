/**
 * Created by kangdaye on 16/5/15.
 */
app.factory('goodsListCacheFactory', function() {
    return {
        navTab : [
            {name:'按销量',up:'volume_desc',dowm:'volume_asc'},
            {name:'按价格',up:'price_desc',dowm:'price_asc'},
            {name:'按上架',up:'time_desc',dowm:'time_asc'}
        ],
        category : [
            {id:101,name:'大米杂粮'},
            {id:102,name:'各地特产'},
            {id:103,name:'手作食材'},
            {id:104,name:'时令水果'},
            {id:105,name:'特价促销'}
        ]
    }
});
