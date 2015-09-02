ctrls.controller("statisticsStudentsController", ['$scope', 'Hotclazz', 'Order', 'Sortable', function ($scope, Hotclazz, Order, Sortable) {

    // Filters cache
    var districtFilters = $scope.filtersGroups[0].filters,
        campusFilters = $scope.filtersGroups[1].filters,
        classFilters = $scope.filtersGroups[2].filters;
    
    // Hidden or show some filter
    classFilters._2class.hidden = false;
    campusFilters._3campus.hidden = false;
        
    // Get the orders pagable by teacher classId
    // The best practice of loadable
    // Create a pageable entity of actions
    $scope.orderLoadable = new Loadable();

    // Initlizate the orderLoadable in parent controller
    $scope.handle.initOrderLoadable = function(currentClassId, sortBy, direction){

        // Set the paramenters of sort
        $scope.sortable.parameter = currentClassId;
        
        // Set the parameters of loadable
        $scope.orderLoadable.size = 5;
        $scope.orderLoadable.page = 0;

        // Set the $resource arguments like {by: "books"}
        $scope.orderLoadable.arguments = { by: 'clazzs', id: currentClassId, 
                                           sortBy: sortBy ? sortBy : 'point', 
                                           direction: direction ? direction :'DESC' };

        // Build the loadable object
        $scope.orderLoadable.build(Order);

        // The index of the entities
        var index = 0;

        // Show the first page
        $scope.orderLoadable.get();
    };
    
    // The instance of sortable
    $scope.sortable = new Sortable('point', 'DESC', $scope.handle.initOrderLoadable);
    
}]);