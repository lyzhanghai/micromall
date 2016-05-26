/**
 * Created by kangdaye on 16/5/20.
 */
app.controller('siteListCtr',["$scope","$stateParams","siteListService","messageFactory", function($scope,$stateParams,siteListService,messageFactory) {
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
        if($stateParams.isSelectAddress){
            sessionStorage.selectAddress = JSON.stringify(item);
            window.history.back();
            return;
        }
        if(item.defaul){
            return;
        }
        siteListService.defaulAddress(item,function(){
            messageFactory({text : '设为默认地址成功'});
            $scope.listData.forEach(function(selectItem){
                if(selectItem.id != id){
                    selectItem.defaul = false;
                }
            });
        })
    };
}]);
