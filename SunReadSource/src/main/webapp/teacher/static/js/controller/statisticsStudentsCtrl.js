ctrls.controller("statisticsStudentsController", ['$scope',function ($scope) {
    $scope.orders = [0, 1];
    
    // Filters cache
    var districtFilters = $scope.filtersGroups[0].filters,
        campusFilters = $scope.filtersGroups[1].filters,
        classFilters = $scope.filtersGroups[2].filters;
    
    // Hidden or show some filter
    classFilters._2class.hidden = false;
}]);