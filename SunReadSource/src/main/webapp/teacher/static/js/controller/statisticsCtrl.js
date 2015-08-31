ctrls.controller("statisticsController", ['$rootScope', '$scope', 'Teacher', 'Admin', 'SchoolDistrict',
                                           'Region', 'EduGroup', 'Campus', 'Campuses', 'Grade', 'Classes',
                                           function ($rootScope, $scope, Teacher, Admin, SchoolDistrict, 
                                                      Region, EduGroup, Campus, Campuses, Grade, Classes) {
    
    $rootScope.staticsticsRouteMap = {
        students: /\/statistics\/students.*/,
        classes: /\/statistics\/classes.*/,
        campuses: /\/statistics\/campuses.*/
    }
    
    $scope.filtersGroups = [
        {
            name: '选择地区',
            filters: {
                _1province: {
                    name: '省',
                    statuses: [],
                    selected_status: 0
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
                _2schoolDistrict: {
                    name: '校区',
                    statuses: [],
                    selected_status: 0
                },
                _3campus: {
                    name: '学校',
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
    
    // Filters cache
    var districtFilters = $scope.filtersGroups[0].filters,
        campusFilters = $scope.filtersGroups[1].filters,
        classFilters = $scope.filtersGroups[2].filters;
    
    // Get the teacher entity by rootScope id
    $scope.teacher = eval($rootScope.type).get( { id: $rootScope.id } )
    
        // Get the container of region
        .$promise.then( function(teacher){
        
            $scope.teacher = teacher;
            
            // The region container cache 
            var regionContainerPromise,
                campusesPromise,
                classesPromise,
                classesPromiseCallback;
        
            switch($rootScope.type) {
                    
                case 'Admin':
                    
                    // Get campus and update schoolDistrict and edugroup
                    switch (teacher.roles[0].name) {
                        case 'ROLE_SCHOOLE_DISTRICT' :
                            $scope.SchoolDistrict = SchoolDistrict.get( {id: teacher.schoolDistrictId} );
                            regionContainerPromise = $scope.SchoolDistrict.$promise;
                            campusesPromise = regionContainerPromise.then( function(SchoolDistrict){
                                $scope.campuses = Campuses.get( {by: 'schoolDistrict', id: SchoolDistrict.id } );
                                campusFilters._2schoolDistrict.statuses = [{ id: 1, name: SchoolDistrict.name }];
                                campusFilters._2schoolDistrict.disabled = true;
                                campusFilters._1edugroup.hidden = true;
                                return $scope.campuses.$promise;
                            });
                            break;
                        case 'ROLE_GROUP' :                    
                            $scope.EduGroup = EduGroup.get( {id: teacher.eduGroupId} );
                            regionContainerPromise = $scope.EduGroup.$promise;
                            campusesPromise = regionContainerPromise.then( function(EduGroup){
                                $scope.campuses = Campuses.get( {by: 'eduGroup', id: EduGroup.id } );
                                campusFilters._1edugroup.statuses = [{ id: 1, name: EduGroup.name }];
                                campusFilters._1edugroup.disabled = true;
                                campusFilters._2schoolDistrict.hidden = true;
                                return $scope.campuses.$promise;
                            })
                            break;
                    }
                    
                    // Update campuses (Campus dropdown enabled)
                    classesPromise = campusesPromise.then( function(campuses){
                        for (var i = 0; i < campuses.length; i++) {
                            campusFilters._3campus.statuses[i] = campuses[i];
                            campusFilters._3campus.statuses[i].callback = function(){
                                console.log(campusFilters._3campus.selected_status);
                                classFilters._2class.selected_status = 0;
                                classFilters._2class.statuses = [];
                                Classes.get( {by: 'campus', id: campusFilters._3campus.selected_status } ).$promise
                                    .then(classesPromiseCallback);
                            };
                        }
                        campusFilters._3campus.selected_status = campuses[0].id;
                        $scope.Campus = campuses[0];
                        return Classes.get( {by: 'campus', id: $scope.Campus.id } ).$promise;
                    });
                    break;
                    
                case 'Teacher':
                    
                    // Update campus (Campus dropdown disabled)
                    $scope.Campus = Campus.get( {id: teacher.campusId} ); 
                    classesPromise = $scope.Campus.$promise.then( function(Campus){
                        $scope.Campus = Campus;
                        campusFilters._1edugroup.hidden = true;
                        campusFilters._2schoolDistrict.hidden = true;
                        campusFilters._3campus.statuses = [ {id: 1, name: Campus.name} ];
                        campusFilters._3campus.disabled = true;
                        return Classes.get( {by: 'campus', id: Campus.id } ).$promise;
                    });
                    regionContainerPromise = $scope.Campus.$promise;
                    break;
            }
            
            classesPromise.then(classesPromiseCallback = function(Classes){
                
                // Grade list and grade map
                var gradesList = [],
                    grades = {};
                
                for ( var i = 0; i < Classes.length; i++ ) {
                    var clazz = Classes[i];
                    clazz.grade += '年级';
                    if ( grades[clazz.grade] === undefined ) {
                        grades[clazz.grade] = [clazz];
                        gradesList.push(clazz.grade);
                    } else {
                        grades[clazz.grade].push(clazz);
                    }
                }
                gradesList.sort(function(a, b) {
                    return b < a;
                });
                                
                // Initlizate the grade filters
                classFilters._1grade.statuses = [];
                for (var i = 0; i < gradesList.length; i++ ) {
                    classFilters._1grade.statuses.push({ id: i + 1,
                                                         name: gradesList[i], 
                                                         callback: function() {
                        
                            // Current selected grade
                            var grade = [];
                            if (classFilters._1grade.statuses.length !== 0) {
                                grade = grades[classFilters._1grade.statuses[ classFilters._1grade.selected_status - 1 ].name];
                            }
                            // Initlizate clazz filter
                            classFilters._2class.statuses = [];
                            for (var i = 0; i < grade.length; i++ ) {
                                classFilters._2class.statuses.push({ id: i + 1,
                                                                     name: grade[i].name, 
                                                                     callback: function(){
                                        console.log(classFilters._2class.selected_status)
                                        
                                        // Fallback to 0 to trigger the selected_status events                    
                                        classFilters._2class.selected_status = 0;
                                    }
                                });
                            }
                            
                            // Dont forget to select item                           
                            classFilters._2class.selected_status = 1;
                        }
                    });
                }
                
                // Dont forget to select item    
                classFilters._1grade.selected_status = 1;
            });
        
            // Return the promise to get region
            return regionContainerPromise;
        })
    
        // Get the region and class
        .then( function(RegionContainer){
            if ( RegionContainer.regionId === undefined ) return this;
            return $scope.Region = Region.get( {id: RegionContainer.regionId} ).$promise;
        })
    
        // Update the district of filtersGroups 
        .then( function(Region){
            districtFilters._1province.statuses = [ { id: 1, name: Region.parent.parent.name }];
            districtFilters._1province.disabled = true;
            districtFilters._2city.statuses = [ { id: 1, name: Region.parent.name }];
            districtFilters._2city.disabled = true;
            districtFilters._3district.statuses = [ { id: 1, name: Region.name }];
            districtFilters._3district.disabled = true;
        });
}]);