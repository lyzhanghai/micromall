/**
 * Created by kangdaye on 16/5/15.
 */
app.controller('detailCrt',["$scope","$rootScope","detailService", function($scope,$rootScope,detailService) {
   $scope.detailData = {};
   detailService.detailData({
      goodsId : 112
   },function (data) {
      $scope.detailData = data.data;
   })
}]);
