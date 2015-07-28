ctrls.controller("eduGroupCtrl",['$scope', '$rootScope', 'SchoolAdmin', 'Pageable', 'GetEduGroup', '$stateParams',
	function($scope, $rootScope, SchoolAdmin, Pageable, GetEduGroup, $stateParams){
		
		//add
		$scope.add = {};
		$scope.AddSys = function(){
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