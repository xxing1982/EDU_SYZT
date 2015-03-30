// app.js

//Main Angular script file for application

//create a module with injected modules in brackets
var routeApp = angular.module('routeApp',['ngResource', 'ui.router', 'nourControllers', 'nourConfig', 'bookServices', 'userServices']);

// router config
routeApp.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
    
     $stateProvider
        //main page
        .state('main', {
            url: '/',
            templateUrl: "partials/main.html"
        })
        //book page
        .state('book', {
            url: '/book',
            templateUrl: "partials/book.html",
            controller: 'bookCtrl'
        })
        //user page
        .state('user', {
            url: '/user',
            templateUrl: "partials/user.html",
            controller: 'userCtrl'
        });
}]);