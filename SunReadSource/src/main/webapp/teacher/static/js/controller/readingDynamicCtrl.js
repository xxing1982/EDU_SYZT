//readingDynamic.js

ctrls.controller("readingDynamicController",["$scope","$rootScope","Teacher","Campus","Action","Pageable","Hotclazz","Hotreader","config",
                                             
     function($scope,$rootScope,Teacher,Campus,Action,Pageable,Hotclazz,Hotreader,config){
	
		var teacher = Teacher.get({id:$rootScope.id},function(){
			
			$scope.campus = Campus.get({id:teacher.campusId});
			
			console.info($scope.campus);
			
			$scope.hotClazzs = Hotclazz.get({by : "campuses" , id : teacher.campusId, page : 0 , size : 3})
			
			console.info($scope.hotClazzs);
			
			$scope.hotReaders = Hotreader.get({by : "campus" , id : teacher.campusId, page : 0 , size : 3});
			
			console.info($scope.hotReaders)
		});
		
	 $scope.actionPageable = new Pageable();
	 $scope.actionPageable.size = 5;
	 $scope.actionPageable.build(Action);
	 $scope.actionPageable.showPage(1);
	 
	 
	 $scope.imageServer = config.IMAGESERVER;
	
}]);