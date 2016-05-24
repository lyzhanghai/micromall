/**
 * Created by kangdaye on 16/5/24.
 */
app.controller('myOrderListCtr',["$scope","$rootScope","myOrderListService","myOrderCacheFactory","confirmFactory","messageFactory",function($scope,$rootScope,myOrderListService,myOrderCacheFactory,confirmFactory,messageFactory) {
    var empty = false;

    $scope.getData = {
        status : 0,
        page : 1,
        limit : 10
    };
    $scope.navTab = myOrderCacheFactory.myOrderListNavTab;
    $scope.async = false;
    $scope.listData = [];

    $scope.load = function(){
        if(empty || $scope.async){
            return
        }
        $scope.async = true;
        myOrderListService.ordersList($scope.getData,function(data){
            if(data.data.length < $scope.getData.limit){
                empty = true;
            }
            data.data.forEach(function(item){
                $scope.listData.push(item);
            });
            $scope.getData.page++;
            $scope.async = false;
        });
    };

    $scope.navTabClick = function(id){
        $scope.getData.status = id;
        $scope.getData.page = 1;
        empty = false;
        $scope.listData = [];
        $scope.load();
    };
    
    $scope.load();
}]);
