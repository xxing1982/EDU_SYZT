//mainCtrl.js
var ctrls = angular.module('nourControllers', ['nourConfig', 'ngResource', 
	'userServices', 'classServices', 'noteServices']);

ctrls.controller("mainController",['$scope', '$rootScope', 'Teacher', "Class", "Note",
	function($scope, $rootScope, Teacher, Class, Note){
		Teacher.get({id: $rootScope.id}, function(teacher){
			$scope.teacher = teacher;
			// Create a classes entitiy
			/*Class.get({id: $scope.teacher.clazzId}, function(classData){
				$scope.teacher.class=classData.name;
				$scope.teacher.school=classData.campusName;
			});*/

			//hot note
			Note.get({page:0, size: 3, sortBy: 'commentCount', direction: 'DESC'}, function(noteData){
				$scope.hotNotes = noteData.content;
			})
		});
	}]);

ctrls.filter('formatSize6', function(){
  return function(input){
    return input.substring(0, 6) + '...';
  }
});