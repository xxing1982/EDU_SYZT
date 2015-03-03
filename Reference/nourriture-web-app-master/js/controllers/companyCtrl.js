/**
 * Created by Pavel Prochazka on 09/12/14.
 */

// "Company profile" page
ctrls.controller("companyCtrl", ['$scope', '$http', "$location", '$routeParams', 'Company', 'Ingredient', 'UserService', function ($scope, $http, $location, $routeParams, Company, Ingredient, UserService) {  //have to load Company and UserService SERVICE

    $scope.editedCompany = null;  //edited company
    $scope.isEditing = false;

    // Get specific company
    $scope.company = Company.get({username:$routeParams.id});

    // Get ingredients
    $scope.ingredients = Ingredient.query({ company:$routeParams.id });

    //Edit
    $scope.edit = function(){
        $scope.isEditing = true;

        $scope.editedCompany = angular.copy($scope.company)
    }

    $scope.abortEdit = function(){
        $scope.isEditing = false;

        $scope.editedCompany = null;
    }

    $scope.confirmEdit = function(){
        Company.update({username:$scope.company.username}, $scope.editedCompany,
            function(resp) {
                $scope.company = resp;  //set the newly updated company object to scope

                $scope.abortEdit();      //invoke abort so that elements are rendered accordingly & editedCompany set to NULL

                $("#company-edit-confirm").modal(); //show modal pop-up
            },
            function(resp) {
                // TODO: Post error message somehow
                console.log("Save edit failed..");
            }
        );
    }


    //Deletion
    $scope.delete = function() {
        $("#company-delete-confirm").modal();
    };

    $scope.confirmDelete = function(){

        Company.remove( {username:$scope.company.username},
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

    $scope.addIngredient = function() {
        $location.path("/createIngredient");
    }

}]);

// "Create company profile" page
ctrls.controller("companyCreationCtrl", ['$scope', '$http', "$location", 'Company', 'UserService', function ($scope, $http, $location, Company, UserService) {
    $scope.company = {};
    $scope.missingRequired = false;
    $scope.username = UserService.user.username;

    // Submit form
    $scope.submit = function(){
        console.log("Submitting");

        if($scope.company != null && $scope.company.name != ""){
            $scope.company.username = UserService.user.username;
            Company.save({}, $scope.company,
                function(response){
                    console.log("Success");

                    $("#company-created-confirm").modal(); //show modal pop-up
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
        $scope.company = null;
        $scope.missingRequired = false;
    }

    $scope.finishCreation = function(){ //redirect to the front page
        console.log("Profile creation finished, redirect to the front page")

        window.location.href = "/"
    }

    $scope.logoUploadComplete = function(url) {
        $scope.company.logo = url;
        $scope.logoSrc = url + "?rn=" + new Date();
    }
}]);

// Convert to date format
ctrls.filter('formatDate', function() {
    return function(input, format) {
        var date = moment(input);
        return date.format(format);
    };
});