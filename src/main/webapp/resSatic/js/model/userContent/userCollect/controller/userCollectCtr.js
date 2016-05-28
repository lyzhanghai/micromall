/**
 * Created by kangdaye on 16/5/15.
 */
app.controller('userCollectCtr',["$scope","userCollectService","messageFactory", function($scope,userCollectService,messageFactory) {
    $scope.listData = [];

    userCollectService.favoriteList(function(data){
        $scope.listData = data.data;
    });

    $scope.deleteItem = function(goodsId,index){
        userCollectService.favoriteDeleteItem({
            goodsId : goodsId
        },function(){
            $scope.listData.splice(index,1);
            messageFactory({text : '删除成功'});
        })
    };

    $scope.deleteAll = function(goodsId,index){
        userCollectService.favoriteDeleteAll(function(){
            $scope.listData = [];
            messageFactory({text : '删除全部成功'});
        })
    }
}]);
