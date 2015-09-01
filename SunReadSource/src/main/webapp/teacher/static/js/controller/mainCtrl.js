//mainCtrl.js
var ctrls = angular.module('nourControllers', ['nourConfig', 'ngResource',
	'userServices', 'classServices', 'noteServices', 'noteViewServices', 'commentServices', 'actionServices', 'pageableServices', 'quickSearchServices','bookshelfServices',
	'lackFeedbackServices','popularSearchServices','conditionSearchServices','dictionariesService','bookDetailServices','reviewServices','recommendServices',
	'hotreaderServices', 'orderServices', 'loadableServices', 'editableServices', 'taskServices', 'checklist-model', 'dropzoneServices', 'coinHistoryServices','campusServices',
	'ngSanitize','hotclazzServices', 'booktagServices', 'tagServices','likeServices','messageServices','teacherServices', 'schoolDistrictServices','regionServices', 'eduGroupServices',
    'gradeServices', 'sortableServices', 'sumStatisticServices']);


ctrls.controller("mainController",['$scope', '$rootScope', 'config', 'Teacher', "Class", "Note", 'Action', 'Pageable', 'QuickSearch', 'Hotreader', 'MyRecommend', 'TagCategory',
	function($scope, $rootScope, config, Teacher, Class, Note, Action, Pageable, QuickSearch, Hotreader, MyRecommend, TagCategory){
		$scope.imageServer = config.IMAGESERVER;
		Teacher.get({id: $rootScope.id}, function(teacher){
			teacher.currentPicture = teacher.picture === ""? "../static/img/myBookshelf/addPhoto.png" : config.IMAGESERVER + teacher.picture;
			$scope.teacher = teacher;
			// Create a classes entitiy
			Class.get({id: $scope.teacher.currentClassId}, function(classData){
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
			Hotreader.get({by: 'campus', id : teacher.campusId, page: 0, size: 3, sortBy: "point" }, function(hotData){
				hotData.content[0].picture = hotData.content[0].picture == "" ? "../static/img/myBookshelf/addPhoto.png" : config.IMAGESERVER + hotData.content[0].picture;
				$scope.hotReaders.first = hotData.content[0];
				$scope.hotReaders.others = hotData.content.slice(1);
			});

			//MyRecommend
			MyRecommend.get(teacher.id, function(myRecommend){
				if (myRecommend.length > 4) {
					myRecommend = myRecommend.slice(0, 4);
				}
				for(var i = 0; i < myRecommend.length; i++){
					myRecommend[i].pictureUrl = myRecommend[i].pictureUrl === ""? "../static/img/book.jpg" : config.IMAGESERVER + myRecommend[i].pictureUrl;
				}
				$scope.myRecommend = myRecommend;
			});

			//Tag
			TagCategory.get({}, function(myTag){
				$scope.Category = {};
				$scope.Category.chinese = 0;
				$scope.Category.math = 0;
				$scope.Category.english = 0;
				$scope.Category.science = 0;
				$scope.Category.other = 0;

				for(var i = 0; i < myTag.length; i++){
					if(myTag[i].subject == "语文"){
						$scope.Category.chinese += myTag[i].count;
					}else if(myTag[i].subject == "数学"){
						$scope.Category.math += myTag[i].count;
					}else if(myTag[i].subject == "英语"){
						$scope.Category.english += myTag[i].count;
					}else if(myTag[i].subject == "科学"){
						$scope.Category.science += myTag[i].count;
					}else {
						$scope.Category.other += myTag[i].count;
					}
				}
			});
		});
}]);

ctrls.filter('formatSize6', function(){
	return function(input){
		return input.substring(0, 6) + '...';
	}
});

ctrls.filter('formatSize10', function(){
	return function(input){
		if(input == undefined || input == "")
			return input;
		else
			return input.substring(0, 10) + '...';
		}
});


ctrls.filter('formatParagraph', function(){
	return function(data) {
		if (!data) return data;
		return data.replace(/[^\S\n]/g, '&nbsp;').replace(/\n/g, '<br/>');
	};
});

ctrls.filter('formatPictrueUrl', function () {
    return function (data) {
        var url = data.slice(27);
        if (url.search('/') === 0) return data;
        return url;
    };
});
