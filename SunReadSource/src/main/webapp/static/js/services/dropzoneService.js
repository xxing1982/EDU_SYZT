/*
    Dropzone object(s)
    
    Load dropzone.js first
*/
angular.module('dropzoneServices', [])
    .factory('Dropzone', function(){
        return Dropzone;
    });
