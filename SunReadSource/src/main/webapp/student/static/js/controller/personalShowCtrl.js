//personalShow.js

ctrls.controller("personalShowController", ['$scope', '$stateParams', 'Student', 'config', 'Class', 'Bookshelf', 'Hotreader', 'Note', 'PassExam','Fish',
    function ($scope, $stateParams, Student, config, Class, Bookshelf, Hotreader, Note, PassExam,Fish) {

        var arguments = {id: $stateParams.studentId};

        $scope.imageServer = config.IMAGESERVER;

        var studentLimit = 200;

        Student.get({id: arguments.id}, function (data) {
            $scope.picture = {};
            $scope.picture.current = data.picture === "" ? "../static/img/myBookshelf/addPhoto.png" : config.IMAGESERVER + data.picture;
            $scope.userInfo = data;

            $scope.completePercentage = parseInt($scope.userInfo.statistic.testCount / $scope.userInfo.statistic.testPasses);

            Class.get({id: $scope.userInfo.clazzId}, function (classData) {
                $scope.userInfo.class = classData.name;
                $scope.userInfo.school = classData.campusName;
            });

            Hotreader.get({
                by: 'campus',
                id: $scope.userInfo.campusId,
                page: 0,
                size: studentLimit,
                sortBy: "point"
            }, function (data) {
                $scope.hotReaders = data;
                $scope.total = $scope.hotReaders.content.length;
                for (var i = 0; i < $scope.total; i++) {
                    var item = $scope.hotReaders.content[i];
                    if (item.id == $scope.userInfo.id) {
                        $scope.rank = i + 1;
                    }
                }
            });


        });

        //testing


        Bookshelf.get({id: arguments.id}, function (data) {
            $scope.bookshelf = data;
            $scope.bookToShow = $scope.bookshelf.booksInShelf.slice(0, 4);
        });


        Note.get({by: "users", id: arguments.id, page: 0, size: 4, direction: "DESC", sortBy: "creationTime"},
            function (data) {
                $scope.notes = data.content;
                for (var i = 0; i < data.content.length; i++) {
                    $scope.notes[i].bookPictureUrl = $scope.notes[i].bookPictureUrl === "" ? "../static/img/book.jpg" : config.IMAGESERVER + $scope.notes[i].bookPictureUrl;
                }

            });

        Fish.getMyFish(arguments.id, function (data) {
            $scope.selectedFish = data;
        });

        PassExam.get(arguments.id, function (data) {
            if (data.examDTOs.length > 4) {
                data.examDTOs = data.examDTOs.slice(0, 4);
            };
            //examDTOs[i].pictureUrl
            for (var i = 0; i < data.examDTOs.length; i++) {
                data.examDTOs[i].pictureUrl = data.examDTOs[i].pictureUrl === "" ? "../static/img/book.jpg" : config.IMAGESERVER + data.examDTOs[i].pictureUrl;
            }
            $scope.exam = data;
        });





    }]);
