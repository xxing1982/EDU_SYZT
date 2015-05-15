ctrls.controller("myRecommendController",['$scope', '$rootScope','Loadable', 'Editable', 'GetRecommends',
	function($scope, $rootScope,Loadable,Editable,GetRecommends){

    console.log($rootScope.id);
    var page = 0;
    var size = 1;
    var arguments = {
      by: 'teacher',
      id: $rootScope.id
    }

    var getRecommends = function(){
      $scope.recommendsLoadable = new Loadable();

      // Set the parameters of loadable
      $scope.recommendsLoadable.size = size;
      $scope.recommendsLoadable.page = page;

      // Set the $resource arguments like {by: "books"}
      $scope.recommendsLoadable.arguments = {by:'teacher',id:8} ;

      $scope.recommendsLoadable.build(GetRecommends);
      console.log($scope.recommendsLoadable);
    }

    getRecommends();
}]);

ctrls.filter('isManditory',function(){
  return function(input){
    if(input)
      return '必读';
    else
      return '选读';
  }
});
