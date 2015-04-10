var examServices = angular.module('examServices', ['ngResource', "nourConfig"]);


examServices.factory('VerifyExam',['$resource', 'config', '$http',
	function($resource, config, $http){
		var api = {};
		api.get = function(studentId, bookId, callback){
			$http.get(config.HOST + 'exam/verifypaper/'+studentId + '/' + bookId)
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		api.getAllInfo = function(userId ,callback){
			$http.get(config.Host + 'verifyexams/' + userId)
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		return api;
	}]);


examServices.factory('WordExam',['$resource', 'config', '$http',
	function($resource, config, $http){
		var api = {};
		api.get = function(studentId, bookId, callback){
			$http.get(config.HOST + 'exam/verifypaper/'+studentId + '/' + bookId)
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		api.getAllInfo = function(userId ,callback){
			$http.get(config.Host + 'wordexams/' + userId)
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		return api;
	}]);


examServices.factory('ThinkExam',['$resource', 'config', '$http',
	function($resource, config, $http){
		var api = {};
		api.get = function(bookId, callback){
			$http.get(config.HOST + 'exam/verifypaper/'+ bookId)
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		api.getAllInfo = function(userId ,callback){
			$http.get(config.Host + 'thinkexam/' + userId)
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		return api;
	}]);


examServices.factory('CapacityExam',['$resource', 'config', '$http',
	function($resource, config, $http){
		var api = {};
		api.get = function(levelId, callback){
			$http.get(config.HOST + 'exam/wordpaper/'+levelId)
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		api.getAllInfo = function(userId ,callback){
			$http.get(config.Host + 'capacityexams/' + userId)
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		return api;
	}]);

examServices.factory('PassExam',['$resource', 'config', '$http',
	function($resource, config, $http){
		var api = {};
		api.get = function(userid, callback){
			$http.get(config.HOST + 'verifyexams/pass/'+userid)
			.success(function(data, status, headers, config){
				callback(data);
			});
		};
		return api;
	}]);


