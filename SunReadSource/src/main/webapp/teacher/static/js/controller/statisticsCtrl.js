ctrls.controller("statisticsController", ['$rootScope', '$scope', 'Teacher', function ($rootScope, $scope, Teacher) {
    
    $rootScope.staticsticsRouteMap = {
        students: /\/statistics\/students.*/,
        classes: /\/statistics\/classes.*/
    }
    
    // Get the teacher entity by rootScope id
    $scope.teacher = Teacher.get({ id: $rootScope.id }, function(){
        
    });
    
    $scope.filtersGroups = [
        {
            name: '选择地区',
            filters: {
                province: {
                    name: '省',
                    statuses: [],
                    selected_status: 0,
                },
                city: {
                    name: '市',
                    statuses: [],
                    selected_status: 0,
                },
                district: {
                    name: '区县',
                    statuses: [],
                    selected_status: 0,
                }
            }
        },
        {
            name: '选择学校',
            filters: {
                edugroup: {
                    name: '集团',
                    statuses: [],
                    selected_status: 0,
                },
                campus: {
                    name: '学校',
                    statuses: [],
                    selected_status: 0,
                },
                schoolDistrict: {
                    name: '校区',
                    statuses: [],
                    selected_status: 0,
                }
            }
        },
        {
            name: '选择班级',
            filters: {
                grade: {
                    name: '年级',
                    statuses: [],
                    selected_status: 0,
                },
                class: {
                    name: '班级',
                    statuses: [],
                    selected_status: 0,
                }
            }
        }
    ];
}]);