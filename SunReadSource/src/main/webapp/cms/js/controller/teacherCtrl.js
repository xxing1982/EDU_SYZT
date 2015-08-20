ctrls.controller("TeacherCtrl",['$scope', '$rootScope', 'Teachers', 'Pageable', 'GetTeachers', '$stateParams',
	function($scope, $rootScope, Teachers, Pageable, GetTeachers, $stateParams){
		$scope.selectSchool = {
			isEdit: true,
			isShowSchool: true,
			checkOne: function(item){
				$("#add").attr("disabled",false); 
				$scope.campusidSelected = item.id;
				$scope.searchArguments = {
        			campusid:item.id,
				}
				$scope.createPageable();
			}
		};

		//update grade
		$scope.updateGrade = function(item){
			$rootScope.confirm_modal = {};
			$rootScope.confirm_modal.title="提示";
			$rootScope.confirm_modal.content="确定给这个班升年级吗？";
			$rootScope.confirm_modal.click = function(){
				Teachers.UpdateGread(item.id, function(){
					$('#confirm-modal').modal('hide');
				})
			}
			$('#confirm-modal').modal();
		}

		//update
		$scope.edit = {};
		$scope.updateSys = function(item){
			$scope.message = "";
			temp = angular.copy(item);
			$scope.edit.id = temp.id;
			$scope.edit.name = temp.name;
		}

		$scope.EditSys = function(){
			$scope.message = "";

			Teachers.Update($scope.edit, function(){
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
				Teachers.Delete(item.id, function(){
					location.reload();
				})
			}
			$('#confirm-modal').modal();
		}

		
		$scope.searchArguments = {
        	name:"",
    	}

	    $scope.searchByName = function(){
	        $scope.createPageable();
	    }
		$scope.createPageable = function (){
			$scope.searchPageable = new Pageable();

			$scope.searchPageable.size = 6;
			$scope.searchPageable.page = 0;

			$scope.searchPageable.pageNumbers.startPage = 1;
        	$scope.searchPageable.pageNumbers.content.length = 8;
        	$scope.searchPageable.arguments=$scope.searchArguments;
        	// Set the placeholder elements
        	$scope.searchPageable.placeHolders.placeHoldersElement = {title: ""};

        	// Build the pageable object
        	$scope.searchPageable.build(GetTeachers);
        	$scope.searchPageable.showPage($stateParams.page === undefined ? 1 : $stateParams.page);
		}
	}]);