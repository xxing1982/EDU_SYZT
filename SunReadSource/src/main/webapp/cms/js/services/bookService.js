var conditionSearchServices = angular.module('conditionSearchServices', [
		'ngResource', "nourConfig" ]);

// Note object(s)
conditionSearchServices
		.factory(
				'ConditionSearch',
				[
						'$resource',
						'config',
						function($resource, config) {
							return $resource(
									"/api/books/conditions?page=:page&size=:size&sortBy=id"
											+ "&level=:level&category=:category&testType=:testType&literature=:literature"
											+ "&grade=:grade&language=:language&resource=:resource&pointRange=:pointRange&searchTerm=:searchTerm",
									{
										page : '@_page',
										size : '@_size',
										level : '@_level',
										category : '@_category',
										testType : '@_testType',
										literature : '@_literature',
										grade : '@_grade',
										language : '@_language',
										resource : '@_resource',
										pointRange : '@_pointRange',
										searchTerm : '@_searchTerm'
									}, {});
						} ]);

var quickSearchServices = angular.module('quickSearchServices', [ 'ngResource',
		"nourConfig" ]);

quickSearchServices
		.factory(
				'QuickSearch',
				[
						'$resource',
						'config',
						function($resource, config) {
							return $resource(
									"/api/books/conditions?page=:page&size=:size&sortBy=:sortBy&direction=desc",
									{
										page : '@_page',
										size : '@_size',
										sortBy : '@_sortBy'
									}, {});
						} ]);

var bookServices = angular.module('bookServices',
		[ 'ngResource', "nourConfig" ]);

bookServices
		.factory(
				'Book',
				[
						'$resource',
						'config',
						function($resource, config) {
							return $resource(
									"/api/books/conditions?page=:page&size=:size&sortBy=:sortBy&direction=desc",
									{
										page : '@_page',
										size : '@_size',
										sortBy : '@_sortBy'
									}, {});
						} ]);

bookServices.factory('BookOperation',['config', '$http',
	function(config, $http){
		var api = {};
		api.Add = function(data, callBack){
			$http.post(config.HOST + 'books/', data)
			.success(function(data){
				callBack();
			});
		};
		api.deleteBook = function(bookId, callBack){
			$http.delete(config.HOST + 'books/' + bookId)
			.success(function(data){
				callBack();
			});
		};
		api.updateBook = function(id, book, callBack){
			$http.put(config.HOST + 'books/' + id, book)
			.success(function(data){
				callBack();
			});
		}
		return api;
	}]);
