var userServices = angular.module('userServices', ['ngResource', "nourConfig"]);

// User object(s)
userServices.factory('User',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "users/:id",{},{
			query: {
				method: "get",
				isArray: true
			},
            update: { 
                method:'PUT'
            }
		});
	}]);

userServices.factory('Teacher',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "teachers/:id",{},{
			query:{
				method: "get",
				isArray: true
			}
		});
	}]);

userServices.factory('Admin',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "admins/:id",{},{
			query:{
				method: "get",
				isArray: true
			}
		});
	}]);



userServices.factory('ShowAllClass',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "teachers/:id/clazzs",
			{id:'@id'}, {
				query:{method:'GET', isArray:false}
			}
			);
	}]);


userServices.factory('ShowAllClass',['$resource', 'config', '$http',
	function($resource, config, $http){
		var api = {};
		api.Gets = function(teacherid, callback){
			$http.get(config.HOST + "teachers/"+teacherid+"/clazzs")
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		api.Change = function(teacherid, classid, callback){
			$http.put(config.HOST + "/teachers/" + teacherid + "/clazzs/" + classid)
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		return api;
	}]);

