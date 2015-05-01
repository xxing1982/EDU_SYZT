//mainCtrl.js
var ctrls = angular.module('nourControllers', ['nourConfig', 'ngResource', 'userServices']);

ctrls.controller("mainController",['$scope', '$rootScope', 'Teacher',
	function($scope, $rootScope, Teacher){
		Teacher.get({id: $rootScope.id}, function(student){
			$scope.student = student;
		});
	}]);