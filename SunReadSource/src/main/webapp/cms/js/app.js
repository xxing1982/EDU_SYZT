// app.js

//Main Angular script file for application

//create a module with injected modules in brackets
var routeApp = angular.module('routeApp',['ngResource', 'ui.router', 'nourControllers', 'nourConfig', 'bookServices', 'userServices']);

// router config
routeApp.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
    
    $urlRouterProvider
        .otherwise('/');

     $stateProvider
        // main page
        .state('main', {
            url: '/',
            templateUrl: "partials/main.html",
            controller: 'mainController'
        })
        // book page
        .state('book', {
            url: '/book',
            templateUrl: "partials/book.html",
            controller: 'readingCenterAddBookAdvancedSearchController'
        })
        // user page
        .state('student', {
            url: '/student',
            templateUrl: "partials/student.html",
            controller: 'studentCtrl'
        })
     	// relation with book question page
     	.state('objectivequestion', {
     		url: '/objectivequestion',
     		templateUrl: "partials/objectivequestion.html",
     		controller: 'objectiveQuestionCtrl'
     	})
     	.state('subjectivequestion', {
     		url: '/subjectivequestion',
     		templateUrl: "partials/subjectivequestion.html",
     		controller: 'subjectiveQuestionCtrl'
     	});
}]);