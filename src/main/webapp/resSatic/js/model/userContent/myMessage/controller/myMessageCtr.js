/**
 * Created by kangdaye on 16/5/15.
 */
app.controller('myMessageCtr',["$scope","$rootScope","myMessageService", function($scope,$rootScope,myMessageService) {
    var getData = {
        page : 1,
        limit : 10
    };
    var async = false;

    $scope.listData = [];

    $scope.load = function(){
        if(async){
            return;
        }
        async = true;
        myMessageService.list(getData,function(data){
            data.data.forEach(function(item){
                $scope.listData.push(item);
            });
            if(data.data.length >= getData.limit){
                async = false;
            }
            getData.page++;
        });
    };

    $scope.load();
}]);
