// app.js

//Main Angular script file for application

//create a module with injected modules in brackets
var routeApp = angular.module('route',['ngResource', 'ui.router', 'nourControllers', 'nourConfig']);

// router config
routeApp.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider, $routeProvider, $httpProvider) {

    $urlRouterProvider
    .when('/teachingCenter', '/teachingCenter/myTask')
    .when('/teachingCenter/addBook', '/teachingCenter/addBook/quick')
    .when('/statistics', '/statistics/students')
    .when('/statistics/classes', '/statistics/classes/coins')
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
        //teachingCenter my recommend page
        .state('teachingCenter.myRecommend',{
            url: '/myRecommend',
            templateUrl: 'partials/teachingCenterMyRecommend.html',
            controller: 'myRecommendController'
        })
        //teaching center -> book details
        .state('teachingCenter.bookDetails',{
            url: '/bookDetails/:bookId/:action',
            templateUrl: 'partials/teachingCenterBookDetails.html',
            controller: 'teachingCenterBookDetailsController'
        })
        //teaching center -> myBookshelf -> add books
        .state('teachingCenter.addBook',{
            url: '/addBook',
            templateUrl: 'partials/teachingCenterAddBook.html',
            controller: 'teachingCenterAddBookController'
        })
        //teaching center -> myBookshelf -> add books -> quick search
        .state('teachingCenter.addBook.quick', {
            url: '/quick',
            templateUrl: 'partials/teachingCenterAddBookQuickSearch.html',
        })
        //teaching center -> myBookshelf -> add books -> advenced search
        .state('teachingCenter.addBook.advanced', {
            url: '/advanced/:searchTerm',
            templateUrl: 'partials/teachingCenterAddBookAdvancedSearch.html',
        })
        //teaching center -> myBookshelf -> add books -> popular teaching
        .state('teachingCenter.addBook.popular', {
            url: '/popular',
            templateUrl: 'partials/teachingCenterAddBookPopularReading.html',
        })
        //teaching center -> myBookshelf -> add books -> popular recommend
        .state('teachingCenter.addBook.recommend', {
            url: '/recommend',
            templateUrl: 'partials/teachingCenterAddBookPopularRecommend.html',
        })
        .state('teachingCenter.addBookToShelf', {
            url: '/addBookToShelf/:bookId/',
            templateUrl: 'partials/teachingCenterAddToShelf.html',
            controller: 'teachingCenterAddToShelfController'
        })
        //teachingCenter my reward page
        .state('teachingCenter.myReward',{
            url: '/myReward',
            templateUrl: 'partials/teachingCenterMyReward.html',
            controller: 'myRewardController'
        })
        //teachingCenter my reward dispatch award page
        .state('teachingCenter.myRewardDispatchReward',{
            url: '/myReward/dispatchReward',
            templateUrl: 'partials/teachingCenterMyRewardDispatchReward.html',
            controller: 'myRewardDispatchRewardController'
        })
        //teachingCenter my task page
        .state('teachingCenter.myResource',{
            url: '/myResource',
            templateUrl: 'partials/teachingCenterMyResource.html',
            controller:'myResourceController'
        })
        //reading dynamic
        .state('readingDynamic',{
            url: '/readingDynamic',
            templateUrl: 'partials/readingDynamic.html',
            controller: 'readingDynamicController'
        })
        //reading dynamic -> note notes
        .state('hotNotes',{
            url: '/hotNotes',
            templateUrl: 'partials/hotNotes.html',
            controller: 'hotNotesController'
        })
        //reading dynamic -> actions
        .state('actions',{
            url: '/readingDynamic/actions',
            templateUrl: 'partials/readingDynamicActions.html',
            controller: 'readingDynamicActionsController'
        })
        //reading dynamic -> actions with parameters
        .state('actionsParams',{
            url: '/readingDynamic/actions/{page:int}/{index:int}',
            templateUrl: 'partials/readingDynamicActions.html',
            controller: 'readingDynamicActionsController'
        })
        //personalProfile page
        .state('personalProfile',{
            url: '/personalProfile',
            templateUrl: 'partials/personalProfile.html',
            controller: "personalProfileController"
        })
        //message center
        .state('messageCenter', {
            url: '/messageCenter',
            templateUrl: "partials/messageCenter.html",
            controller: "messageCenterController"
        })
        //statistics page
        .state('statistics',{
            url: '/statistics',
            templateUrl: 'partials/statistics.html',
            controller: "statisticsController",
            controllerAs: "parentCtrl",
        })
            //students statistics page
            .state('statistics.students',{
                url: '/students',
                parent: 'statistics',
                templateUrl: 'partials/statistics/students.html',
                controller: "statisticsStudentsController",
                controllerAs: "ctrl",
            })
            //classes statistics page
            .state('statistics.classes',{
                url: '/classes',
                parent: 'statistics',
                templateUrl: 'partials/statistics/classes.html',
                controller: "statisticsClassesController",
                controllerAs: "parentCtrl",
            })
                //classes coins statistics page
                .state('statistics.classes.coins',{
                    url: '/coins',
                    parent: 'statistics.classes',
                    templateUrl: 'partials/statistics/classes/coins.html',
                    controller: "statisticsClassesController",
                    controllerAs: "ctrl",
                })
                //classes readings statistics page
                .state('statistics.classes.readings',{
                    url: '/readings',
                    parent: 'statistics.classes',
                    templateUrl: 'partials/statistics/classes/readings.html',
                    controller: "statisticsClassesController",
                    controllerAs: "ctrl",
                })
                //classes categories statistics page
                .state('statistics.classes.categories',{
                    url: '/categories',
                    parent: 'statistics.classes',
                    templateUrl: 'partials/statistics/classes/categories.html',
                    controller: "statisticsClassesCategoriesController",
                    controllerAs: "ctrl",
                })
            //campuses statistics page
            .state('statistics.campuses',{
                url: '/campuses',
                parent: 'statistics',
                templateUrl: 'partials/statistics/campuses.html',
                controller: "statisticsCampusesController",
                controllerAs: "ctrl",
            })
}]);

routeApp.run(['$rootScope', '$state', function($rootScope, $state){

    // Id cache id from sessionStorage
    var teacherId = sessionStorage.getItem("teacherId"),
        userId = sessionStorage.getItem("userId"),    
        adminId = sessionStorage.getItem("adminId");
    
    if ( teacherId || userId || adminId) {
                
        // Login successfully 
        $rootScope.type = teacherId ? 'Teacher' : userId ? 'User' : 'Admin';
        $rootScope.id = teacherId ? teacherId : userId ? userId : adminId;
        
        $rootScope.$on('$stateChangeStart',
            function(event, toState, toParams, fromState) {
                if ( $rootScope.type === 'Admin' 
                     && ( toState.name === 'main' || toState.name === 'statistics.students' )) { 
                    event.preventDefault();
//                    $state.transitionTo('statistics.classes');
                    // FIXME wtf checkout this http://plnkr.co/edit/aYmykLnWZVUwg4trMmVX?p=preview
                    window.location.href = "#/statistics/classes";
                }
        });
    } else {
        
        // Not login so login first
        window.location.href = "../../login.html";
    }
    
}]);
