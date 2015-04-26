ctrls.controller('indexCtrl', ['$rootScope', '$scope', '$location','Student', function( $rootScope, $scope, $location, Student ) {
    
    // Route map regexp
    $scope.routeMap = {
        index: /^\/$/,
        readingCenter: /\/readingCenter.*/,
        readingDynamic: /\/readingDynamic.*/,
        statisticsSummary: /\/statistics.*/,
        readingSea: /\/readingSea.*/,
        readingTraining: /\/readingTraining.*/,
        prizeCenter: /\/prizeCenter.*/,
    }
    
    
    // Update the nav bar active
    $scope.isActive = function(routeRegexp) {
        return routeRegexp.test( $location.path() );
    }
    
    // Get the information of student
    $rootScope.student = Student.get({id: $rootScope.id}, function(){});
}]);