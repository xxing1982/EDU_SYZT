// Adding access token to all requests
// http://jeremymarc.github.io/2014/08/14/oauth2-with-angular-the-right-way/

routeApp.run(['$rootScope', '$injector', function($rootScope,$injector) {
    $injector.get("$http").defaults.transformRequest = function(data, headersGetter) {
//      if (sessionService.isLogged()) {
        headersGetter()['Authorization'] = "Bearer " + sessionStorage.access_token;
//      }
      if (data) {
        return angular.toJson(data);
      }
    };
}]);
