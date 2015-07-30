// app.js

//Main Angular script file for application

//create a module with injected modules in brackets
var routeApp = angular.module('routeApp',['ngResource', 'ui.router', 'nourControllers', 'nourConfig', 'bookServices', 'userServices', 'nourDirectives']);

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
        .state('teacher',{
        	url: '/teacher',
            templateUrl: "partials/teacher.html",
            controller: 'TeacherCtrl'
        })
         .state('admin',{
             url: '/admin',
             templateUrl: "partials/cmsAdmin.html",
             controller: 'adminCtrl'
         })
         .state('sysAdmin',{
             url: '/sysAdmin',
             templateUrl: "partials/sysAdmin.html",
             controller: 'sysAdminCtrl'
         })
         .state('schoolAdmin',{
             url: '/schoolAdmin',
             templateUrl: "partials/schoolAdmin.html",
             controller: 'schoolAdminCtrl'
         })
         .state('schoolSuperAdmin',{
             url: '/schoolSuperAdmin',
             templateUrl: "partials/schoolSuperAdmin.html",
             controller: 'schoolSuperAdminCtrl'
         })
        .state('region',{
        	url: '/region',
            templateUrl: "partials/region.html",
            controller: 'regionCtrl'
        })
        .state('gift',{
            url: '/gift',
            templateUrl: "partials/gift.html",
            controller: 'giftCtrl'
        })
         .state('edugroup',{
        	url: '/edugroup',
            templateUrl: "partials/edugroup.html",
            controller: 'eduGroupCtrl'
        })
        .state('school',{
        	url: '/school',
            templateUrl: "partials/school.html",
            controller: 'schoolCtrl'
        })
        .state('campus',{
        	url: '/campus',
            templateUrl: "partials/campus.html"
            //controller: 'campusCtrl'
        })
        .state('clazz',{
        	url: '/clazz',
            templateUrl: "partials/clazz.html",
            controller: 'ClassCtrl'
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
routeApp.run(['$rootScope', '$injector', function($rootScope, $injector){
    if (sessionStorage.getItem("cmsId") == null || sessionStorage.getItem("cmsId") == "") {
        window.location.href="cms/login.html";
    };
    $rootScope.id = sessionStorage.getItem("cmsId");

    $injector.get("$http").defaults.transformRequest = function(data, headersGetter) {
//      if (sessionService.isLogged()) {
        headersGetter()['Authorization'] = "Bearer " + sessionStorage.access_token;
//      }
      if (data) {
        return angular.toJson(data);
      }
    };

    /*
    $("form[enctype='multipart/form-data'] input[type='file']").die().live('onchange', function(){
        $rootScope.uploadpath = this.val();
        this.replaceWith('<input type="file" name="myfile"/>');
    });
    */
    
}]);