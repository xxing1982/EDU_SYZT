ctrls.controller("statisticsController", ['$rootScope',function ($rootScope) {
    $rootScope.staticsticRouteMap = {
        students: /\/statistics\/students.*/,
        classes: /\/statistics\/classes.*/
    }
}]);