//readingCenterTestSuccessCtrl.js

ctrls.controller("readingCenterTestSuccessController", ['$scope', '$rootScope', 'BookDetail', 'Review', 
    function ($scope, $rootScope, BookDetail, Review) {
        $scope.name = $rootScope.exam.bookName;
        $scope.score = $rootScope.exam.score;
        $scope.content = "";
		$scope.title = "";
        var bookid = $rootScope.exam.bookId;
        BookDetail.get({id: bookid}, function(data){
           $scope.coin = data.coin;
           $scope.point = data.point;
       })

        $scope.evaluate = function(){
           var review = new Review();
           review.bookId = bookid;
           review.content = $scope.content;
           review.title = $scope.title;
           review.$save(function(dataSave){
              console.log(dataSave);
           });
       }
   }]);