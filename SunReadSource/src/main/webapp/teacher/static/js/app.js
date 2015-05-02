// app.js

//Main Angular script file for application

//create a module with injected modules in brackets
var routeApp = angular.module('route',['ngResource', 'ui.router', 'nourControllers', 'nourConfig']);

// router config
routeApp.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider, $routeProvider, $httpProvider) {

    $urlRouterProvider
    .when('/teachingCenter', '/teachingCenter/myTask')
    .otherwise('/');
    // $httpProvider.responseInterceptors.push('SecurityHttpInterceptor');

    $stateProvider
        //main page
        .state('main', {
            url: '/',
            templateUrl: "partials/main.html",
            controller: "mainController"
        })
        //teachingCenter page
        .state('teachingCenter',{
            url: '/teachingCenter',
            templateUrl: 'partials/teachingCenter.html'
        })
        //teachingCenter my task page
        .state('teachingCenter.myTask',{
            url: '/myTask',
            templateUrl: 'partials/teachingCenterMyTask.html',
            controller: "myTaskController"
        })
        //teachingCenter my task dispatch task page
        .state('teachingCenter.myTaskDispatchTask',{
            url: '/myTask/dispatchTask',
            templateUrl: 'partials/teachingCenterMyTaskDispatchTask.html',
            controller: "myTaskDispatchTaskController"
        })
        //teachingCenter my task page
        .state('teachingCenter.myRecommend',{
            url: '/myRecommend',
            templateUrl: 'partials/teachingCenterMyRecommend.html'
        })
        //teachingCenter my reward page
        .state('teachingCenter.myReward',{
            url: '/myReward',
            templateUrl: 'partials/teachingCenterMyReward.html'
        })
        //teachingCenter my reward dispatch award page
        .state('teachingCenter.myRewardDispatchReward',{
            url: '/myReward/dispatchReward',
            templateUrl: 'partials/teachingCenterMyRewardDispatchReward.html'
        })
        //teachingCenter my task page
        .state('teachingCenter.myResource',{
            url: '/myResource',
            templateUrl: 'partials/teachingCenterMyResource.html'
        })
        //readingDynamic page
        .state('readingDynamic',{
            url: '/readingDynamic',
            templateUrl: 'partials/readingDynamic.html'
        })
        //statisticsSummary page
        .state('statisticsSummary',{
            url: '/statisticsSummary',
            templateUrl: 'partials/statisticsSummary.html'
        })
}]);

routeApp.run(['$rootScope', function($rootScope){
    if (sessionStorage.getItem("teacherId") == null) {
        window.location.href="../../login.html";
    };
    $rootScope.id = sessionStorage.getItem("teacherId");
}]);
