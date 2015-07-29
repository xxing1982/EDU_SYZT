ctrls.controller("studentCtrl",['$scope', '$rootScope', 'Students','Pageable', 'GetStudent', '$stateParams',
	function($scope, $rootScope, Students, Pageable, GetStudent, $stateParams){
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