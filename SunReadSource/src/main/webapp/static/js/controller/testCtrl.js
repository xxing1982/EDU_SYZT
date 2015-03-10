var testController = angular.module('testController');    //injection 'userServices' into our controllers, so we can use the "user" object everywhere

testController.controller("testController", ['$scope', function ($scope) {
    
    $scope.name = "World";
} ]);