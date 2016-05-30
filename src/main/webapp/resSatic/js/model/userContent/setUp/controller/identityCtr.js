/**
 * Created by kangdaye on 16/5/15.
 */
app.controller('identityCtr',["$scope","$rootScope","Upload","messageFactory","identityService", function($scope,$rootScope,Upload,messageFactory,identityService) {
    var watch;

    $scope.isVlidate = !$scope.userInfoData.certifiedInfo || $scope.userInfoData.certifiedInfo.status == 9 || $scope.userInfoData.certifiedInfo.auditlog
    $scope.getData = {};

    $scope.upload = function (model,file) {
        if($scope.isVlidate){
            return;
        }
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

    watch = $scope.$watch('userInfoData.certifiedInfo',function(newVal){
        if(newVal){
            angular.extend($scope.getData,newVal);
        }
    });

    $scope.$on('$destroy', function(){
        watch();
    });

}]);
