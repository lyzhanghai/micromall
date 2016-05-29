/**
 * Created by kangdaye on 16/5/15.
 */
app.factory('distributorCacheFactory', function($http) {
    return {
        myDistributorNavTab : [
            {name:'全部'},
            {level:'lv1',name:'一级'},
            {level:'lv2',name:'二级'}
        ],
       recordTab : [
           {name : '全部'},
           {status : 'through',name : '已通过'},
           {status : 'not_through',name : '未通过'},
           {status : 'audit',name : '审核中'}
       ]
    }
});
