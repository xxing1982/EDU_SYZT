ctrls.controller('headNavCtrl', ['$rootScope', '$scope', '$location', 'Teacher', function( $rootScope, $scope, $location, Teacher) {

    // Route map regexp
    $rootScope.routeMap = {
        index: /^\/$/,
        teachingCenter: /\/teachingCenter.*/,
        readingDynamic: /\/readingDynamic.*/,
        statisticsSummary: /\/statisticsSummary.*/,
    }

    // Get the information of teacher
    Teacher.get({id: $rootScope.id}, function(data){
        $rootScope.teacher = data
    });

    // Update the nav bar active
    $rootScope.isActive = function(routeRegexp) {
        return routeRegexp.test( $location.path() );
    }

    // The logout method
    $rootScope.logout = function(){
        delete $rootScope.id;
        delete $rootScope.teacher;
        delete sessionStorage.access_token;
        delete sessionStorage.teacherId;
        delete sessionStorage.length;
        location.reload();
    }
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

ctrls.controller('recommendNavCtrl', ['$rootScope', '$scope', '$location',
    function( $rootScope, $scope, $location) {
        $rootScope.routeMap= {
            quick: /\/teachingCenter\/addBook\/quick.*/,
            advanced: /\/teachingCenter\/addBook\/advanced.*/,
            popular: /\/teachingCenter\/addBook\/popular.*/,
            recommend: /\/teachingCenter\/addBook\/recommend.*/
        }

        // Update the nav bar active
        $rootScope.isActive = function(routeRegexp) {
            return routeRegexp.test( $location.path() );
        }
    }]);
