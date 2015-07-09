var myDirective = angular.module("nourDirectives", ['provinceServices']);

myDirective.directive('chooseschool', ['Province', function(Province){
	var directive = {};

	directive.restrict = 'E';

	directive.templateUrl = "/cms/partials/selectSchool.html";

	directive.controller = function($scope){
		$scope.isDisabled = false;
		//if ($scope.selectSchool.isEdit == false) {
		//	$scope.isDisabled = true;
		//	return;
		//};

		Province.GetProvinces(function(data){
			$scope.provinces = data.content;
		});

		$scope.c_province = function(){
			$scope.SelectedCity = "";
			$scope.SelectedRegion = "";
		}

		$scope.c_city = function(){
			$scope.SelectedRegion = "";
		}
	}

	return directive;
}]);
