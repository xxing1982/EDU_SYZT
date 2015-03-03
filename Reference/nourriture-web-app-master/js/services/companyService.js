/**
 * Created by Pavel Prochazka on 09/12/14.
 */

var companyServices = angular.module('companyServices', ['ngResource', 'nourConfig']);

companyServices.factory('Company', ['$resource', 'config',
    function ($resource, config) {
        return $resource(config.BE_HOST + '/company/:username', {}, {
            update: {
                method:'PUT',
                url:config.BE_HOST + '/company/:username'
            }
        });
    }]);