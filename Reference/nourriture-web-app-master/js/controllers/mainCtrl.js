/**
 * Created by niels on 11/26/14.
 *
 *  The main controller module which all other controllers are based on.
 *  Also, the controller "mainCtrl" used by the frontpage is defined here.
 */

var ctrls = angular.module('nourControllers', ['userServices', "nourConfig", "nourUpload"]);    //injection 'userServices' into our controllers, so we can use the "user" object everywhere

ctrls.controller("mainCtrl", ['$scope', function ($scope) {
    // Application state
    $scope.name = "World";
}]);