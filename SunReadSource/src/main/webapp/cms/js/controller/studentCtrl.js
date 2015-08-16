ctrls.controller("studentCtrl",['$scope', '$rootScope', 'Students','Pageable', 'GetStudent', '$stateParams', 'Dropzone', 'GetCampus', 'GetClazzs', 'config',
	function($scope, $rootScope, Students, Pageable, GetStudent, $stateParams, Dropzone, GetCampus, GetClazzs, config){
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

				GetClazzs.get({campusid: $scope.campusidSelected, size: 10000, page: 0},function(data){
					for(var i = 0; i < data.content.length; i++){
						data.content[i].name = data.content[i].grade + "年" + data.content[i].name;
					}
					$scope.CLZ = data.content;
				});
			}
		};

		$scope.add = {};
		$scope.add.nickname = "";
		$scope.add.password = "123456";
		$scope.add.gradeId = 0;
		//$scope.add.birthday = new Date();
		$scope.add.roles = [{
            "id":3,
            "name":"ROLE_STUDENT",
            "desc":"User"
        }];
		$scope.add.enabled = true;
		$scope.add.accountNonExpired = true;
		$scope.add.accountNonLocked = true;
		$scope.add.credentialsNonExpired = true;
		$scope.dropzone = Dropzone(config.USERICON, function(url){
            $scope.add.picture = url;
        } );
		$scope.AddSys = function(){
			$scope.add.campusId = $scope.campusidSelected;
			//$scope.add.birthday = 1427618967110;
			$scope.add.gender = $scope.add.gender == 0 ? "male":"famale";
			Students.Add($scope.add, function(data) {
    			location.reload();
  			});
			
		}
		$scope.c_CLZ = function(){
			$scope.add.clazzId = $scope.SelectedCLZ.id;
		}


		//update
		$scope.edit = {};
		$scope.updateSys = function(item){
			$scope.message = "";
			$scope.selectStudentID = item.id;
			temp = angular.copy(item);
			$scope.edit.nickname = temp.nickname;
			$scope.edit.phoneNumber = temp.phoneNumber;
			$scope.edit.qqId = temp.qqId;
			$scope.edit.wechatId = temp.wechatId;
			$scope.edit.email = temp.email;
		}
		$scope.EditSys = function(){
			$scope.message = "";
			var item = $scope.edit;
			if ($scope.repassword != item.password) {
				$scope.message = "两次密码不一致！";
				return;
			}

			Students.Update($scope.selectStudentID, item, function(){
				$("#editModal").modal('hide');
				location.reload();
			})
		}

		$scope.deleteSys = function(item){
			$rootScope.confirm_modal = {};
			$rootScope.confirm_modal.title="提示";
			$rootScope.confirm_modal.content="确定删除吗？";
			$rootScope.confirm_modal.click = function(){
				Students.Delete(item.id, function(){
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
        	$scope.searchPageable.build(GetStudent);
        	$scope.searchPageable.showPage($stateParams.page === undefined ? 1 : $stateParams.page);
		}
	}]);