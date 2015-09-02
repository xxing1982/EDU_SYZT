ctrls.controller("statisticsClassesController", ['$rootScope', '$scope', 'SumStatistic', function ($rootScope, $scope, SumStatistic) {

    // The functin to get sumStatistic 
    $scope.handle.getSumStatistic = function ( grade, campusId ) {
        $scope.SumStatistic = SumStatistic.get( {grade: grade, campusId: campusId}, function(){
            
            // Apply classes
            $scope.classes = $scope.SumStatistic.classSums;
            
            // Apply classCategoryData
            if ($scope.handle.renderCategories) {
                $scope.handle.renderCategories($scope.SumStatistic);
            }
            
        });
    }
    
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
    campusFilters._3campus.hidden = false;
    
    
}]);