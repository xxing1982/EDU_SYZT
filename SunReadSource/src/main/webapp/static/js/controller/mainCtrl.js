//mainCtrl.js
var ctrls = angular.module('nourControllers',['nourConfig', 'ngResource', 'userServices','bookshelfServices', 'noteServices', 'commentServices']);

ctrls.controller("mainController", ['$scope', 'User',"Bookshelf", function ($scope, User,Bookshelf) {
	//userinfo
    $scope.userInfo = {
    	'name': '张晓晨',
    	'portrait': '../static/img/picture.jpg',
    	'sex': 'm',
    	'school': '北京市101中学',
    	'class': '三年（2）班 ',
    	'level': '4',
    	'treasure': '300',
    	'integral': '102'
    };
    //test function to get data list
    var testDataList = User.query(function(){
        console.log(testDataList);
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
    var bookshelf = Bookshelf.get(function(){
        console.log(bookshelf);
    });
    $scope.bookshelf = bookshelf;
    $scope.bookshelf.finishStatus = 80;
}]);

ctrls.filter('formatSex', function(){
	return function(input){
		if (input == 'f') {
    		return "女生";
    	};
    	return "男生";
	}
});