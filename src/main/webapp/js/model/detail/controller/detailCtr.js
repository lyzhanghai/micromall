/**
 * Created by kangdaye on 16/5/15.
 */
app.controller('detailCtr',["$scope","$rootScope","$stateParams","detailService","messageFactory", function($scope,$rootScope,$stateParams,detailService,messageFactory) {
   var defaultData = {
      goodsId : $stateParams.goodsId
   };
   var async = false;

   $scope.buyDialog = false;
   $scope.goModel = 'addCart';
   $scope.detailData = {};
   $scope.getData = angular.extend({buyNumber : 1},defaultData);

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
   };

   $scope.numOption = function(model){      //＋＋＋＋  －－－－－
      if(model == 'redu'){
         if($scope.getData.buyNumber <= 0){
            return;
         }
         $scope.getData.buyNumber--;
      }else{
         $scope.getData.buyNumber++;
      }
   };

   $scope.shopChangeNum = function(){
      var res = new RegExp(/^(\d)*$/);
      if($scope.getData.buyNumber > $scope.detailData.inventory){    //是否比存量大
         $scope.getData.buyNumber = $scope.detailData.inventory;
      }

      if(!res.test($scope.getData.buyNumber)){  //是否是数字
         $scope.getData.buyNumber = 1;
      }
   };

   $scope.shopBlurNum = function(){
      $scope.getData.buyNumber = parseInt($scope.getData.buyNumber);
      if($scope.getData.buyNumber <= 0 || !$scope.getData.buyNumber){
         $scope.getData.buyNumber = 1;
      }
   };

   $scope.buyDialogToggle = function(){
      $scope.buyDialog = !$scope.buyDialog;
   };

   $scope.go = function(model){
      $scope.goModel = model;
      $scope.buyDialogToggle();
   };

   $scope.addCart = function(){
      detailService.addCart($scope.getData,function (data) {
         $scope.buyDialogToggle();
         messageFactory({text : '加入购物车成功'});
      });
   };
}]);
