ctrls.controller("userCtrl", ['$scope', 'User', function ($scope, User) {
	$scope.users = [
	{
		"id": 1,
		"userName": "Lily",
		"password": "111111",
		"phoneNumber": "131111",
		"email": "china@china.com",
		"address": "china",
		"gender": 2,
		"age": 22,
		"role": "1",
		"birthday": "2013-01-01"
	},
	{
		"id": 2,
		"userName": "Lucy",
		"password": "111111",
		"phoneNumber": "131111",
		"email": "china@china.com",
		"address": "china",
		"gender": 2,
		"age": 22,
		"role": "1",
		"birthday": "2013-01-01"
	},
	{
		"id": 1,
		"userName": "Tom",
		"password": "111111",
		"phoneNumber": "131111",
		"email": "china@china.com",
		"address": "china",
		"gender": 1,
		"age": 23,
		"role": "1",
		"birthday": "2013-01-01"
	}
	];
	$scope.selected={};
	$scope.Add = function(){
		$scope.selected={};
		$scope.selected.title = "Add";
		
		var user = new User();
		
		user = $scope.selected;

		//save option
		user.$save(function(res){

		});
	}
	$scope.Update = function(userid){
		$scope.selected.title = "Edit";
		$scope.selected.user = User.get({id: userid});

		//save option
		$scope.selected.user.$update(function(res){

		});
	}
	$scope.Delete = function(userid){
		User.delete({id: userid}, function(){

		})
	}
	
}]);