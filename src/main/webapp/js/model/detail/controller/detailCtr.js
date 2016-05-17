/**
 * Created by kangdaye on 16/5/15.
 */
app.controller('detailCtr',["$scope","$rootScope","$stateParams","detailService","messageFactory", function($scope,$rootScope,$stateParams,detailService,messageFactory) {
   var defaultData = {
      goodsId : $stateParams.goodsId
   };
   var async = false;

   $scope.detailData = {};

   detailService.detailData(defaultData,function (data) {
      $scope.detailData = data.data;
   });

   $scope.addFavorite  = function(){
      if(!async){
         async = true;
         detailService.addFavorite(defaultData,function (data) {
            $scope.detailData.favorite = !$scope.detailData.favorite;
            messageFactory({text : '收藏成功'});
            async = false;
         });
      }
   }
}]);
