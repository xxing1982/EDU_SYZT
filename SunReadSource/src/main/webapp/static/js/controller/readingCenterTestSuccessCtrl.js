//readingCenterTestSuccessCtrl.js

ctrls.controller("readingCenterTestSuccessController", ['$scope', '$rootScope', 'BookDetail', 'Review', 
  function ($scope, $rootScope, BookDetail, Review) {
    $scope.name = $rootScope.exam.bookName;
    $scope.score = $rootScope.exam.score;
    $scope.content = "";
    $scope.isVerify = $rootScope.exam.isVerify;
    $scope.title = "";
    var bookid = $rootScope.exam.bookId;
    BookDetail.get({id: bookid}, function(data){
     $scope.coin = data.coin;
     $scope.point = data.point;
   })

    $scope.evaluate = function(){
     var review = {};
      review.studentId = $rootScope.id;
      review.title = $scope.title;
      review.content = $scope.content;
      review.rate = 5;
      AddReview.AddReview($scope.chooseBookId, review, function(data){
        window.location.href = $rootScope.exam.returnURL;
      })
   }
   $scope.returnCode = function(){
    window.location.href = $rootScope.exam.returnURL;
  });
}]);