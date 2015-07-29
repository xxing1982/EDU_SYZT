ctrls.controller("schoolCtrl",['$scope', '$rootScope', 'Campus', 'Pageable', 'GetCampus', '$stateParams',
	function($scope, $rootScope, Campus, Pageable, GetCampus, $stateParams){

		//add
		/*$scope.add = {};
		$scope.AddSys = function(){
			Campus.Add($scope.add.userid, $scope.add.password, function(){
				$("#addModal").modal('hide');
			})
		}*/

		//update
		$scope.edit = {};
		$scope.updateSys = function(item){
			$scope.message = "";
			temp = angular.copy(item);
			$scope.edit.id = temp.id;
			$scope.edit.name = temp.name;
			$scope.edit.headmaster = temp.headmaster;
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