var reviewServices = angular.module('reviewServices', ['ngResource', "nourConfig"]);

// Comment object(s)
reviewServices.factory('Review',['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + ":by/:id/reviews",
            {by:'@by', id:'@id'}, {}
        );
	}]); 

reviewServices.factory('AddReview',['$resource', 'config', '$http',
	function($resource, config, $http){
		var api = {};
		api.AddReview = function(bookid, data, callback){
			$http.post(config.HOST + 'books/' + bookid + '/reviews', data)
			.success(function(data, status, headers, config){
				callback(data);
			});
		}
		api.getAllById = function(bookid, page, size, callback){
			$http.get(config.HOST + 'books/' + bookid + '/reviews?page=' + page + '&size=' + size)
			.success(function(data, status, headers, config){
				callback(data);
			});
		}
		return api;
	}]); 