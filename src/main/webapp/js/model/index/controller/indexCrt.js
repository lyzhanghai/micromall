/**
 * Created by kangdaye on 16/5/15.
 */
app.controller('indexCrt',["$scope","$rootScope","indexService", function($scope,$rootScope,indexService) {
   $scope.indexConfData = [];
   $scope.getData = {
      sort : 'volume_desc',
      page : 1,
      limit : 10
   };

   indexService.indexAdConfig(function (data) {
      $scope.indexConfData = data.data;
   });

   $scope.searchList = function () {
      location.href = $rootScope.prefix + "goodsList.html?searchText="+ $scope.search;
   };
}]);
