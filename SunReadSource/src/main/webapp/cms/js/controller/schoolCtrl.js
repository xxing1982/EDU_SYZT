ctrls.controller("schoolCtrl",['$scope', '$rootScope', 'Campus', 'Pageable', 'GetCampus', '$stateParams', 'Province', 'GetEduGroup', 'GetSchoolDistricts',
	function($scope, $rootScope, Campus, Pageable, GetCampus, $stateParams, Province, GetEduGroup, GetSchoolDistricts){

		//add
		$scope.add = {};
		$scope.AddSys = function(){
			Campus.Add($scope.SelectedRegion.id ,$scope.add, function(){
				$("#addModal").modal('hide');
			})
		}
		Province.GetProvinces(function(data){
			$scope.provinces = data.content;
		});

		$scope.c_province = function(){
			$scope.SelectedCity = "";
		}

		$scope.c_city = function(){
			$scope.SelectedRegion = "";
		}

		$scope.c_region = function(){
		}
		
		GetEduGroup.get({size: 10000, page: 0},function(data){
			$scope.EDU = data.content;
		});
		$scope.c_EDU = function(){
			$scope.add.eduGroupName = $scope.SelectedEDU.name;
		}
		GetSchoolDistricts.get({size: 10000, page: 0},function(data){
			$scope.SD = data.content;
		});
		$scope.c_SD = function(){
			$scope.add.schoolDistrictName = $scope.SelectedSD.name;
		}

		//update
		$scope.edit = {};
		$scope.updateSys = function(item){
			$scope.message = "";
			temp = angular.copy(item);
			$scope.edit.id = temp.id;
			$scope.edit.name = temp.name;
			$scope.edit.headmaster = temp.headmaster;
			$scope.edit.wish = temp.wish;
			$scope.edit.description = temp.description;
		}

		$scope.EditSys = function(){
			Campus.Update($scope.edit, function(){
				$("#editModal").modal('hide');
				location.reload();
			})
		}

		//delete
		$scope.deleteSys = function(item){
			$rootScope.confirm_modal = {};
				$rootScope.confirm_modal.title="提示";
				$rootScope.confirm_modal.content="确定删除吗？";
				$rootScope.confirm_modal.click = function(){
					Campus.Delete(item.id, function(){
						location.reload();
					})
				}
				$('#confirm-modal').modal();
		}


		$scope.createPageable = function (){
			$scope.searchPageable = new Pageable();

			$scope.searchPageable.size = 6;
			$scope.searchPageable.page = 0;

			$scope.searchPageable.pageNumbers.startPage = 1;
        	$scope.searchPageable.pageNumbers.content.length = 8;

        	// Set the placeholder elements
        	$scope.searchPageable.placeHolders.placeHoldersElement = {title: ""};

        	// Build the pageable object
        	$scope.searchPageable.build(GetCampus);
        	$scope.searchPageable.showPage($stateParams.page === undefined ? 1 : $stateParams.page);
		}
		$scope.createPageable();
	}]);