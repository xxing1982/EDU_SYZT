ctrls.controller("myResourceController",['$scope', '$rootScope','$stateParams','config','Loadable', 'Editable', 'GetBooksByTag',
	function($scope, $rootScope,$stateParams,config,Loadable,Editable,GetBooksByTag){

		$scope.imageServer=config.IMAGESERVER;
		var page = 0;
		var size = 4;
		//Book Tag
		$scope.searchArguments = {
				lesson:0,
				grade:0,
				subject:0,
				chapter:0,
				theme:0
			}

			function createLoadable(getBookByTag){
				$scope.bookTagLoadable = new Loadable();
				// Set the parameters of loadable
				$scope.bookTagLoadable.size = size;
				$scope.bookTagLoadable.page = page;
				// Set the $resource arguments like {by: "books"}
				$scope.bookTagLoadable.arguments = $scope.searchArguments  ;

				$scope.bookTagLoadable.build(getBookByTag);

				$scope.bookTagLoadable.get();

				console.log($scope.bookTagLoadable);

			}

			createLoadable(GetBooksByTag);
  }]);
