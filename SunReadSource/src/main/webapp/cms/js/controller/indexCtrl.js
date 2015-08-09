ctrls.controller('indexCtrl', ['$rootScope', '$scope', '$location', '$window', function( $rootScope, $scope, $location, $window ) {
    
    // Route map regexp
    $scope.routeMap = {
        book: /\/book/,
        objectivequestion: /\/objectivequestion/,
        subjectivequestion: /\/subjectivequestion/,
        region: /\/region/,
        edugroup: /\/edugroup/,
        school: /\/school/,
        schoolDistrict: /\/schoolDistrict/,
        clazz: /\/clazz/,
        teacher: /\/teacher/,
        student: /\/student/,
        gift: /\/gift/,
        action: /\/action/,
        semester: /\/semester/,
        sysAdmin: /\/sysAdmin/,
        schoolAdmin: /\/schoolAdmin/,
        schoolSuperAdmin: /\/schoolSuperAdmin/,
    }
    
    
    // Update the nav bar active
    $scope.isActive = function(routeRegexp) {
        return routeRegexp.test( $location.path() );
    }
}]);