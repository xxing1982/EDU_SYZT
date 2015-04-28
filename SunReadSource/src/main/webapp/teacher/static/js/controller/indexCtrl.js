ctrls.controller('headNavCtrl', ['$rootScope', '$scope', '$location', function( $rootScope, $scope, $location) {

    // Route map regexp
    $rootScope.routeMap = {
        index: /^\/$/,
        teachingCenter: /\/teachingCenter.*/,
        readingDynamic: /\/readingDynamic.*/,
        statisticsSummary: /\/statisticsSummary.*/,
    }
    
    
    // Update the nav bar active
    $rootScope.isActive = function(routeRegexp) {
        return routeRegexp.test( $location.path() );
    }
    
    // The logout method
    /*$rootScope.logout = function(){
        delete $rootScope.id;
        delete $rootScope.student;
        delete sessionStorage.access_token;
        delete sessionStorage.userId;
        delete sessionStorage.length;
        window.location.href="/";;
    }*/
}]);

ctrls.controller('leftNavCtrl', ['$rootScope', '$scope', '$location', 
    function( $rootScope, $scope, $location) {
        $rootScope.routeMapLeft = {
            myTask: /\/teachingCenter\/myTask.*/,
            myRecommend: /\/teachingCenter\/myRecommend.*/,
            myReward: /\/teachingCenter\/myReward.*/,
            myResource: /\/teachingCenter\/myResource.*/
        }

        // Update the nav bar active
        $rootScope.isActiveLeft = function(routeRegexp) {
            return routeRegexp.test( $location.path() );
        }
    }]);