ctrls.controller("ingredientCtrl", ['$scope', '$http', '$location', '$routeParams', 'Ingredient', 'UserService', function ($scope, $http, $location, $routeParams, Ingredient, UserService) {

    $scope.ingredient = {};
    $scope.missingRequired = false;

    $scope.ingredient = Ingredient.get({ id:$routeParams.id });

    $scope.edit = function(){
        $scope.isEditing = true;

        $scope.editedIngredient = angular.copy($scope.ingredient)
    }

    $scope.abortEdit = function(){
        $scope.isEditing = false;

        $scope.editedIngredient = null;
    }

    $scope.confirmEdit = function(){
        Company.update({name:$scope.ingredient.name}, $scope.editedIngredient,
            function(resp) {
                $scope.ingredient = resp;

                $scope.abortEdit();

                $("#ingredient-edit-confirm").modal();
            },
            function(resp) {
                // TODO: Post error message somehow
                console.log("Save edit failed..");
            }
        );
    }


    //Deletion
    $scope.delete = function() {
        $("#ingredient-delete-confirm").modal();
    };

    $scope.confirmDelete = function(){

        Ingredient.remove( {name:$scope.ingredient.name},
            function() {
                UserService.logOut(function() {
                    $location.path("/");
                });
            },
            function() {
                // TODO: Post error message somehow
                console.log("Deletion failed...");
            }
        );
    }
}]);

ctrls.controller("ingredientCreationCtrl", ['$scope', '$http', "$location", 'Ingredient', 'UserService', function ($scope, $http, $location, Ingredient, UserService) {

    $scope.ingredient = {};
    $scope.missingRequired = false;

    // Submit form
    $scope.submit = function(){
        console.log("Submitting");

        if($scope.ingredient != null && $scope.ingredient.name != ""){
            $scope.ingredient. company = UserService.user.username;
            Ingredient.save({}, $scope.ingredient,
                function(response){
                    console.log("Success");
                    $location.path("/ingredient/" + response._id);
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
        $scope.ingredient = null;
        $scope.missingRequired = false;
    }

    $scope.finishCreation = function(){
        console.log("Profile creation finished, redirect to the front page")

        $location.path("/");
    }
}]);

ctrls.controller("searchIngredientCtrl", ['$scope', '$http', '$location', "Ingredient", "SaveIngredient", function ($scope, $http, $location, Ingredient, SaveIngredient) {

$scope.tmp = [];

$scope.tmp = [
        { name: 'Banana'},
        { name: 'Tomato'},
        { name: 'Ketchup'},
        { name: 'Meat'},
        { name: 'Fish'},
        { name: 'Egg'},
        { name: 'Orange'}];

$scope.search = function(name)
      {

        //$scope.tmp = Ingredient.get(name);

        $scope.tmp = [
        { name: 'Banana'},
        { name: 'Tomato'},
        { name: 'Ketchup'},
        { name: 'Meat'},
        { name: 'Fish'},
        { name: 'Egg'},
        { name: 'Orange'}];
        
        //--------------------------------------------


        $scope.ingredientlist = [];
        if ($scope.tmp.length > 10)
        {
            for (index = 0; index < 10; index++) {
                $scope.ingredientlist.push($scope.tmp[index]);
            }
        }
        else {
            $scope.ingredientlist = $scope.tmp;
        }
        };

$scope.loadMore = function ()
{
    var indexTmp = index + 10;
    if (indexTmp > $scope.tmp.length)
        indexTmp = $scope.tmp.length;
    for (index; index < indexTmp; index++) {
        $scope.ingredientlist.push($scope.tmp[index]);
    };

    $scope.toggleShow = function (data)
    {
        console.log(data);
        $location.path("/ingredientProfile");
    }
};
}]);

ctrls.controller("searchIngredientByCompanyCtrl", ['$scope', '$http', '$location', "Ingredient", "SaveIngredient", function ($scope, $http, $location, Ingredient, SaveIngredient) {

$scope.tmp = [];

$scope.searchCompany = function(company)
      {

        //$scope.tmp = Ingredient.get(company);

        $scope.tmp = [
        { name: 'Banana', company: 'Coca'},
        { name: 'Tomato', company: 'Coca'},
        { name: 'Ketchup', company: 'Coca'},
        { name: 'Meat', company: 'Coca'},
        { name: 'Fish', company: 'Coca'},
        { name: 'Egg', company: 'Coca'},
        { name: 'Orange', company: 'Coca'}];
        
        //--------------------------------------------


        $scope.ingredientlist = [];
        if ($scope.tmp.length > 10)
        {
            for (index = 0; index < 10; index++) {
                $scope.ingredientlist.push($scope.tmp[index]);
            }
        }
        else {
            $scope.ingredientlist = $scope.tmp;
        }
        };

$scope.loadMore = function ()
{
    var indexTmp = index + 10;
    if (indexTmp > $scope.tmp.length)
        indexTmp = $scope.tmp.length;
    for (index; index < indexTmp; index++) {
        $scope.ingredientlist.push($scope.tmp[index]);
    };
};
}]);