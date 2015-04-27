//readingCenterAddBookQuickSearchCtrl.js
ctrls.controller("readingCenterAddBookQuickSearchController", ['$rootScope','$scope','QuickSearch','config','JoinShelf',
																											function ($rootScope,$scope,QuickSearch,config,JoinShelf) {
	$scope.name='阅读中心->添加书籍->快速查找';

    var pageSize = 3;
    var level=0;
    var category=0;
    var testType=0;
    var literature=0;
    var grade=0;
    var category=0;
    var language=0;
    var resource=0;
    var pointRange=0;

    $scope.level = level;
    $scope.category = category;
    $scope.testType = testType;
    $scope.literature = literature;
    $scope.grade = grade;
    $scope.category = category;
    $scope.language = language;
    $scope.resource = resource;
    $scope.pointRange = pointRange;


		$scope.hots=QuickSearch.get({page:0,size:3,sortBy:"statistic.readNums"},function(){
			console.log($scope.hots);
		})

		$scope.recommends=QuickSearch.get({page:0,size:3,sortBy:"statistic.recommends"},function(){
			console.log($scope.hots);
		})

		$scope.addBooktoShelf = function(terms){
			var addBook = new JoinShelf();
			addBook.joinShelf(terms);
		} ;


    $scope.searchToAdvance=function(searchContent){
        window.location.href="#readingCenter/addBook/advanced/"+searchContent;
    }
$scope.imageServer = config.IMAGESERVER;
}]);
