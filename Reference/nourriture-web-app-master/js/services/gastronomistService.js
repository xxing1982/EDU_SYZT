

var gastronomistServices = angular.module('gastronomistServices', ['ngResource', 'nourConfig']);

gastronomistServices.factory('Gastronomist', ['$resource', 'config',
    function ($resource, config) {
        return $resource(config.BE_HOST + '/gastronomist/:username', {}, {
            update: {
                method:'PUT',
                url:config.BE_HOST + '/gastronomist/:username'
            }
            });
    }]
);