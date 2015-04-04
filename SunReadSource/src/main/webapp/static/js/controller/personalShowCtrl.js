//personalShow.js

ctrls.controller("personalShowController", ['$scope', 'User', function ($scope, User) {
    $scope.personalShow = '个人展示';
    
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
}]);