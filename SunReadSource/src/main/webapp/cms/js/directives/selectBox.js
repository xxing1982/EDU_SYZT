var myDirective = angular.module("nourDirectives", ['provinceServices']);

myDirective.directive('chooseschool', ['Province', function(Province){
	var directive = {};

	directive.restrict = 'E';

	directive.templateUrl = "/cms/partials/selectSchool.html";

	directive.controller = function($scope){
		$scope.isDisabled = false;
		if ($scope.selectSchool.isEdit == false) {
			$scope.isDisabled = true;
			return;
		};
		if (sessionStorage.getItem("cmsRoleId") == 8 || sessionStorage.getItem("cmsRoleId") == 9) {
			$scope.isDisabled = true;
			var school = {};
			school.id = sessionStorage.getItem("campusId");
			$scope.selectSchool.checkOne(school);
			return;
		}

		/*Province.GetProvinces(function(data){
			$scope.provinces = data.content;
		});*/

		Province.GetSchools(function(data){
			$scope.schools = data.content;

			$scope.chooseSchool = function(school){
				$scope.searchTextSschol = school.name;
				$scope.selectSchool.checkOne(school);
			}
		});

		$scope.c_province = function(){
			$scope.SelectedCity = "";
			$scope.SelectedRegion = "";
			$scope.SelectedSchool = "";
		}

		$scope.c_city = function(){
			$scope.SelectedRegion = "";
			$scope.SelectedSchool = "";
		}

		$scope.c_region = function(){
			if ($scope.selectSchool.isShowSchool) {
				return;
			}
			$scope.selectSchool.checkOne($scope.SelectedSchool);
		}

		$scope.c_school = function(){
			if (!$scope.selectSchool.isShowSchool) {
				return;
			}
			$scope.selectSchool.checkOne($scope.SelectedSchool);
		}
	}

	return directive;
}]);
