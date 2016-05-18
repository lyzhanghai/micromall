app.directive('back',['$timeout',function($timeout){
    return {
        restrict : 'EA',
        link : function(scope, elm, attrs) {
            $timeout(function(){
                scope.$on('$destroy', function(){
                    elm.off();
                });

                elm.bind('click',function(){
                    window.history.go(-1);
                });
            },0);
        }
    }
}]);