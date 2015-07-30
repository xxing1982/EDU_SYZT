/**
 * Created by by on 2015/7/30.
 */
ctrls.controller("othersNoteController", ['$scope', '$stateParams', 'NoteView', 'Student', 'config',
    function ($scope, $stateParams, NoteView, Student, config) {

        var args = {id: $stateParams.studentId};

        $scope.student = Student.get({id: args.id}, function () {

            // Get the user id form rootScope
            $scope.arguments = {
                by: "users",
                id: args.id,
                sortBy: "creationTime"
            };

            // Initlizate the noteView entity
            $scope.noteView = new NoteView();
            $scope.noteView.ShowMoreNotes($scope.arguments);

            // Get the image server
            $scope.imageServer = config.IMAGESERVER;
        });

        $scope.showLightBox = function (url) {
            $scope.showLightBox.url = $scope.imageServer + url;
        }
    }]);