ctrls.controller("readingCenterBookDetailsController", ['$scope', '$rootScope', '$stateParams', 'para','WeeklyHotSearch','OneBookInShelf'
    ,'BookDetail' , 'Student', 'NoteView', 'NoteTake', 'Dropzone', 'config', function($rootScope,$scope, $stateParams, para,WeeklyHotSearch,OneBookInShelf,BookDetail, Student, NoteView, NoteTake, Dropzone, config){                                                    
	$scope.name = '书籍详情';

    
    var bookDetail = BookDetail.get({ id: $stateParams.bookId }, function(){
        console.log(bookDetail);
        
        $scope.student = Student.get({id: $rootScope.id}, function(){
            // Initlizate the note entity
            $scope.noteTake = new NoteTake(bookDetail);

            // Make an instance of the NoteView
            $scope.noteView = new NoteView();
            $scope.arguments = {by: "books", id: bookDetail.id, sortBy: "commentCount" };

            // Transmit arguments to note search engine
            $scope.noteView.ShowMoreNotes($scope.arguments);
        });
        
        $scope.wordTest = function(){
            $rootScope.exam.id = 1;
            $rootScope.exam.returnURL = "/protype/index.html#/readingCenter/bookDetails/" + bookDetail.id + "/";
            $rootScope.exam.bookId = bookDetail.id;
            $rootScope.exam.bookName = bookDetail.name;
            $rootScope.exam.typeName = "我的书架 > 词汇训练";
        }

        $scope.thinkTest = function(){            
            $rootScope.exam.id = 2;
            $rootScope.exam.returnURL = "/protype/index.html#/readingCenter/bookDetails/" + bookDetail.id + "/";
            $rootScope.exam.bookId = bookDetail.id;
            $rootScope.exam.bookName = bookDetail.name;
            $rootScope.exam.typeName = "我的书架 > 思维训练";
        }
        
        /* Goto or open the action defined $stateParams.action
           example: 
                /bookDetails/1/takenote
                /bookDetails/1/intro
                /bookDetails/1/catalog
                /bookDetails/1/review
                /bookDetails/1/note
                /bookDetails/1/recommand
                /bookDetails/1/multimedia
        */
        $(function() {
            switch($stateParams.action){
                case "takenote":
                    $('#takenote').click();
                    break;
                default:
                    if ($stateParams.action !== undefined){
                        $('ul.nav a[href="#' + $stateParams.action + '"]').tab('show');
                    }
           }
        });
        

        
        var thisBookinshelf = OneBookInShelf.get({id:$rootScope.id,bookId:$stateParams.bookId},function(){
            console.log(thisBookinshelf);
            var verify;
            if(thisBookinshelf.readState){
                verify = "已认证";
            }
            else {
                verify = "未认证";
            }
            $scope.verify = verify;
        });


        // Image uploader
        $scope.dropzone = Dropzone(config.NOTEPIC, function(url){
            $scope.noteTake.image = url;
        });

        // Get the image server
        $scope.imageServer = config.IMAGESERVER;
    })
    
    $scope.bookDetails = bookDetail;
    
            
    $scope.hots=WeeklyHotSearch.get({page:0,size:5,level:0,testType:0,literature:0,category:0
                                        ,grade:0,language:0,resource:0,pointRange:0},function(){
        }); 
    $scope.imageServer = config.IMAGESERVER;
}]);