ctrls.controller("schoolSuperAdminCtrl",['$scope', '$rootScope', 'SuperSchoolAdmin', 'Pageable', 'GetSuperSchoolAdmin', '$stateParams',
	function($scope, $rootScope, SuperSchoolAdmin, Pageable, GetSuperSchoolAdmin, $stateParams){
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

		//add
		$scope.add = {};
		$scope.AddSys = function(){
			SuperSchoolAdmin.Add($scope.campusidSelected, $scope.add.userid, $scope.add.password, function(){
				$("#addModal").modal('hide');
			})
		}

		//update
		$scope.edit = {};
		$scope.updateSys = function(item){
			$scope.message = "";
			$scope.edit = item;
		}

		$scope.EditSys = function(){
			$scope.message = "";
			var item = $scope.edit;
			//if (item.oldpassword != item.password) {
			//	$scope.message = "旧密码输入错误！";
			//	return;
			//}
			$scope.message = "";

			SuperSchoolAdmin.Update(item.userId, item.oldpassword, item.newpassword, function(){
				$("#editModal").modal('hide');
			})
		}

		//delete
		$scope.deleteSys = function(item){
			$rootScope.confirm_modal = {};
				$rootScope.confirm_modal.title="提示";
				$rootScope.confirm_modal.content="确定删除吗？";
				$rootScope.confirm_modal.click = function(){
					SuperSchoolAdmin.Delete(item.id, function(){
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
        	$scope.searchPageable.arguments=$scope.searchArguments;
        	// Set the placeholder elements
        	$scope.searchPageable.placeHolders.placeHoldersElement = {title: ""};

        	// Build the pageable object
        	$scope.searchPageable.build(GetSuperSchoolAdmin);
        	$scope.searchPageable.showPage($stateParams.page === undefined ? 1 : $stateParams.page);
		}
	}]);