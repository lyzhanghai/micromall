/**
 * Created by kangdaye on 16/5/20.
 */
app.controller('siteAddEditCtr',["$scope","siteAddEditService","messageFactory", function($scope,siteAddEditService,messageFactory) {
    $scope.toggleModal = function() {
        $scope.modalShown = !$scope.modalShown;
    };

    $scope.addressItemData = {};

    // if($scope.model != 'add'){
    //     userService.site({
    //         op:'getter',
    //         addressId:$scope.model
    //     },function(data){
    //         $scope.postData = data.model;
    //         $scope.postData.address = data.model.addressDetail;
    //         $scope.postData.op = 'edit';
    //         $scope.postData.site = $scope.postData.province + $scope.postData.city + $scope.postData.country;
    //         $scope.address.province = $scope.postData.province;
    //         $scope.address.city = $scope.postData.city;
    //         $scope.address.area = $scope.postData.country;
    //     })
    // }

    $scope.siteSelect =function(){ //选择地区成功
        $scope.addressItemData.site = $scope.address.province + $scope.address.city + $scope.address.area;
        $scope.addressItemData.province = $scope.address.province;
        $scope.addressItemData.city = $scope.address.city;
        $scope.addressItemData.county = $scope.address.area;
    };

    $scope.submit = function(){ //提交
        siteAddEditService.addressAdd($scope.addressItemData,function(){
            window.history.go(-1);
        });
    };
}]);
