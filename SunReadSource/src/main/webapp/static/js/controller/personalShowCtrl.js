//personalShow.js

ctrls.controller("personalShowController", ['$scope', 'User', function ($scope, User) {

    //userinfo
    //test function to get data list
    $scope.userInfo = User.query(function(){});
   
}]);
