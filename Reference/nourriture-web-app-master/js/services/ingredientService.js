
var ingredientServices = angular.module('ingredientServices', ['ngResource', 'nourConfig']);

ingredientServices.factory('Ingredient', ['$resource', 'config',
    function ($resource, config) {
        return $resource(config.BE_HOST + '/ingredient/:id', {}, {
            update: {
                method:'PUT',
                url:config.BE_HOST + '/ingredient/:name'
            },
            byName: {
                method:'GET',
                url:config.BE_HOST + '/ingredient/name/:nameId',
                isArray:true
            }
        });
    }]);

ingredientServices.factory('SaveIngredient', function () 
    {
        var savedData = {}
         function set(data) {
           savedData = data;
         }
         function get() {
          return savedData;
         }

         return {
          set: set,
          get: get
         }

    });