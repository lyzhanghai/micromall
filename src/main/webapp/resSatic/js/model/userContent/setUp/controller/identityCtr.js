/**
 * Created by kangdaye on 16/5/15.
 */
app.controller('identityCtr',["$scope","$rootScope","Upload","messageFactory","identityService", function($scope,$rootScope,Upload,messageFactory,identityService) {
    $scope.getData = {};

    $scope.upload = function (model,file) {
        Upload.upload({
            url: servicePath + 'member/upload_certificate',
            data: {file: file, 'username': $scope.username}
        }).then(function (resp) {
            if(model === 1){
                $scope.getData.idCarImage1 = resp.data;
            }else{
                $scope.getData.idCarImage0 = resp.data;
            }
        });
    };

    $scope.submit = function(){
        identityService.certification($scope.getData,function(){
            messageFactory({text : '正在提交审核'});
            $rootScope.userInfoData.certifiedInfo.status = 0;
        });
    };
}]);
