/**
 * Created by chenmingkang on 16/3/1.
 */
;(function(){
    'use strict';
    angular.module('cz-cache',[]).factory('cacheFactory',['$cacheFactory','$rootScope','$window','$timeout', function($cacheFactory,$rootScope,$window,$timeout) {
        var myCache = function(){
            return $cacheFactory('myData');
        }();

        function setCache(datas){
            var scope =  datas.scope || $rootScope.$new();
            if($rootScope.cache){
                scope.$on('$destroy', function() {
                    //$timeout(function(){
                        if(scope.asyns){   //可能在加载数据的时候点下一页，数据还没返回 page还没＋＋，所以就加1
                            if(datas.data.page){
                                datas.data.page++;
                            }

                            if(datas.data.getData && datas.data.getData.page){
                                datas.data.getData.page++;
                            }
                        }
                        datas.data.scrollTop = window.scrollY;
                        myCache.put(datas.name,datas.data);
                    //},100)
                });
            }
        };

        function getCache(name){
            return myCache.get(name)
        };

        return {
            set : setCache,
            get : getCache,
            removeAll : myCache.removeAll
        }
    }])
}());
