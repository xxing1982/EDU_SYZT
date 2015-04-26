var securityHttpInterceptor = angular.module('SecurityHttpInterceptor',['ngResource', "nourConfig"]);

securityHttpInterceptor.factory('SecurityHttpInterceptor',function($q){
  return function(promise){
    return promise.then(function(response){
      return response;
    },
    function(response){
      if(response.status === 409||500){
        console.log("app.js ERROR"+ response.status);
      }
      return $q.reject(response);
    });
  };
});

// $routeProvider.factory('SecurityHttpInterceptor',function($q){
//   return function(promise){
//     return promise.then(function(response){
//       return response;
//     },
//     function(response){
//       if(response.status === 409||500){
//         console.log("app.js ERROR"+ response.status);
//       }
//       return $q.reject(response);
//     });
//   };
// });

// $httpProvider.interceptors.push('SecurityHttpInterceptor');
