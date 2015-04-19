ctrls.controller("readingCenterBookDetailsController", ['$scope', '$rootScope', '$stateParams', 'para','WeeklyHotSearch','OneBookInShelf'
    ,'BookDetail','NoteView', 'NoteTake', 'Dropzone', 'config', function($rootScope,$scope, $stateParams, para,WeeklyHotSearch,OneBookInShelf,BookDetail, NoteView, NoteTake, Dropzone, config){                                                    
	$scope.name = '书籍详情';

    
    var bookDetail = BookDetail.get({ id: $stateParams.bookId }, function(){
        console.log(bookDetail);
        
        // Initlizate the note entity
        $scope.noteTake = new NoteTake(bookDetail);
            
        // Make an instance of the NoteView
        $scope.noteView = new NoteView();
        $scope.arguments = {by: "books", id: bookDetail.id, sortBy: "commentCount" };

        // Transmit arguments to note search engine
        $scope.noteView.ShowMoreNotes($scope.arguments);

        
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


    })
    
    $scope.bookDetails = bookDetail;
    
            
    $scope.hots=WeeklyHotSearch.get({page:0,size:5,level:0,testType:0,literature:0,category:0
                                        ,grade:0,language:0,resource:0,pointRange:0},function(){
        }); 
        
    
    // Image uploader
    $scope.dropzone = new Dropzone("div#image-uploader", {
        url: "/api/upload/notepic",
        paramName: "myfile", // The name that will be used to transfer the file
        maxFilesize: 0.2, // MB
        previewTemplate: "<div class=\"dz-preview dz-file-preview\">\n  <div class=\"dz-image\"><img data-dz-thumbnail /></div>\n <div class=\"dz-error-message\"><span data-dz-errormessage></span></div> <div class=\"dz-error-mark\">\n    <svg width=\"54px\" height=\"54px\" viewBox=\"0 0 54 54\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns:sketch=\"http://www.bohemiancoding.com/sketch/ns\">\n      <title>Error</title>\n      <defs></defs>\n      <g id=\"Page-1\" stroke=\"none\" stroke-width=\"1\" fill=\"none\" fill-rule=\"evenodd\" sketch:type=\"MSPage\">\n        <g id=\"Check-+-Oval-2\" sketch:type=\"MSLayerGroup\" stroke=\"#747474\" stroke-opacity=\"0.198794158\" fill=\"#FFFFFF\" fill-opacity=\"0.816519475\">\n          <path d=\"M32.6568542,29 L38.3106978,23.3461564 C39.8771021,21.7797521 39.8758057,19.2483887 38.3137085,17.6862915 C36.7547899,16.1273729 34.2176035,16.1255422 32.6538436,17.6893022 L27,23.3431458 L21.3461564,17.6893022 C19.7823965,16.1255422 17.2452101,16.1273729 15.6862915,17.6862915 C14.1241943,19.2483887 14.1228979,21.7797521 15.6893022,23.3461564 L21.3431458,29 L15.6893022,34.6538436 C14.1228979,36.2202479 14.1241943,38.7516113 15.6862915,40.3137085 C17.2452101,41.8726271 19.7823965,41.8744578 21.3461564,40.3106978 L27,34.6568542 L32.6538436,40.3106978 C34.2176035,41.8744578 36.7547899,41.8726271 38.3137085,40.3137085 C39.8758057,38.7516113 39.8771021,36.2202479 38.3106978,34.6538436 L32.6568542,29 Z M27,53 C41.3594035,53 53,41.3594035 53,27 C53,12.6405965 41.3594035,1 27,1 C12.6405965,1 1,12.6405965 1,27 C1,41.3594035 12.6405965,53 27,53 Z\" id=\"Oval-2\" sketch:type=\"MSShapeGroup\"></path>\n        </g>\n      </g>\n    </svg>\n  </div> </div>",
        maxFiles: 1,
        acceptedFiles: "image/*",
        addRemoveLinks: true,
        thumbnailWidth: 100,
        thumbnailHeight: 100,
        dictDefaultMessage: "",
        dictFallbackMessage: "你的浏览器不支持批量上传",
        dictFallbackText: "请使用下面的控件上传",
        dictFileTooBig: "你上传的文件太大({{filesize}}MB). 最大文件大小: {{maxFilesize}}MB.",
        dictInvalidFileType: "你不能上传这种类型的文件",
        dictResponseError: "Server responded with {{statusCode}} code.",
        dictCancelUpload: "Cancel upload",
        dictCancelUploadConfirmation: "Are you sure you want to cancel this upload?",
        dictRemoveFile: "Remove file",
        dictRemoveFileConfirmation: null,
        dictMaxFilesExceeded: "You can not upload any more files.",
        dictRemoveFile: "",
        success:  function(data){
            $scope.noteTake.image = data.xhr.responseText;
        }
    });
    
    // Get the image server
    $scope.imageServer = config.IMAGESERVER;
}]);