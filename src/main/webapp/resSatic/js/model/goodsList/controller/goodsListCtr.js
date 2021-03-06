/**
 * Created by kangdaye on 16/5/15.
 */
app.controller('goodsListCtr',["$scope","$rootScope","$stateParams","goodsListCacheFactory","goodsListService", function($scope,$rootScope,$stateParams,goodsListCacheFactory,goodsListService) {
   var empty = false;
   var async = false;

   $scope.bannerConfData = {};
   $scope.tabData = goodsListCacheFactory.navTab;
   $scope.category = goodsListCacheFactory.category;
   $scope.listData = [];
   $scope.getData = {
      query : $stateParams.searchText,
      categoryId : $stateParams.categoryId,
      sort : 'volume_desc',
      page : 1,
      limit : 10
   };

   $scope.searchList = function () {
      location.href = $rootScope.prefix + "goodsList.html?searchText="+ $scope.search;
   };

   goodsListService.indexAdConfig(function (data) {
      $scope.bannerConfData = data.data.banner;
   });

   $scope.load = function(){
      if(!empty && !async){
         async = true;
         goodsListService.searchList($scope.getData,function (data) {
            if(data.data.length < $scope.getData.limit){
               empty = true;
            }
            data.data.forEach(function(item){
               $scope.listData.push(item);
            });
            async = false;
            $scope.getData.page++;
         });
      }
   };

   $scope.tabFilter = function(up,dowm){
      empty = false;
      $scope.getData.page = 1;
      $scope.listData = [];
      if($scope.getData.sort == up){
         $scope.getData.sort = dowm;
      }else{
         $scope.getData.sort = up;
      }
      $scope.load();
   };

}]);
