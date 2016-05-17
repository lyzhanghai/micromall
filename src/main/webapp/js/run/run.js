/**
 * Created by chenmingkang on 16/3/8.
 */

app.run(["$rootScope","$http","$timeout",function($rootScope,$http,$timeout) {
    $rootScope.$on('$stateChangeSuccess', function(evt, current, previous) {
        if(current.tabSelectIndex){   //埋点
            $rootScope.targetScope = current.tabSelectIndex;
        }
    });
}]);
