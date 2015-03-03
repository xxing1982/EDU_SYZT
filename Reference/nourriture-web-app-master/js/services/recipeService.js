/**
 * Created by niels on 12/2/14.
 */

var recipeServices = angular.module('recipeServices', ['ngResource', 'nourConfig']);

recipeServices.factory('Recipe', ['$resource', 'config',
    function ($resource, config) {
        return $resource(config.BE_HOST + '/recipe/:id', {}, {
            byAuthor: {
                method:'GET',
                url:config.BE_HOST + '/recipe/author/:authorId',
                isArray:true
            },
            update: {
                method:'PUT',
                url:config.BE_HOST + '/recipe/:recipeId'
            },
            byTitle: {
                method:'GET',
                url:config.BE_HOST + '/recipe/title/:titleId',
                isArray:true
            }
        });
    }]);