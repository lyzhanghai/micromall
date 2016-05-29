/**
 * Created by kangdaye on 16/5/15.
 */
app.controller('htlistCrt',["$scope","$rootScope","$stateParams","htlistService", function($scope,$rootScope,$stateParams,htlistService) {
   $scope.$parent.empty = false;
   $scope.$parent.async = false;
   $scope.$parent.listData = [];

   $scope.load = function(){
      if(!$scope.empty && !$scope.async){
         $scope.async = true;
         htlistService.list($scope.getData,function (data) {
            if(data.data.length < $scope.getData.limit){
               $scope.empty = true;
            }
            data.data.forEach(function(item){
               $scope.listData.push(item);
            });
            $scope.async = false;
            $scope.getData.page++;
         });
      }
   };

   $scope.tabFilter = function(up,dowm){
      $scope.empty = false;
      $scope.getData.page = 1;
      if($scope.getData.sort == up){
         $scope.getData.sort = dowm;
      }else{
         $scope.getData.sort = up;
      }
      $scope.load();
   };

   $scope.load();
}]);
