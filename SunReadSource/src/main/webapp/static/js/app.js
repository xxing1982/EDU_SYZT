// app.js

// define our application and pull in ngRoute and ngAnimate
var animateApp = angular.module('animateApp', ['ngRoute', 'ngAnimate']);

// ROUTING
animateApp.config(function ($routeProvider) {

    $routeProvider

    // home page
        .when('/index', {
            templateUrl: 'partials/test.html',
            controller: 'mainController'
        })

    // about page
        .when('/about', {
            templateUrl: 'page-about.html',
            controller: 'aboutController'
        })

    // contact page
        .when('/contact', {
            templateUrl: 'page-contact.html',
            controller: 'contactController'
        });

});