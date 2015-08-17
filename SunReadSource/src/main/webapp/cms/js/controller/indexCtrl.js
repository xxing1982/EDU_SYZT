ctrls.controller('indexCtrl', ['$rootScope', '$scope', '$location', '$window', function( $rootScope, $scope, $location, $window ) {
    
    // Route map regexp
    $scope.routeMap = {
        index: "/index",
        book: "/book",
        objectivequestion: "/objectivequestion",
        subjectivequestion: "/subjectivequestion",
        region: "/region",
        edugroup: "/edugroup",
        school: "/school",
        schoolDistrict: "/schoolDistrict",
        clazz: "/clazz",
        teacher: "/teacher",
        student: "/student",
        gift: "/gift",
        action: "/action",
        semester: "/semester",
        sysAdmin: "/sysAdmin",
        schoolAdmin: "/schoolAdmin",
        schoolSuperAdmin: "/schoolSuperAdmin",
    }
    
    
    // Update the nav bar active
    $scope.isActive = function(routeRegexp) {
        if (routeRegexp == $location.path()) {
            return true;
        }
    }


    //控制显示
    $scope.showMap = {
        index: true,
        book: true,
        objectivequestion: true,
        subjectivequestion: true,
        region: true,
        edugroup: true,
        school: true,
        schoolDistrict: true,
        clazz: true,
        teacher: true,
        student: true,
        gift: true,
        action: true,
        semester: true,
        sysAdmin: true,
        schoolAdmin: true,
        schoolSuperAdmin: true,
    }
    if (sessionStorage.getItem("cmsRoleId") == 6) {

    }else if (sessionStorage.getItem("cmsRoleId") == 7) {
        
    }else if (sessionStorage.getItem("cmsRoleId") == 8) {
        $scope.showMap.book = false;
        $scope.showMap.objectivequestion = false;
        $scope.showMap.subjectivequestion = false;
        $scope.showMap.region = false;
        $scope.showMap.edugroup = false;
        $scope.showMap.school = false;
        $scope.showMap.schoolDistrict = false;
        $scope.showMap.clazz = false;
        $scope.showMap.action = false;
        $scope.showMap.sysAdmin = false;
        $scope.showMap.schoolSuperAdmin = false;
    }else if (sessionStorage.getItem("cmsRoleId") == 9) {
        $scope.showMap.book = false;
        $scope.showMap.objectivequestion = false;
        $scope.showMap.subjectivequestion = false;
        $scope.showMap.region = false;
        $scope.showMap.edugroup = false;
        $scope.showMap.school = false;
        $scope.showMap.schoolDistrict = false;
        $scope.showMap.clazz = false;
        $scope.showMap.action = false;
        $scope.showMap.sysAdmin = false;
        $scope.showMap.schoolSuperAdmin = false;
        $scope.showMap.schoolAdmin = false;
    }
}]);