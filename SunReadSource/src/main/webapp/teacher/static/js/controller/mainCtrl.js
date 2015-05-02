//mainCtrl.js
var ctrls = angular.module('nourControllers', ['nourConfig', 'ngResource', 
	'userServices', 'classServices', 'noteServices', 'actionServices', 'pageableServices']);

ctrls.controller("mainController",['$scope', '$rootScope', 'Teacher', "Class", "Note", 'Action', 'Pageable',
	function($scope, $rootScope, Teacher, Class, Note, Action, Pageable){
		Teacher.get({id: $rootScope.id}, function(teacher){
			$scope.teacher = teacher;
			// Create a classes entitiy
			Class.get({id: $scope.teacher.classId}, function(classData){
				$scope.teacher.class=classData.name;
				$scope.teacher.school=classData.campusName;
			});

			// Create a pageable entity of actions
			$scope.actionPageable = new Pageable();

     		// Set the parameters of pageable
      		$scope.actionPageable.size = 3;

      		// Build the pageable object
      		$scope.actionPageable.build(Action);

      		// Show the page 1
      		$scope.actionPageable.showPage(1);

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