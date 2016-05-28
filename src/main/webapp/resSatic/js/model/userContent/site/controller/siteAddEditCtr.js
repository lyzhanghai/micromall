/**
 * Created by kangdaye on 16/5/20.
 */
app.controller('siteAddEditCtr',["$scope","$stateParams","siteAddEditService","messageFactory", function($scope,$stateParams,siteAddEditService,messageFactory) {
    var id = $stateParams.id;

    $scope.toggleModal = function() {
        $scope.modalShown = !$scope.modalShown;
    };

    $scope.addressItemData = {
        defaul : false
    };
    $scope.address = {};

    if(id){
        $scope.addressItemData.id = id;
        siteAddEditService.addressGet($scope.addressItemData,function(data){
            $scope.addressItemData = data.data;
            angular.extend($scope.address,$scope.addressItemData);
        });
    }

    $scope.siteSelect =function(){ //选择地区成功
        angular.extend($scope.addressItemData,$scope.address);
        $scope.toggleModal();
    };

    $scope.submit = function(){ //提交
        if(id){
            siteAddEditService.addressUpdate($scope.addressItemData,function(){
                window.history.go(-1);
            });
        }else{
            siteAddEditService.addressAdd($scope.addressItemData,function(){
                window.history.go(-1);
            });
        }
    };
}]);
