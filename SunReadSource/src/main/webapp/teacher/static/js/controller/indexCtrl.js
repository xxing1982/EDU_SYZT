ctrls.controller('headNavCtrl', ['$rootScope', '$scope', '$location', '$window', 'Teacher', 'Admin', 'User', 'ShowAllClass',
function( $rootScope, $scope, $location, $window, Teacher, Admin, User, ShowAllClass) {

    // Route map regexp
    $rootScope.routeMap = {
        index: /^\/$/,
        teachingCenter: /\/teachingCenter.*/,
        readingDynamic: /\/readingDynamic.*/,
        statistics: /\/statistics.*/,
    }

    // Get the information of teacher
    eval($rootScope.type).get({id: $rootScope.id}, function(data){
        $rootScope.teacher = data
    }); 

    // Update the nav bar active
    $rootScope.isActive = function(routeRegexp) {
        return routeRegexp.test( $location.path() );
    }

    // The logout method
    $rootScope.logout = function(){
        sessionStorage.clear();
    }

    $rootScope.ShowClasses = function(){
        ShowAllClass.Gets($rootScope.id, function(data){
            $rootScope.Classes = data;
        })
    }
    $rootScope.UpdateClass = function(item){
        ShowAllClass.Change($rootScope.id, item.id, function(data){
            $rootScope.currentClassId = data.currentClassId;
            $("#changeClassModal").modal('hide');
            location.reload();
        })
    }
}]);

ctrls.controller('leftNavCtrl', ['$rootScope', '$scope', '$location',
    function( $rootScope, $scope, $location) {
        $rootScope.routeMapLeft = {
            myTask: /\/teachingCenter\/myTask.*/,
            myRecommend: /\/teachingCenter\/myRecommend.*/,
            myReward: /\/teachingCenter\/myReward.*/,
            myResource: /\/teachingCenter\/myResource.*/
        }

        // Update the nav bar active
        $rootScope.isActiveLeft = function(routeRegexp) {
            return routeRegexp.test( $location.path() );
        }
    }]);

ctrls.controller('recommendNavCtrl', ['$rootScope', '$scope', '$location',
    function( $rootScope, $scope, $location) {
        $rootScope.routeMapAddBook= {
            quick: /\/teachingCenter\/addBook\/quick.*/,
            advanced: /\/teachingCenter\/addBook\/advanced.*/,
            popular: /\/teachingCenter\/addBook\/popular.*/,
            recommend: /\/teachingCenter\/addBook\/recommend.*/
        }

        // Update the nav bar active
        $rootScope.isActiveAddBook = function(routeRegexp) {
            return routeRegexp.test($location.path() );
        }
    }]);


