/**
 * Created by kangdaye on 16/5/15.
 */
app.factory('userInfoCacheFactory', function($http) {
    return {
        navTab : [
            {id:1,name:'普通会员'},
            {id:2,name:'分销会员'}
        ]
    }
});
