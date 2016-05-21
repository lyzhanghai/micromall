/**
 * Created by kangdaye on 16/5/15.
 */
app.controller('setInfoCtr',["$scope","$rootScope","$timeout","Upload","messageFactory", function($scope,$rootScope,$timeout,Upload,messageFactory) {
    var watchUserInfoData = $scope.$watch('userInfoData',function(newVal){
        if(newVal){
            $scope.userInfo = angular.extend({},$rootScope.userInfoData);
            console.log($scope.userInfo);
        }
    });

    $scope.$on('$destroy', function(){
        watchUserInfoData();
    });

    $scope.upload = function (file) {
        Upload.upload({
            url: 'upload/url',
            data: {file: file, 'username': $scope.username}
        }).then(function (resp) {
            console.log('Success ' + resp.config.data.file.name + 'uploaded. Response: ' + resp.data);
        }, function (resp) {
            console.log('Error status: ' + resp.status);
        }, function (evt) {
            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
            console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
        });
    };

}]);
