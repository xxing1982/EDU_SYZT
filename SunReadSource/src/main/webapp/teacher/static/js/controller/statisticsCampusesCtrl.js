ctrls.controller("statisticsCampusesController", [ '$scope', 'Loadable', 'CampusOrder', 'Sortable',
                                                   function ($scope, Loadable, CampusOrder, Sortable) {
    
    // Filters cache
    var districtFilters = $scope.filtersGroups[0].filters,
        campusFilters = $scope.filtersGroups[1].filters,
        classFilters = $scope.filtersGroups[2].filters;
    
    // Hidden or show some filter
    classFilters._2class.hidden = true;
    campusFilters._3campus.hidden = true;
    
    // Get the orders pagable by teacher classId
    // The best practice of loadable
    // Create a pageable entity of actions
    $scope.campusOrderLoadable = new Loadable();

    // Initlizate the campusOrderLoadable in parent controller
    $scope.handle.initCampusOrderLoadable = function(parameter, sortBy, direction){

        // Set the paramenters of sort
        $scope.sortable.parameter = parameter;
        
        // Set the parameters of loadable
        $scope.campusOrderLoadable.size = 5;
        $scope.campusOrderLoadable.page = 0;

        // Set the $resource arguments like {by: "books"}
        if (parameter.schoolDistrictId) {
        $scope.campusOrderLoadable.arguments = { by: 'grade', id: parameter.grade, 
                                           sortBy: sortBy ? sortBy : 'point', 
                                           direction: direction ? direction :'DESC',
                                           schoolDistrictId: parameter.schoolDistrictId };
        }
        if (parameter.eduGroupId) {
        $scope.campusOrderLoadable.arguments = { by: 'grade', id: parameter.grade, 
                                           sortBy: sortBy ? sortBy : 'point', 
                                           direction: direction ? direction :'DESC',
                                           eduGroupId: parameter.eduGroupId };
        }

        // Build the loadable object
        $scope.campusOrderLoadable.build(CampusOrder);

        // The index of the entities
        var index = 0;

        // Show the first page
        $scope.campusOrderLoadable.get();
    };
    
    // The instance of sortable
    $scope.sortable = new Sortable('point', 'DESC', $scope.handle.initCampusOrderLoadable);
    
}]);