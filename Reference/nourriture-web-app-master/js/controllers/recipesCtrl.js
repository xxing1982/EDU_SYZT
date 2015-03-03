/**
 * Created by niels on 11/30/14.
 *
 * Controller for listing, adding and editing recipes
 */

ctrls.controller("recipesCtrl", ['$scope', '$http', '$location', "Recipe", "Ingredient", "UserService", function ($scope, $http, $location, Recipe, Ingredient, UserService) {

    $scope.addrecipe = {};
    $scope.addrecipe.ingredients = [];
    $scope.addrecipe.instructions = [];
    $scope.addrecipe.fat = 0;
    $scope.addrecipe.carbs = 0;
    $scope.addrecipe.proteins = 0;
    $scope.addrecipe.calories = 0;
    $scope.addrecipe.author = "549421169309181831aedf20";
    $scope.tmp = [];
    $scope.ingredientslist = [];
    $scope.loadError = false;
    $scope.difficultys = [1, 2, 3, 4, 5];
    $scope.loadshow = true;
    $scope.steps = 1;
    var index = 0;
    var stepIndex = 1;


    $scope.getNumber = function(num)
    {
        return new Array(num);
    }


    $scope.save = function(addrecipe) {
        console.log(addrecipe);
    if (addrecipe.title && addrecipe.description && addrecipe.instructions.length >= 1 && addrecipe.ingredients.length >= 1 && addrecipe.difficulty)
    {
    Recipe.save(addrecipe);
    $location.path("/recipes/search");
    }};

    $scope.search = function(name)
      {
        $scope.ingredientslist = [];
        var a = Ingredient.query({name:name}, function()
            {
                $scope.tmp = a;
                for (b = 0; b < $scope.tmp.length; b++)
                {
                    $scope.tmp[b].quantity = 1;
                }

                if ($scope.tmp.length > 10)
                {
                    $scope.loadshow = false;
                    for (index = 0; index < 10; index++) {
                        $scope.ingredientslist.push($scope.tmp[index]);
                    }
                }
                else {
                    $scope.loadshow = true;
                    $scope.ingredientslist = $scope.tmp;
                }
                });
    };
          

    $scope.toggleAdd = function (index) {
        console.log(index);
        if ($scope.addrecipe.ingredients.indexOf($scope.ingredientslist[index]) == -1)
        {
            var sIng = $scope.ingredientslist[index];

            // Select default quanity unit
            var defUnit = "g";
            if(sIng.type == "liquid") {
                defUnit = "dl"
            }

            // Compose ingredient
            var pIngredient = {
                name: sIng.name,
                original: sIng._id,
                quantity: 1,
                quantityUnit: defUnit,
            };
            $scope.addrecipe.ingredients.push(pIngredient);
        }
        else
        {
            $scope.addrecipe.ingredients[$scope.addrecipe.ingredients.indexOf($scope.ingredientslist[index])].quantity = $scope.addrecipe.ingredients[$scope.addrecipe.ingredients.indexOf($scope.ingredientslist[index])].quantity + 1;
        }
    };

    $scope.toggleDelete = function (index) {
        console.log(index);
        $scope.addrecipe.ingredients.splice(index, 1);
    };

    $scope.loadMore = function ()
    {
        var indexTmp = index + 10;
        if (indexTmp > $scope.tmp.length)
            indexTmp = $scope.tmp.length;
        for (index; index < indexTmp; index++) {
            $scope.ingredientslist.push($scope.tmp[index]);
        };
    };


    $scope.stepChange = function()
    {
        for (stepIndex; stepIndex > $scope.steps; stepIndex--) {
        $scope.addrecipe.instructions.pop();
        };
        stepIndex = $scope.steps;
    }

}]);

ctrls.controller("listRecipes", ['$scope', '$http', '$location', '$routeParams', "Recipe", function ($scope, $http, $location, $routeParams, Recipe) {
    $scope.recipes = Recipe.query();
}]);

