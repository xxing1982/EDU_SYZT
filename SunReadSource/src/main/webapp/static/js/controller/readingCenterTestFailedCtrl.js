//readingCenterTestFailedController.js

ctrls.controller("readingCenterTestFailedController", ['$scope', '$rootScope', function ($scope, $rootScope) {
    $scope.name = $rootScope.exam.bookName;
}]);