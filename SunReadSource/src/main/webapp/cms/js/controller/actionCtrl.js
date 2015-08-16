ctrls.controller("ActionCtrl",['$scope', '$rootScope', 'Action', 'Pageable', '$stateParams',
	function($scope, $rootScope, Action, Pageable, $stateParams){
		
		//add
		$scope.add = {};
		$scope.AddSys = function(){
			Action.save($scope.add, function(){
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
			$scope.edit.title = temp.title;
			$scope.edit.content = temp.content;
		}

		$scope.EditSys = function(){
			$scope.message = "";
			var item = $scope.edit;
			//if (item.oldpassword != item.password) {
			//	$scope.message = "旧密码输入错误！";
			//	return;
			//}
			$scope.message = "";

			Action.update({id : $scope.edit.id}, $scope.edit, function(){
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
					Action.del({id: item.id}, function(){
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
        	$scope.searchPageable.build(Action);
        	$scope.searchPageable.showPage($stateParams.page === undefined ? 1 : $stateParams.page);
		}
		$scope.createPageable();
	}]);

ctrls.filter('fmtSize10', function () {
    return function (data) {
        if (data.length > 10) {
        	return data.substring(0, 10) + "...";
        }
        return data;
    };
});