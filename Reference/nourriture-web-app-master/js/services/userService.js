/**
 * Created by niels on 12/1/14.
 */

var userServices = angular.module('userServices', ['ngResource', "nourConfig"]);

// User object(s)
userServices.factory('User', ['$resource', 'config',
    function ($resource, config) {
        return $resource(config.BE_HOST + '/user/:username', {}, {
            update: {
                method:'PUT',
                url:config.BE_HOST + '/user/:username'
            }
        });
    }]
);

// Utilities for manipulating and reading login state
userServices.factory('UserService', ['$rootScope', '$http', 'config',
    function($rootScope, $http, config){
        var api = {
            isLoggedIn: false,
            user: null
        };

        // Attempt to log in to Nourriture platform
        api.logIn = function(credentials, callback) {
            $http.post(config.BE_HOST + "/login", credentials).
                success(function(data, status, headers, config) {
                    // Update login state
                    api.isLoggedIn = true;
                    api.user = data;

                    // Broadcast state change
                    $rootScope.$emit("user:loginStateChanged", api);

                    // Return
                    callback();
                })
                .error(function(data, status, headers, config) {
                    if(status == 0) {
                        callback(-1);
                    } else {
                        callback(status);
                    }
                });
        };

        // Attempt to log out of Nourriture platform
        api.logOut = function(callback) {
            $http.get(config.BE_HOST + "/logout").
                success(function(data, status, headers, config) {
                    // Update login state
                    api.isLoggedIn = false;
                    api.user = null;

                    // Broadcast state change
                    $rootScope.$emit("user:loginStateChanged", api);

                    // Return
                    callback();
                })
                .error(function(data, status, headers, config) {
                    if(status == 0) {
                        callback(-1);
                    } else {
                        callback(status);
                    }
                });
        };

        // Attempt to refresh login state (i.e login automatically with cookie, if we have a session)
        api.refreshLoginState = function(callback) {
            $http.get(config.BE_HOST + "/isloggedin").
                success(function(data, status, headers, config) {
                    // Update login state
                    api.isLoggedIn = true;
                    api.user = data;

                    // Broadcast state change
                    $rootScope.$emit("user:loginStateChanged", api);

                    // Return, if callback provided
                    if(callback) callback();
                })
                .error(function(data, status, headers, config) {
                    if(status == 0) {
                        if(callback) callback(-1);
                    } else {
                        if(callback) callback(status);
                    }
                });
        };

        return api;
    }]);