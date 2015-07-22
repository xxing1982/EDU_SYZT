//myRewardDisapatchTaskCtrl.js
ctrls.controller("myRewardDispatchRewardController", ['$scope', '$rootScope', 'Teacher', 'Class', 'CoinHistory', 'Loadable', 'Hotreader',
    function ($scope, $rootScope, Teacher, Class, CoinHistory, Loadable, Hotreader) {

        // Initlizate the dropdown statues
        $scope.campusStatuses = [];
        $scope.campusSelected_status = 0;

        $scope.gradeStatuses = [];
        $scope.gradeSelected_status = 0;

        $scope.classStatuses = [];
        $scope.classSelected_status = 0;


        // Get the teacher entity by rootScope id
        $scope.teacher = Teacher.get({id: $rootScope.id}, function () {

            var gradeName = ['一', '二', '三', '四', '五', '六'];
            var index = 0;

            // Get the class by teacher classId
            $scope.class = Class.get({id: $scope.teacher.currentClassId}, function () {
                $scope.campusStatuses.push({id: index, name: $scope.class.campusName});
                $scope.gradeStatuses.push({id: index, name: gradeName[$scope.class.grade - 1] + '年级'});
                $scope.classStatuses.push({id: index, name: $scope.class.name});
            });


            // Get the coinhistory pagable by teacherid
            // The best practice of loadable
            // Create a pageable entity of actions
            $scope.hotreaderLoadable = new Loadable();

            // Set the parameters of loadable
            $scope.hotreaderLoadable.size = 5;
            $scope.hotreaderLoadable.page = 0;

            // Set the $resource arguments like {by: "books"}
            $scope.hotreaderLoadable.arguments = {by: 'clazz', id: $scope.teacher.currentClassId, sortBy: 'coin'};


            // Build the loadable object
            $scope.hotreaderLoadable.build(Hotreader);

            // Show the first page and append editable to every entity
            $scope.hotreaderLoadable.get();

            // The index of the entities
            //var index = 0;

            // Make the checklist model
            $scope.hotreaderLoadable.selected = [];

            // Publish the selected entities
            //$scope.hotreaderLoadable.publish = function(){
            //    var saved = 0;
            //    var targetSaved = this.selected.length;
            //    for(var i = 0; i < this.selected.length; i++ ){
            //        if (this.selected[i].num >= 0){
            //            CoinHistory.save({ by: "students",
            //                               id: this.selected[i].id },
            //                             { "coinType": "IN",
            //                               "coinFrom": "FROM_TEACHER",
            //                               "num": this.selected[i].num,
            //                               "reason": this.selected[i].reason },
            //                             function(){
            //                                if ( saved + 1 < targetSaved){
            //                                    saved ++;
            //                                } else {
            //                                    $rootScope.modal = {title: "发布奖励", content: "奖励发布成功！"};
            //                                    $('#alert-modal').modal();
            //                                    this.selected = [];
            //                                }
            //                             }
            //
            //            );
            //            this.selected[i].statistic.coin += this.selected[i].num;
            //        } else {
            //            saved ++;
            //        }
            //    }
            //
            //};

            // The select all method
            //$scope.hotreaderLoadable.selectAll = function(){
            //    if (!this.selectedAllValue) {
            //        angular.extend(this.selected, this.entities.content);
            //    } else {
            //        this.selected = [];
            //    }
            //    this.selectedAllValue = !this.selectedAllValue;
            //};

            $scope.publishRewordToPerson = function (hotreader) {
                if (hotreader.num > 0) {
                    CoinHistory.save({
                            by: "students",
                            id: hotreader.id
                        },
                        {
                            "coinType": "IN",
                            "coinFrom": "FROM_TEACHER",
                            "num": hotreader.num,
                            "reason": hotreader.reason
                        },
                        function () {
                            $rootScope.modal = {title: "发布奖励", content: "奖励发布成功！"};
                            $('#alert-modal').modal();
                            hotreader = [];

                        }
                    );
                    hotreader.statistic.coin += hotreader.num;
                }
                else {
                    $rootScope.modal = {title: "发布奖励失败", content: "奖励数值必须大于0！"};
                    $('#alert-modal').modal();
                }
            };

        });
    }]);
