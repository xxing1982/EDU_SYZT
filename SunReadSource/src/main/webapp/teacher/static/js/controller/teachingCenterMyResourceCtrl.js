ctrls.controller("myResourceController",['$scope', '$rootScope','$stateParams','config','Loadable', 'Editable', 'GetBooksByTag','Tag',
	function($scope, $rootScope,$stateParams,config,Loadable,Editable,GetBooksByTag,Tag){

		$scope.imageServer=config.IMAGESERVER;
		var page = 0;
		var size = 3;

		// Bs dropdown statues
		$scope.statuses = { LESSON: [{id: 0, name: "无限制",callback:function(){createLoadable(GetBooksByTag);}}],
												SUBJECT: [{id: 0, name: "无限制",callback:function(){createLoadable(GetBooksByTag);}}],
												GRADE: [{id: 0, name: "无限制",callback:function(){createLoadable(GetBooksByTag);}}],
												CHAPTER: [{id: 0, name: "无限制",callback:function(){createLoadable(GetBooksByTag);}}],
												THEME: [{id: 0, name: "无限制",callback:function(){createLoadable(GetBooksByTag);}}] };
		$scope.selected_status = { LESSON: 0,
															SUBJECT: 0,
															GRADE: 0,
															CHAPTER: 0,
															THEME: 0 };

		// The tag object
		$scope.tags = Tag.get({}, function(){
				var tags = $scope.tags;
				for ( var i = 0; i < tags.length; i++ ){
					temp = {
						id: tags[i].id,
						name: tags[i].name,
						callback:function(){createLoadable(GetBooksByTag);}
					}
					$scope.statuses[tags[i].type].push(temp);
				}
		});
			function createLoadable(getBookByTag){
				$scope.bookTagLoadable = new Loadable();
				// Set the parameters of loadable
				$scope.bookTagLoadable.size = size;
				$scope.bookTagLoadable.page = page;
				// Set the $resource arguments like {by: "books"}
				$scope.bookTagLoadable.arguments = $scope.selected_status  ;

				$scope.bookTagLoadable.build(getBookByTag);

				$scope.bookTagLoadable.get();

				console.log($scope.bookTagLoadable);

			}

			createLoadable(GetBooksByTag);
  }]);
