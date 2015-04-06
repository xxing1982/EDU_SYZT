//mainCtrl.js
var ctrls = angular.module('nourControllers',['nourConfig', 'ngResource', 'userServices', 'noteServices', 'paraServices', 'commentServices'
                                              ,'examServices'
                                              ,'bookDetailServices','bookshelfServices','bookInShelfServices','addbookToShelfServices'
                                              ,'lackFeedbackServices','conditionSearchServices','quickSearchServices'
                                              ,'weeklyHotServices','monthlyHotServices'
                                              ,'weeklyRecommendServices','monthlyRecommendServices']);

ctrls.controller("mainController", ['$rootScope', '$scope', 'Student',"Bookshelf", "Note", 
    function ($rootScope, $scope, Student,Bookshelf) {
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
    
    //bookshelf
    Bookshelf.get({id : $rootScope.id}, function(data){
        console.log(data);
    })

    //note
    Note.get({page:0, size: 3, sortBy: 'commentCount', direction: 'DESC'}, function(data){
        console.log(data.content);
        $scope.hotNotes = data.content;   
    })



    }]);
ctrls.filter('formatImg', function(){
    return function(input){
        if (input == undefined || input == "") {
            return "../static/img/picture.jpg";
        };
        return input;
    }
});

ctrls.filter('formatSize6', function(){
    return function(input){
        return input.substring(0, 6) + '...';
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