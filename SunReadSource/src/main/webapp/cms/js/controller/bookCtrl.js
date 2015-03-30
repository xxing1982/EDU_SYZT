var ctrls = angular.module('nourControllers',['ngResource', 'nourConfig', 'bookServices', 'userServices']);

ctrls.controller("bookCtrl", ['$scope', 'Book', function ($scope, Book) {
	$scope.books = [
	{
		"id": 2,
		"creationTime": "18-03-2015",
		"description": null,
		"modificationTime": "18-03-2015",
		"name": "name1",
		"isbn": "isbn1rrr11131311",
		"avgRate": 0
	},
	{
		"id": 3,
		"creationTime": "18-03-2015",
		"description": null,
		"modificationTime": "18-03-2015",
		"name": "name1",
		"isbn": "isbn1rrr111313111",
		"avgRate": 0
	},
	{
		"id": 4,
		"creationTime": "18-03-2015",
		"description": null,
		"modificationTime": "18-03-2015",
		"name": "name1",
		"isbn": "isbn1",
		"avgRate": 0
	}]
	$scope.selected={};
	$scope.Add = function(){
		$scope.selected={};
		$scope.selected.title = "Add";
		
		var book = new Book();
		
		book = $scope.selected;
		/*book.name = $scope.selected.book.name;
		book.description = $scope.selected.book.description;
		book.isbn = $scope.selected.book.isbn;
		book.publicationDate = $scope.selected.book.publicationDate;
		book.author = $scope.selected.book.author;
		book.publisher = $scope.selected.book.publisher;
		book.point = $scope.selected.book.point;
		book.coin = $scope.selected.book.coin;
		book.level = $scope.selected.book.level;
		book.language = $scope.selected.book.language;
		book.literature = $scope.selected.book.literature;
		book.testType = $scope.selected.book.testType;
		book.categories = $scope.selected.book.categories;*/

		//save option
		book.$save(function(res){

		});
	}
	$scope.Update = function(bookid){
		$scope.selected.title = "Edit";
		$scope.selected.book = Book.get({id: bookid});

		//save option
		$scope.selected.book.$update(function(res){

		});
	}
	$scope.Delete = function(bookid){
		Book.delete({id: bookid}, function(){

		})
	}
	/*var testDataList = Book.query({page: 0, size: 4}, function(){
        console.log(testDataList);
    });*/
	
}]);