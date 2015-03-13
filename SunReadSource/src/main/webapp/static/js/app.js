// app.js

//Main Angular script file for application

//create a module with injected modules in brackets
var routeApp = angular.module('routeApp',['ngResource', 'ui.router', 'nourControllers', 'nourConfig', 'userServices']);

// router config
routeApp.config(['$stateProvider', '$urlRouterProvider',function ($stateProvider, $urlRouterProvider) {
    
    $urlRouterProvider
        .when('/readingCenter', '/readingCenter/myBookshelf')
        //.otherwise('/');

    $stateProvider
        //main page
        .state('main', {
            url: '/',
            templateUrl: "partials/main.html",
            controller: "mainController"
        })
        //reading center
        .state('readingCenter', {
            url: '/readingCenter',
            templateUrl: "partials/readingCenter.html"
        })
        //reading center -> myBookshelf
        .state('readingCenter.myBookshelf',{
            url: '/myBookshelf',
            templateUrl: 'partials/readingCenterMyBookshelf.html',
            controller: 'readingCenterMyBookshelfController'
        })
        //reading center -> myNote
        .state('readingCenter.myNote',{
            url: '/myNote',
            templateUrl: 'partials/readingCenterMyNote.html',
            controller: 'readingCenterMyNoteController'
        })
        //reading center -> myEvaluating
        .state('readingCenter.myEvaluating',{
            url: '/myEvaluating',
            templateUrl: 'partials/readingCenterMyEvaluating.html',
            controller: 'readingCenterMyEvaluatingController'
        })
        //reading center -> myEvaluate
        .state('readingCenter.myEvaluate',{
            url: '/myEvaluate',
            templateUrl: 'partials/readingCenterMyEvaluate.html',
            controller: 'readingCenterMyEvaluateController'
        })
        //reading sea
        .state('readingSea', {
            url: '/readingSea',
            templateUrl: "partials/readingSea.html",
            controller: "readingSeaController"
        })
        //reading training
        .state('readingTraining', {
            url: '/readingTraining',
            templateUrl: "partials/readingTraining.html",
            controller: "readingTrainingController"
        })
        //prize center
        .state('prizeCenter', {
            url: '/prizeCenter',
            templateUrl: "partials/prizeCenter.html",
            controller: "prizeCenterController"
        });
}]);