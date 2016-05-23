/**
 * Created by kangdaye on 16/5/15.
 */
app.controller('myDistributorCtr',["$scope","$rootScope","myDistributorService","distributorCacheFactory", function($scope,$rootScope,myDistributorService,distributorCacheFactory) {
    var empty = false;
    $scope.async = false;
    $scope.distributorsData = {
        startData : {},
        listData : []
    };
    $scope.getData = {
        level : 'lv1',
        page : 1,
        limit : 10
    };

    $scope.navTab = distributorCacheFactory.myDistributorNavTab;

    myDistributorService.distributorsStat(function(data){
        $scope.distributorsData.startData = data.data;
    });

    $scope.load = function(){
        if(!$scope.async && !empty){
            $scope.async = true;
            myDistributorService.distributorsList($scope.getData,function(data){
                data.data.forEach(function(item){
                    $scope.distributorsData.listData.push(item);
                    if(data.data.length < $scope.getData.limit){
                        empty = true;
                    }
                    $scope.async = false;
                });
            });
        }
    };

    $scope.navTabClick = function(level){
        $scope.distributorsData.listData = [];
        empty = false;
        if(level){
            $scope.getData.level = level;
        }else{
            delete $scope.getData.level;
        }
        $scope.load();
    }
}]);