ctrls.controller("searchRecipes", ['$scope', '$http', '$location', '$routeParams', "Recipe", function ($scope, $http, $location, $routeParams, Recipe) {

$scope.tmp = [];
$scope.loadshow = true;

$scope.search = function(titleId) {
    $location.search({ title:titleId });
}

$scope.doSearch = function(titleId) {

    $scope.tmp = Recipe.query({title: titleId}, function () {

        $scope.recipeslist = [];
        if ($scope.tmp.length > 10) {
            $scope.loadshow = false;
            for (index = 0; index < 10; index++) {
                $scope.recipeslist.push($scope.tmp[index]);
            }
        }
        else {
            $scope.loadshow = true;
            $scope.recipeslist = $scope.tmp;
        }
    });
}


$scope.loadMore = function ()
{
    var indexTmp = index + 10;
    if (indexTmp > $scope.tmp.length)
        indexTmp = $scope.tmp.length;
    for (index; index < indexTmp; index++) {
        $scope.recipeslist.push($scope.tmp[index]);
    };
};


$scope.toggleShow = function (data)
    {
        console.log(data);
        $location.path("/recipes/profile/" + data._id);
    }

    if($routeParams.title) {
        $scope.doSearch($routeParams.title);
        $scope.recipe = {
            name: $routeParams.title
        };
    }

}]);


ctrls.controller("profileRecipes", ['$scope', '$http', '$location', "$routeParams", "UserService", "Recipe", function ($scope, $http, $location, $routeParams, UserService, Recipe) {
$scope.Id = $routeParams.Id;
$scope.addrecipe = {};
$scope.user = UserService.user;

var tmp = Recipe.get({id:$scope.Id}, function(){
    $scope.addrecipe = tmp;
    $scope.steps = $scope.addrecipe.instructions.length;
});

$scope.toggleUpdate = function ()
    {
        $location.path("/recipes/update/" + $scope.Id);
    }

    $scope.toggleDelete = function ()
    {
        Recipe.delete({id:$scope.Id});
        $location.path("/recipes/search");
    }

}]);

ctrls.controller("updateRecipes", ['$scope', '$http', '$location', '$routeParams', "Recipe", "Ingredient", function ($scope, $http, $location, $routeParams, Recipe, Ingredient) {
$scope.Id = $routeParams.Id;
var tmp = Recipe.get({id:$scope.Id}, function(){
    $scope.addrecipe = tmp;
    $scope.steps = $scope.addrecipe.instructions.length;
});
    $scope.loadshow = true;
    $scope.tmp = [];
    $scope.ingredientslist = [];
    $scope.loadError = false;
    $scope.difficultys = [1, 2, 3, 4, 5];
    var index = 0;
    var stepIndex = 1;


    $scope.getNumber = function(num)
    {
        return new Array(num);
    }


    $scope.save = function(addrecipe) {
    console.log(addrecipe);
if (addrecipe.title && addrecipe.title != "" && 
    addrecipe.description && addrecipe.description != "" &&
    addrecipe.instructions.length >= 1 && addrecipe.ingredients.length >= 1 && addrecipe.difficulty)
    {
    Recipe.update({recipeId:addrecipe._id}, addrecipe);
    $location.path("/recipes/profile/"+$scope.Id);
}
    };


    $scope.search = function(name)
      {
        $scope.ingredientslist = [];
        var a = Ingredient.query({name:name}, function()
            {
                $scope.tmp = a;
                for (b = 0; b < $scope.tmp.length; b++)
                {
                    $scope.tmp[b].quantity = 1;
                }

                if ($scope.tmp.length > 10)
                {
                    $scope.loadshow = false;
                    for (index = 0; index < 10; index++) {
                        $scope.ingredientslist.push($scope.tmp[index]);
                    }
                }
                else {
                    $scope.loadshow = true;
                    $scope.ingredientslist = $scope.tmp;
                }
                });
        

        };
          

    $scope.toggleAdd = function (index) {
        console.log(index);
        if ($scope.addrecipe.ingredients.indexOf($scope.ingredientslist[index]) == -1)
        {
            $scope.addrecipe.ingredients.push($scope.ingredientslist[index]);
        }
        else
        {
            $scope.addrecipe.ingredients[$scope.addrecipe.ingredients.indexOf($scope.ingredientslist[index])].quantity = $scope.addrecipe.ingredients[$scope.addrecipe.ingredients.indexOf($scope.ingredientslist[index])].quantity + 1;
        }
    };

    $scope.toggleDelete = function (index) {
        $scope.addrecipe.ingredients.splice(index, 1);
    };

    $scope.loadMore = function ()
    {
        var indexTmp = index + 10;
        if (indexTmp > $scope.tmp.length)
            indexTmp = $scope.tmp.length;
        for (index; index < indexTmp; index++) {
            $scope.ingredientslist.push($scope.tmp[index]);
        };
    };


    $scope.stepChange = function()
    {
        console.log("stepIndex : ", stepIndex, "scope.step : ", $scope.steps);
        for (stepIndex; stepIndex > $scope.steps; stepIndex--) {
        $scope.addrecipe.instructions.pop();
        };
        stepIndex = $scope.steps;
    }



}]);