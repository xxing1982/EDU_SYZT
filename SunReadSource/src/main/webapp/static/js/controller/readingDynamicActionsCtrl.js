//readingDynamicActions.js

ctrls.controller("readingDynamicActionsController", ['$scope', '$stateParams','Pageable', 'Action', function ($scope, $stateParams, Pageable, Action) {
    
    // Show action with modal
    $scope.showAction = function(action){
        this.showAction.action = action;
        $('#myModal').modal('show');
    }
    
    // The best practice of pageable
    // Create a pageable entity of actions
    $scope.actionPageable = new Pageable();
    
    // Set the parameters of pageable
    $scope.actionPageable.size = 15;
    $scope.actionPageable.page = 1;
    
    // Set the $resource arguments like {by: "books"}
    $scope.actionPageable.arguments = {};
    
    // Set the startPage and length of number page array
    $scope.actionPageable.pageNumbers.startPage = 1;
    $scope.actionPageable.pageNumbers.content.length = 8;
    
    // Set the placeholder elements
    $scope.actionPageable.placeHolders.placeHoldersElement = {title: ""};
    
    // Build the pageable object
    $scope.actionPageable.build(Action);
    
    // Show the page 1
    $scope.actionPageable.showPage($stateParams.page === undefined ? 1 : $stateParams.page, function(){

        // Check whether modal is displayed
        if ($stateParams.index !== undefined){
            $scope.showAction($scope.actionPageable.entities.content[$stateParams.index]);
        }
    });
}]);