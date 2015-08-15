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
			$scope.add.province = $scope.SelectedProvince.name;
			$scope.add.city = $scope.SelectedCity.name;
			Province.Add($scope.add, function(dataP){
				$("#addModal").modal('hide');
				$scope.SelectedCity.subRegion.push(dataP);
			})
		}

		//update
		$scope.edit = {};
		$scope.updateSys = function(item){
			$scope.message = "";
			var tempItem = angular.copy(item);
			$scope.edit.id = tempItem.id;
			$scope.edit.regionType = tempItem.regionType;
			$scope.edit.name = tempItem.name;
			$scope.edit.subRegion = tempItem.subRegion;
		}
		$scope.EditSys = function(){
			Province.UpdateRegion($scope.edit, function(){
				$("#editModal").modal('hide');
				location.reload();
			})
		}
		$scope.deleteSys = function(item){
			$rootScope.confirm_modal = {};
			$rootScope.confirm_modal.title="提示";
			$rootScope.confirm_modal.content="确定删除吗？";
			$rootScope.confirm_modal.click = function(){
				Province.Delete(item.id, function(){
					location.reload();
				})
			}
			$('#confirm-modal').modal();
		}
	}]);