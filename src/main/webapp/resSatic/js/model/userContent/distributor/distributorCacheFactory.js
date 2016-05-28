/**
 * Created by kangdaye on 16/5/15.
 */
app.factory('distributorCacheFactory', function($http) {
    return {
        myDistributorNavTab : [
            {name:'全部'},
            {level:'lv1',name:'一级'},
            {level:'lv2',name:'二级'}
        ]
    }
});
