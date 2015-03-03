/**
 * Created by niels on 11/30/14.
 *
 * Controller for listing, adding and editing gastronomists
 */

// "Gastronomist profile" page
ctrls.controller("gastronomistsCtrl", ['$scope', '$http', '$location', '$routeParams', 'Gastronomist', 'Recipe', 'UserService', function ($scope, $http, $location, $routeParams, Gastronomist, Recipe, UserService) {  //have to load Company and UserService SERVICE

    $scope.editedGastronomist = null;  //edited gastronomist
    $scope.isEditing = false;
    $scope.user = UserService.user

    // Get specific gastronomist
    $scope.gastronomist = Gastronomist.get({username:$routeParams.id});
    $scope.recipes = Recipe.query();

    //Edit
    $scope.edit = function(){
        $scope.isEditing = true;

        $scope.editedGastronomist = angular.copy($scope.gastronomist)
    }

    $scope.abortEdit = function(){
        $scope.isEditing = false;

        $scope.editedGastronomist = null;
    }

    $scope.confirmEdit = function(){
        Gastronomist.update({username:$scope.gastronomist.username}, $scope.editedGastronomist,
            function(resp) {
                $scope.gastronomist = resp;  //set the newly updated gastronomist object to scope

                $scope.abortEdit();      //invoke abort so that elements are rendered accordingly & editedGastronomist set to NULL

                $("#gastronomist-edit-confirm").modal(); //show modal pop-up
            },
            function(resp) {
                // TODO: Post error message somehow
                console.log("Save edit failed..");
            }
        );
    }


    //Deletion
    $scope.delete = function() {
        $("#gastronomist-delete-confirm").modal();
    };

    $scope.confirmDelete = function(){

        Gastronomist.remove( {username:$scope.gastronomist.username},
            function() {
                UserService.logOut(function() {
                    $location.path("/"); // Navigate to front page
                });
            },
            function() {
                // TODO: Post error message somehow
                console.log("Deletion failed...");
            }
        );
    }

    $scope.createRecipe = function() {
        $location.path("/recipes/create");
    }

}]);

// "Create gastronomist profile" page
ctrls.controller("gastronomistCreationCtrl", ['$scope', '$http', "$location", 'Gastronomist', 'UserService', function ($scope, $http, $location, Gastronomist, UserService) {

    $scope.gastronomist = {};
    $scope.missingRequired = false;

    // Submit form
    $scope.submit = function(){
        console.log("Submitting");

        if($scope.gastronomist != null && $scope.gastronomist.name != ""){
            $scope.gastronomist.username = UserService.user.username;
            Gastronomist.save({}, $scope.gastronomist,
                function(response){
                    console.log("Success");

                    $("#gastronomist-created-confirm").modal(); //show modal pop-up
                },
                function(response){
                   console.log("Ups error");
                });
        }
        else{
            $scope.missingRequired = true;
        }
    }

    // Reset form values
    $scope.reset = function(){
        $scope.gastronomist = null;
        $scope.missingRequired = false;
    }

    $scope.finishCreation = function(){ //redirect to the front page
        console.log("Profile creation finished, redirect to the front page")

        $location.path("/"); // Navigate to front page
    }
}]);

ctrls.controller("listGastronomistsCtrl", ['$scope', '$http', "$location", 'Gastronomist', 'UserService', function ($scope, $http, $location, Gastronomist, UserService) {
    $scope.gastronomists = Gastronomist.query();
}]);

// Convert to date format
ctrls.filter('formatDate', function() {
    return function(input, format) {
        var date = moment(input);
        return date.format(format);
    };
});