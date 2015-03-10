// app.js

//Main Angular script file for application

//create a module with injected modules in brackets
var routeApp = angular.module('routeApp',['ngRoute', 'ngResource', 'nourControllers', 'nourConfig', 'userServices']);

// router config
routeApp.config(['$routeProvider',function ($routeProvider) {
    $routeProvider
    	//main page
        .when('/', {
            templateUrl: "partials/main.html",
            controller: "mainController"
        })
        //reading center
        .when('/readingCenter', {
        	templateUrl: "partials/readingCenter.html",
            controller: "readingCenterCtrlController"
        })
        //reading sea
        .when('/readingSea',{
        	templateUrl: "partials/index.html",
            controller: "indexController"
        })
        //reading training
        .when('/readingTraining',{
        	templateUrl: "partials/index.html",
            controller: "indexController"
        })
        //prize center
        .when('/prizeCenter',{
        	templateUrl: "partials/index.html",
            controller: "indexController"
        })
        .otherwise({
                redirectTo: '/'
        });
}]);