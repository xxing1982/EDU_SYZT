//personalProfile.js

ctrls.controller("personalProfileController", ['$scope', function ($scope) {
    $scope.editable = false;
    $scope.toggleEdit = function(){
        $scope.editable = !$scope.editable ;
    }
    
}]);