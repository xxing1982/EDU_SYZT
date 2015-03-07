var ctrls = angular.module('testCtrl', ['userServices', "nourConfig", "nourUpload"]);    //injection 'userServices' into our controllers, so we can use the "user" object everywhere

ctrls.controller("testCtrl", ['$scope', function ($scope) {
    // Application state
    $scope.name = "World";
} ]);