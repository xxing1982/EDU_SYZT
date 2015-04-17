//readingCenterTestSuccessCtrl.js

ctrls.controller("readingCenterTestSuccessController", ['$scope', '$rootScope', 'BookDetail', function ($scope, $rootScope, BookDetail) {
    $scope.name = $rootScope.exam.bookName;
    $scope.score = $rootScope.exam.score;
    var bookid = $rootScope.exam.bookId;
    BookDetail.get({id: bookid}, function(data){
    	$scope.coin = data.coin;
    	$scope.point = data.point;
    })

    $scope.evaluate = function(){
    	window.location.href="/protype/index.html#/readingCenter/myBookshelf";
    }
}]);