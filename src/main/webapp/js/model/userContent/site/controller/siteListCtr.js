/**
 * Created by kangdaye on 16/5/20.
 */
app.controller('siteListCtr',["$scope","siteListService","messageFactory", function($scope,siteListService,messageFactory) {
    $scope.listData = [];
    
    siteListService.addressList(function(data){
        $scope.listData = data.data;
    });

    $scope.deleteItem = function(id,index){
        siteListService.delete({
            id : id
        },function(){
            $scope.listData.splice(index,1);
            messageFactory({text:'删除成功'});
        })
    };

    $scope.defaulAddress = function(item){
        siteListService.defaulAddress(item,function(){
            messageFactory({text : '设为默认地址成功'});
        })
    };
}]);
