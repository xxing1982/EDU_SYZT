ctrls.controller("teachingCenterAddToShelfController",['$scope', '$rootScope','$stateParams','Teacher','Class','GetBookshelvesByClass','AddRecommends','AddRecommendsToClazz','BookDetail',
	function($scope, $rootScope,$stateParams,Teacher,Class,GetBookshelvesByClass,AddRecommends,AddRecommendsToClazz,BookDetail){

		// $scope.bookAttribute_status

		$scope.selectBookAttribute = function(){
			console.log (	$scope.isMandatoryForClazz);
			// console.log (	$scope.isMandatoryForClazz
			// console.log($scope.isMandatory);

		}

		// Initlizate the dropdown statues
    $scope.campusStatuses = [];
    $scope.campusSelected_status = 0;

    $scope.gradeStatuses = [];
    $scope.gradeSelected_status = 0;

    $scope.classStatuses = [];
    $scope.classSelected_status = 0;

		$scope.isMandatoryForClazz = true;

		$scope.isMandatory = [];


		$scope.book = BookDetail.get({id:$stateParams.bookId});

    // Get the teacher entity by rootScope id
    $scope.teacher = Teacher.get({ id: $rootScope.id }, function(){
        var gradeName = ['一', '二', '三', '四', '五', '六'];
        var index = 0;

        // Get the class by teacher classId
        $scope.class = Class.get({ id: $scope.teacher.currentClassId }, function(){
            // $scope.campusStatuses.push({ id: index, name: $scope.class.campusName});
            // $scope.gradeStatuses.push({ id: index, name: gradeName[$scope.class.grade - 1] + '年级'});
            // $scope.classStatuses.push({ id: index, name: $scope.class.name});
        		console.log($scope.class);
						//Add books for a whole class
						$scope.recommendToClazz = function(para){
							AddRecommendsToClazz.save({teacherId:$rootScope.id,clazzId:$scope.class.id},
																		{"bookId":$stateParams.bookId,"bookAttribute":$scope.isMandatoryForClazz,'description':para.description}
																		,function(){
																							$rootScope.modal = {title: "添加图书状态", content: "添加成功"};
																							$('#alert-modal').modal();
																							console.log();
																		}
																		,function(error){
																			$rootScope.modal = {title: "添加图书状态", content:"添加失败"};
																			$('#alert-modal').modal();
																			console.log(error);
																		}
																		);
						}
				});


        // Get the hotreaers pagable by teacher classId
        // The best practice of loadable
        // Create a pageable entity of actions
        $scope.bookshelfLoadable = new Loadable();

        // Set the parameters of loadable
        $scope.bookshelfLoadable.size = 5;
        $scope.bookshelfLoadable.page = 0;

        // Set the $resource arguments like {by: "books"}
        $scope.bookshelfLoadable.arguments = {classId: $scope.teacher.currentClassId};

        // Build the loadable object
        $scope.bookshelfLoadable.build(GetBookshelvesByClass);

        // Show the first page and append editable to every entity
        $scope.bookshelfLoadable.get();

				// $scope.hotreader = {
				// 	isMandatory:"true"
				// }


				for(var i=0;i<$scope.bookshelfLoadable.entities.content.length;i++){
					// $scope.bookshelfLoadable.entities.content[i].isMandatory = true;
					$scope.isMandatory[i] = true;
				}

				console.log($scope.bookshelfLoadable);

        // Make the checklist model
        // $scope.bookshelfLoadable.selected = [];

        // Publish the selected entities
        $scope.bookshelfLoadable.publish = function(para){
							AddRecommends.save({teacherId:$rootScope.id,studentId:para.id},
																	{"bookId":$stateParams.bookId,"bookAttribute":para.isMandatory,'description':para.description}
																	,function(date){
																			$rootScope.modal = {title: "添加图书状态"+date.recommendState, content: date.recommendStateStr};
																			$('#alert-modal').modal();
																			console.log(date);
																	});
        };

        // The select all method
        $scope.bookshelfLoadable.selectAll = function(){
            if (!this.selectedAllValue) {
                this.selected = angular.copy(this.entities.content);
            } else {
                this.selected = [];
            }
            this.selectedAllValue = !this.selectedAllValue;
        };
    });

		$scope.selectLength = function(){
			console.log($scope.bookshelfLoadable.selected.length);
			console.log($scope.bookshelfLoadable.selected);
			if($scope.bookshelfLoadable.selected.length>0)
				$scope.bookshelfLoadable.selected[$scope.bookshelfLoadable.selected.length-1].isMandatory =true;
		}

  }]);
