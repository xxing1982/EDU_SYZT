/*
    Dropzone object(s)
    
    Load dropzone.js first
*/
angular.module('dropzoneServices', [])
    .factory('Dropzone', function(){
        
        return function(url, callback, previewTemplate){
            var myPreviewTemplate = previewTemplate === undefined ? "<div class=\"dz-preview dz-file-preview\">\n  <div class=\"dz-image\"><img style=\"width:100%\"data-dz-thumbnail /></div>\n <div class=\"dz-progress\"><span class=\"dz-upload\" data-dz-uploadprogress></span></div>\n <div class=\"dz-error-message\"><span data-dz-errormessage></span></div> <div class=\"dz-error-mark\">\n    <svg width=\"54px\" height=\"54px\" viewBox=\"0 0 54 54\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns:sketch=\"http://www.bohemiancoding.com/sketch/ns\">\n      <title>Error</title>\n      <defs></defs>\n      <g id=\"Page-1\" stroke=\"none\" stroke-width=\"1\" fill=\"none\" fill-rule=\"evenodd\" sketch:type=\"MSPage\">\n        <g id=\"Check-+-Oval-2\" sketch:type=\"MSLayerGroup\" stroke=\"#747474\" stroke-opacity=\"0.198794158\" fill=\"#FFFFFF\" fill-opacity=\"0.816519475\">\n          <path d=\"M32.6568542,29 L38.3106978,23.3461564 C39.8771021,21.7797521 39.8758057,19.2483887 38.3137085,17.6862915 C36.7547899,16.1273729 34.2176035,16.1255422 32.6538436,17.6893022 L27,23.3431458 L21.3461564,17.6893022 C19.7823965,16.1255422 17.2452101,16.1273729 15.6862915,17.6862915 C14.1241943,19.2483887 14.1228979,21.7797521 15.6893022,23.3461564 L21.3431458,29 L15.6893022,34.6538436 C14.1228979,36.2202479 14.1241943,38.7516113 15.6862915,40.3137085 C17.2452101,41.8726271 19.7823965,41.8744578 21.3461564,40.3106978 L27,34.6568542 L32.6538436,40.3106978 C34.2176035,41.8744578 36.7547899,41.8726271 38.3137085,40.3137085 C39.8758057,38.7516113 39.8771021,36.2202479 38.3106978,34.6538436 L32.6568542,29 Z M27,53 C41.3594035,53 53,41.3594035 53,27 C53,12.6405965 41.3594035,1 27,1 C12.6405965,1 1,12.6405965 1,27 C1,41.3594035 12.6405965,53 27,53 Z\" id=\"Oval-2\" sketch:type=\"MSShapeGroup\"></path>\n        </g>\n      </g>\n    </svg>\n  </div> </div>" : previewTemplate ;
            
            // Image uploader
            return new Dropzone("div#image-uploader", {
                url: url,
                paramName: "myfile", // The name that will be used to transfer the file
                maxFilesize: 0.2, // MB
                previewTemplate: myPreviewTemplate,  
                maxFiles: 1,
                acceptedFiles: "image/*",
                addRemoveLinks: true,
                thumbnailWidth: 200,
                thumbnailHeight: 200,
                dictDefaultMessage: "",
                dictFallbackMessage: "你的浏览器不支持批量上传",
                dictFallbackText: "请使用下面的控件上传",
                dictFileTooBig: "你上传的文件太大({{filesize}}MB). 最大文件大小: {{maxFilesize}}MB.",
                dictInvalidFileType: "你不能上传这种类型的文件",
                dictResponseError: "Server responded with {{statusCode}} code.",
                dictCancelUpload: "",
                dictCancelUploadConfirmation: "Are you sure you want to cancel this upload?",
                dictRemoveFile: "",
                dictRemoveFileConfirmation: null,
                dictMaxFilesExceeded: "You can not upload any more files.",
                dictRemoveFile: "",
                success:  function(data){
                    if (callback !== undefined){
                        callback(data.xhr.responseText);
                    }
                }
            });
        };
    });
