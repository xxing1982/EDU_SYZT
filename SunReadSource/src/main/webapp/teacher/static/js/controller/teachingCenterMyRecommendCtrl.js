ctrls.controller("myRecommendController",['$scope', '$rootScope','Loadable', 'Editable', 'GetRecommends',
	function($scope, $rootScope,Loadable,Editable,GetRecommends){

    console.log($rootScope.id);
    var page = 0;
    var size = 5;
    var arguments = {
      by: 'teacher',
      id: $rootScope.id
    }

    $scope.getRecommends = function(){
      $scope.recommendsLoadable = new Loadable();

      // Set the parameters of loadable
      $scope.recommendsLoadable.size = size;
      $scope.recommendsLoadable.page = page;

      // Set the $resource arguments like {by: "books"}
      $scope.recommendsLoadable.arguments = {by:'teacher',id:$rootScope.id} ;

      $scope.recommendsLoadable.build(GetRecommends);

			$scope.recommendsLoadable.get();

      console.log($scope.recommendsLoadable);

    }

		$scope.getRecommends();

		$scope.showReason=function(reason){
				$rootScope.modal = {title: "奖励原因", content: reason};
				$('#alert-modal').modal();
		}
}]);

ctrls.filter('isManditory',function(){
  return function(input){
    if(input)
      return '必读';
    else
      return '选读';
  }
});
