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
        //reading center -> myBookshelf -> add books
        .state('teachingCenter.addBook',{
            url: '/addBook',
            templateUrl: 'partials/teachingCenterAddBook.html',
            controller: 'teachingCenterAddBookController'
        })
        //reading center -> myBookshelf -> add books -> quick search
        .state('teachingCenter.addBook.quick', {
            url: '/quick',
            templateUrl: 'partials/teachingCenterAddBookQuickSearch.html',
            controller: 'readingCenterAddBookSearchController'
        })
        //reading center -> myBookshelf -> add books -> advenced search
        .state('teachingCenter.addBook.advanced', {
            url: '/advanced/:searchTerm',
            templateUrl: 'partials/teachingCenterAddBookAdvancedSearch.html',
            controller: 'readingCenterAddBookSearchController'
        })
        //reading center -> myBookshelf -> add books -> popular reading
        .state('teachingCenter.addBook.popular', {
            url: '/popular',
            templateUrl: 'partials/teachingCenterAddBookPopularReading.html',
            controller: 'readingCenterAddBookSearchController'
        })
        //reading center -> myBookshelf -> add books -> popular recommend
        .state('teachingCenter.addBook.recommend', {
            url: '/recommend',
            templateUrl: 'partials/teachingCenterAddBookPopularRecommend.html',
            controller: 'readingCenterAddBookSearchController'
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
