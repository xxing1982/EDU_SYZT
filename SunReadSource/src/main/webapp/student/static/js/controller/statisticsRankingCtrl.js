//statisticsRanking.js

ctrls.controller("statisticsRankingController", ['$scope', '$rootScope', 'Student', 'Hotreader', 'Loadable', function ($scope, $rootScope, Student, Hotreader, Loadable) {
    
    // Get student information first
    $scope.student = Student.get({id: $rootScope.id}, function(){
        
        // The best practice of loadable
        // Create a pageable entity of actions
        $scope.hotreaderLoadable = new Loadable();
        
        // Set the parameters of loadable
        $scope.hotreaderLoadable.size = 10;
        $scope.hotreaderLoadable.page = 0;
    
        // Set the $resource arguments like {by: "books"}
        $scope.hotreaderLoadable.arguments = { by: "clazz", id: $scope.student.clazzId, sortBy: "point" };
        
        // Build the loadable object
        $scope.hotreaderLoadable.build(Hotreader);
        
        // Show the first page
        $scope.hotreaderLoadable.get();
    });    
}]);