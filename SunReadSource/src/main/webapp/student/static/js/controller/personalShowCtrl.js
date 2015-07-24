//personalShow.js

ctrls.controller("personalShowController", ['$scope', '$stateParams', 'Student', 'config', 'Class', 'Bookshelf', 'Hotreader', function ($scope, $stateParams, Student, config, Class, Bookshelf, Hotreader) {

    var arguments = {id: $stateParams.studentId};

    $scope.imageServer = config.IMAGESERVER;

    var studentLimit = 200;

    Student.get({id: arguments.id}, function (data) {
        $scope.picture = {};
        $scope.picture.current = data.picture === "" ? "../static/img/myBookshelf/addPhoto.png" : config.IMAGESERVER + data.picture;
        $scope.userInfo = data;

        $scope.completePercentage = parseInt($scope.userInfo.statistic.testCount/$scope.userInfo.statistic.testPasses);

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
        },function(data){
            $scope.hotReaders = data;
            $scope.total = $scope.hotReaders.content.length;
            for(var i=0;i<$scope.total;i++){
                var item = $scope.hotReaders.content[i];
                if(item.id == $scope.userInfo.id){
                    $scope.rank = i+1;
                }
            }
        });



    });

    Bookshelf.get({id: arguments.id}, function (data) {
        $scope.bookshelf = data;
        $scope.bookToShow = $scope.bookshelf.booksInShelf.slice(0, 4);
    });


}]);
