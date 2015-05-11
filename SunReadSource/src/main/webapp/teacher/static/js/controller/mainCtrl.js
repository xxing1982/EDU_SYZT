//mainCtrl.js
var ctrls = angular.module('nourControllers', ['nourConfig', 'ngResource',
	'userServices', 'classServices', 'noteServices', 'actionServices', 'pageableServices', 'quickSearchServices',
	'lackFeedbackServices','popularSearchServices','conditionSearchServices','dictionariesService','bookDetailServices','reviewServices',
	'hotreaderServices', 'orderServices', 'loadableServices', 'editableServices', 'taskServices', 'checklist-model', 'dropzoneServices', 'coinHistoryServices']);


ctrls.controller("mainController",['$scope', '$rootScope', 'config', 'Teacher', "Class", "Note", 'Action', 'Pageable', 'QuickSearch', 'Hotreader',
	function($scope, $rootScope, config, Teacher, Class, Note, Action, Pageable, QuickSearch, Hotreader){
		$scope.imageServer = config.IMAGESERVER;
		Teacher.get({id: $rootScope.id}, function(teacher){
			teacher.currentPicture = teacher.picture === ""? "../static/img/myBookshelf/addPhoto.png" : config.IMAGESERVER + teacher.picture;
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

			//
			QuickSearch.get({page:0,size:4,sortBy:"statistic.recommends"},function(popularDate){
				$scope.recommends = popularDate.content;
			})

			// Create a Hotreaders entitiy
			$scope.hotReaders={};
			Hotreader.get({by: 'campus', id : teacher.campusId, page: 0, size: 3 }, function(hotData){
				hotData.content[0].picture = hotData.content[0].picture == "" ? "../static/img/myBookshelf/addPhoto.png" : config.IMAGESERVER + dataHot.content[0].picture;
				$scope.hotReaders.first = hotData.content[0];
				$scope.hotReaders.others = hotData.content.slice(1);
			});
		});
	}]);

ctrls.filter('formatSize6', function(){
	return function(input){
		return input.substring(0, 6) + '...';
	}
});

ctrls.filter('formatParagraph', function(){
  return function(data) {
   if (!data) return data;
   return data.replace(/[^\S\n]/g, '&nbsp;').replace(/\n/g, '<br/>');
 	};
});
