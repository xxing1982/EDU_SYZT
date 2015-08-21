ctrls.controller("statisticsController", ['$rootScope', '$scope', 'Teacher', 'Admin', function ($rootScope, $scope, Teacher, Admin) {
    
    $rootScope.staticsticsRouteMap = {
        students: /\/statistics\/students.*/,
        classes: /\/statistics\/classes.*/,
        campuses: /\/statistics\/campuses.*/
    }
    
    // Get the teacher entity by rootScope id
    $scope.teacher = eval($rootScope.type).get({ id: $rootScope.id }, function(){

        $scope.filtersGroups = [
            {
                name: '选择地区',
                filters: {
                    _1province: {
                        name: '省',
                        statuses: [],
                        selected_status: 0,
                        disabled: true
                    },
                    _2city: {
                        name: '市',
                        statuses: [],
                        selected_status: 0
                    },
                    _3district: {
                        name: '区县',
                        statuses: [],
                        selected_status: 0
                    }
                }
            },
            {
                name: '选择学校',
                filters: {
                    _1edugroup: {
                        name: '集团',
                        statuses: [],
                        selected_status: 0
                    },
                    _2campus: {
                        name: '学校',
                        statuses: [],
                        selected_status: 0
                    },
                    _3schoolDistrict: {
                        name: '校区',
                        statuses: [],
                        selected_status: 0
                    }
                }
            },
            {
                name: '选择班级',
                filters: {
                    _1grade: {
                        name: '年级',
                        statuses: [],
                        selected_status: 0
                    },
                    _2class: {
                        name: '班级',
                        statuses: [],
                        selected_status: 0
                    }
                }
            }
        ]; 
    });
    
}]);