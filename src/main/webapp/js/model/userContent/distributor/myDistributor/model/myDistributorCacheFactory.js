/**
 * Created by kangdaye on 16/5/15.
 */
app.factory('myDistributorCacheFactory', function($http) {
    return {
        navTab : [
            {name:'全部'},
            {level:'lv1',name:'一级'},
            {level:'lv2',name:'二级'}
        ]
    }
});
