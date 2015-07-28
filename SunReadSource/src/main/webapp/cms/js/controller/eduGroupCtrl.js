ctrls.controller("eduGroupCtrl",['$scope', '$rootScope', 'EduGropu', 'Pageable', 'GetEduGroup', '$stateParams',
	function($scope, $rootScope, EduGropu, Pageable, GetEduGroup, $stateParams){
		
		//add
		$scope.add = {};
		$scope.AddSys = function(){
			EduGropu.Add($scope.add, function(){
				$("#addModal").modal('hide');
				location.reload();
			})
		}

		//update
		$scope.edit = {};
		$scope.updateSys = function(item){
			$scope.message = "";
			var temp = angular.copy(item);
			$scope.edit.id = temp.id;
			$scope.edit.name = temp.name;
		}
		$scope.EditSys = function(){
			EduGropu.Update($scope.edit, function(){
				$("#editModal").modal('hide');
				location.reload();
			})
		}

		$scope.deleteSys = function(item){
			$rootScope.confirm_modal = {};
			$rootScope.confirm_modal.title="提示";
			$rootScope.confirm_modal.content="确定删除吗？";
			$rootScope.confirm_modal.click = function(){
				EduGropu.Delete(item.id, function(){
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
        	$scope.searchPageable.build(GetEduGroup);
        	$scope.searchPageable.showPage($stateParams.page === undefined ? 1 : $stateParams.page);
		}
		$scope.createPageable();
	}]);