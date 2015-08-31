ctrls.controller("statisticsClassesController", ['$rootScope', '$scope', function ($rootScope, $scope) {

    // Data get from server
    $scope.classes = [{
        name: '五年一班',
        rate: 80,
        num: 10,
        word: 1000
    }, {
        name: '五年二班',
        rate: 90,
        num: 20,
        word: 1000,
    }, {
        name: '五年三班',
        rate: 80,
        num: 19,
        word: 900,
    }, {
        name: '五年四班',
        rate: 70,
        num: 18,
        word: 800,
    }, {
        name: '五年五班',
        rate: 60,
        num: 17,
        word: 700,
    }, {
        name: '五年六班',
        rate: 50,
        num: 16,
        word: 600,
    }, {
        name: '五年九班',
        rate: 40,
        num: 15,
        word: 500,
    }];
    
    $rootScope.classesRouteMap = {
        coins: /\/statistics\/classes\/coins.*/,
        readings: /\/statistics\/classes\/readings.*/,
        categories: /\/statistics\/classes\/categories.*/
    };
    
        
    // Filters cache
    var districtFilters = $scope.filtersGroups[0].filters,
        campusFilters = $scope.filtersGroups[1].filters,
        classFilters = $scope.filtersGroups[2].filters;
    
    // Hidden or show some filter
    classFilters._2class.hidden = true;
}]);