ctrls.controller("regionCtrl",['$scope', '$rootScope', 'config', 'Province',
	function($scope, $rootScope, config, Province){
		Province.GetProvinces(function(data){
			$scope.provinces = data.content;
		});

		$scope.c_province = function(){
			$("#add").attr("disabled",true); 
			$scope.SelectedCity = "";
		}

		$scope.c_city = function(){
			$("#add").attr("disabled",false); 
		}

		//add
		$scope.add = {};
		$scope.AddSys = function(){
			$scope.add.creationTime = new Date().toLocaleDateString();
			$scope.add.regionType = 'district';
			$scope.add.parent = $scope.SelectedCity.id;
			Province.Add($scope.add, function(){
				$("#addModal").modal('hide');
			})
		}

		//update
		$scope.edit = {};
		$scope.updateSys = function(item){
			$scope.message = "";
			$scope.edit = angular.copy(item);
		}
		$scope.EditSys = function(){
			Province.UpdateRegion($scope.edit, function(){
				$("#editModal").modal('hide');
			})
		}
	}]);