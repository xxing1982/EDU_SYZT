//mainCtrl.js
var ctrls = angular.module('nourControllers',['nourConfig', 'ngResource', 'userServices', 'noteServices', 'paraServices', 'commentServices'
                                              ,'examServices'
                                              ,'bookDetailServices','bookshelfServices','bookInShelfServices','addbookToShelfServices'
                                              ,'lackFeedbackServices','conditionSearchServices','quickSearchServices'
                                              ,'weeklyHotServices','monthlyHotServices'
                                              ,'weeklyRecommendServices','monthlyRecommendServices']);

ctrls.controller("mainController", ['$rootScope', '$scope', 'Student',"Bookshelf", function ($rootScope, $scope, Student,Bookshelf) {
	$rootScope.id = 2;
    //test function to get data list
    $scope.userInfo = Student.get({id : $rootScope.id} ,function(data){
        $rootScope.student = data;
    });

    //bookshelf
        //bookshelf
//    $scope.bookshelf = {
//    	'finishStatus': '80%',
//    	'readed': {
//    		'must': '5',bookshelfServices
//    		'select': '12'
//    	},
//    	'unread': {
//    		'must': '12',
//    		'select': '8'
//    	},
//    };
    /*var bookshelf = Bookshelf.get(function(){
        console.log(bookshelf);
    });
    $scope.bookshelf = bookshelf;
    $scope.bookshelf.finishStatus = 80;*/
}]);

ctrls.filter('formatImg', function(){
    return function(input){
        if (input == undefined || input == "") {
            return "../static/img/picture.jpg";
        };
        return input;
    }
});

ctrls.filter('formatGender', function(){
	return function(input){
		if (input == 'male') {
    		return "男生";
    	};
    	return "女生";
	}
});