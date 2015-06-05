// app.js

//Main Angular script file for application

//create a module with injected modules in brackets
var routeApp = angular.module('routeApp',['ngResource', 'ui.router', 'nourControllers', 'nourConfig', 'userServices']);

// router config
routeApp.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider, $routeProvider,$httpProvider) {

    $urlRouterProvider
    .when('/readingCenter', '/readingCenter/myBookshelf')
    .when('/readingCenter/addBook', '/readingCenter/addBook/quick')
    .otherwise('/');
    // $httpProvider.responseInterceptors.push('SecurityHttpInterceptor');

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
        //reading center -> myBookshelf -> book details
        .state('readingCenter.bookDetails',{
            url: '/bookDetails/:bookId/:action',
            templateUrl: 'partials/readingCenterBookDetails.html',
            controller: 'readingCenterBookDetailsController'
        })
        //reading center -> myBookshelf -> add books
        .state('readingCenter.addBook',{
            url: '/addBook',
            templateUrl: 'partials/readingCenterAddBook.html',
            controller: 'readingCenterAddBookController'
        })
        //reading center -> myBookshelf -> add books -> quick search
        .state('readingCenter.addBook.quick', {
            url: '/quick',
            templateUrl: 'partials/readingCenterAddBookQuickSearch.html',
            controller: 'readingCenterAddBookQuickSearchController'
        })
        //reading center -> myBookshelf -> add books -> advenced search
        .state('readingCenter.addBook.advanced', {
            url: '/advanced/:searchTerm',
            templateUrl: 'partials/readingCenterAddBookAdvancedSearch.html',
            controller: 'readingCenterAddBookAdvancedSearchController'
        })
        //reading center -> myBookshelf -> add books -> popular reading
        .state('readingCenter.addBook.popular', {
            url: '/popular',
            templateUrl: 'partials/readingCenterAddBookPopularReading.html',
            controller: 'readingCenterAddBookPopularReadingController'
        })
        //reading center -> myBookshelf -> add books -> popular recommend
        .state('readingCenter.addBook.recommend', {
            url: '/recommend',
            templateUrl: 'partials/readingCenterAddBookPopularRecommend.html',
            controller: 'readingCenterAddBookPopularRecommendController'
        })
        //reading center -> myBookshelf -> test subjective
        .state('readingCenter.testSubjective',{
            url: '/testSubjective',
            templateUrl: 'partials/readingCenterTestSubjective.html',
            controller: 'readingCenterTestSubjectiveController'
        })
        //reading center -> myBookshelf -> authentication testing
        .state('readingCenter.multipleTesting',{
            url: '/multipleTesting',
            templateUrl: 'partials/readingCenterMultipleTesting.html',
            controller: 'readingCenterMultipleTestingController'
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

        //reading center -> test result -> failed
        .state('readingCenter.failed',{
            url: '/failed',
            templateUrl: 'partials/readingCenterTestResultFailed.html',
            controller: 'readingCenterTestFailedController'
        })

        //reading center -> test result -> success
        .state('readingCenter.success',{
            url: '/success',
            templateUrl: 'partials/readingCenterTestResultSuccess.html',
            controller: 'readingCenterTestSuccessController'
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
        //reading center -> thinking test
        .state('readingCenter.myAnswers',{
            url: '/myEvaluating/myAnswers',
            templateUrl: 'partials/readingCenterMyAnswers.html',
            controller: 'readingCenterMyAnswersController'
        })
        //reading center -> thinking test -> other answers
        .state('readingCenter.otherAnswer',{
            url: '/myEvaluating/otherAnswer/:questionId',
            templateUrl: 'partials/readingCenterOtherAnswer.html',
            controller: 'readingCenterOtherAnswerController'
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
        })
        //gift center
        .state('giftCenter', {
            url: '/giftCenter',
            templateUrl: "partials/giftCenter.html",
            controller: "giftCenterController"
        })
        //Gift Center -> Redeem Gift
        .state('redeemGift', {
            url: '/redeemGift',
            templateUrl: "partials/giftCenterRedeemGift.html",
            controller: "giftCenterRedeemGiftController"
        })
        //message center
        .state('messageCenter', {
            url: '/messageCenter',
            templateUrl: "partials/messageCenter.html",
            controller: "messageCenterController"
        })
        //statistic summary
        .state('statisticsSummary', {
            url: '/statisticsSummary',
            templateUrl: "partials/statisticsSummary.html",
            controller: "statisticsSummaryController"
        })
        //statistic ranking
        .state('statisticsRanking', {
            url: '/statisticsRanking',
            templateUrl: "partials/statisticsRanking.html",
            controller: "statisticsRankingController"
        })
        //personal profile
        .state('personalProfile', {
            url: '/personalProfile',
            templateUrl: "partials/personalProfile.html",
            controller: "personalProfileController"
        })
        //personal show
        .state('personalShow', {
            url: '/personalShow',
            templateUrl: "partials/personalShow.html",
            controller: "personalShowController"
        });



        }]);

routeApp.run(['$rootScope', 'Student', function($rootScope, Student){
    if (sessionStorage.getItem("userId") == null || sessionStorage.getItem("userId") == "") {
        window.location.href="../../login.html";
    };
    $rootScope.id = sessionStorage.getItem("userId");
}]);
