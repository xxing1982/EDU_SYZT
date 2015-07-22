//personalShow.js

ctrls.controller("personalShowController", ['$scope', '$stateParams', 'Student','config','Class','Bookshelf', function ($scope, $stateParams, Student,config,Class,Bookshelf) {

    var arguments = {id: $stateParams.studentId};

    $scope.imageServer = config.IMAGESERVER;

    Student.get({id: arguments.id}, function (data) {
        $scope.picture = {};
        $scope.picture.current = data.picture === "" ? "../static/img/myBookshelf/addPhoto.png" : config.IMAGESERVER + data.picture;
        $scope.userInfo = data;

        Class.get({id: $scope.userInfo.clazzId}, function (classData) {
            $scope.userInfo.class = classData.name;
            $scope.userInfo.school = classData.campusName;
        });
    });

    Bookshelf.get({id:arguments.id},function(data){
        $scope.bookshelf = data;

        $scope.bookToShow = $scope.bookshelf.booksInShelf.slice(0,4);

        console.log($scope.bookToShow);

    })


}]);
