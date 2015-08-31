ctrls.controller("statisticsStudentsController", ['$scope', 'Hotclazz', 'Order', function ($scope, Hotclazz, Order) {

    // Filters cache
    var districtFilters = $scope.filtersGroups[0].filters,
        campusFilters = $scope.filtersGroups[1].filters,
        classFilters = $scope.filtersGroups[2].filters;
    
    // Hidden or show some filter
    classFilters._2class.hidden = false;
    
    // Get the orders pagable by teacher classId
    // The best practice of loadable
    // Create a pageable entity of actions
    $scope.orderLoadable = new Loadable();

    // Initlizate the orderLoadable in parent controller
    $scope.handle.initOrderLoadable = function(currentClassId){

        // Set the parameters of loadable
        $scope.orderLoadable.size = 5;
        $scope.orderLoadable.page = 0;

        // Set the $resource arguments like {by: "books"}
        $scope.orderLoadable.arguments = { by: 'clazzs', id: currentClassId, sortBy: 'point', direction: 'DESC' };

        // Build the loadable object
        $scope.orderLoadable.build(Order);

        // The index of the entities
        var index = 0;

        // Show the first page
        $scope.orderLoadable.get();
    };

}]);