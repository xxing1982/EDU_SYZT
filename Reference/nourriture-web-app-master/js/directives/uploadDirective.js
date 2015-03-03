/**
 * Created by niels on 11/30/14.
 *
 * Amazon S3 based file-uploader for the various image upload forms throughout Nourriture
 */

var upldModule = angular.module("nourUpload", ["nourConfig", "angularFileUpload"]);

upldModule.directive("nourUpload", ["$http", "config", "FileUploader", function($http, config, FileUploader) {
    return {
        templateUrl: "partials/directives/uploadDirective.html",
        compile: function ($tmplElm, $tmplAttr) {
            // Compiling
            var uploaderId = $tmplAttr["uploader"];
            if(!uploaderId) uploaderId = "uploader";    // Default id, if non specified
            $tmplElm.find("[nv-file-select],[nv-file-drop]").attr("uploader", uploaderId);  // Forward uploader reference to dependency module "angular-file-upload"

            // Linking
            return {
                pre: function ($scope, $linkElm, $linkAttr) {
                    var group = $linkAttr["nourGroup"];
                    var entity = $linkAttr["nourEntity"];
                    var bucket = "https://" + config.S3_BUCKET + ".s3.amazonaws.com/";
                    var key;

                    // Utility function (invoked just before upload, see below) for getting policy token needed to authorize a user to upload a file to our S3 bucket
                    var getPolicyToken = function (callback) {
                        $http.get(config.BE_HOST + "/upload/token/" + group + "/" + entity)
                            .success(function (data, status, headers, config) {
                                callback(data);
                            })
                            .error(function (data, status, headers, config) {
                                callback(null);
                            });
                    };

                    // Instantiate uploader
                    var uploader = new FileUploader({
                        url: bucket,
                        autoUpload: true,
                        formData: [
                            {AWSAccessKeyId: config.AMAZON_ID},
                            {acl: "public-read"}
                        ]
                    });

                    // Restrict maximum file size
                    uploader.filters.push({
                        name: 'maxSize', fn: function (file) {
                            return file.size < 1048576;
                        }
                    });

                    // Restrict supported image types
                    uploader.filters.push({
                        name: 'imageTypes', fn: function (file) {
                            return file.name.match(/^.+\.(png|gif|jpg|jpeg)$/);
                        }
                    });

                    // Error if above restrictions fail
                    uploader.onWhenAddingFileFailed = function (item, filter, options) {
                        uploader.error = filter.name;
                    };

                    // Final preparations for upload after user submitted file and it passed restrictions
                    uploader.onAfterAddingFile = function (item) {
                        uploader.error = null;
                        group = $linkAttr["nourGroup"];
                        entity = $linkAttr["nourEntity"];

                        // Add Content-Type field depending on file submitted by user
                        item.formData.push({"Content-Type": item._file.type});

                        // Add key to store under (NOTE: Policy issued from server will only allow keys starting with the company name. This bit is mostly to add the correct file-extension)
                        var ext = item._file.name.match(/^.+\.(png|gif|jpg|jpeg)$/)[1];
                        key = group + "/" + entity + "." + ext;
                        item.formData.push({"key": key}); // e.g. company/cocacola.png

                        // Inject a policy token retrieval before upload
                        item.upload = function (uploadItem) {
                            return function () {
                                getPolicyToken(function (token) {
                                    if (token) {
                                        item.formData.push({policy: token.s3Policy});
                                        item.formData.push({signature: token.s3Signature});
                                        item.upload = uploadItem;
                                        item.upload();
                                    } else {
                                        uploader.error = "policy";
                                        item.cancel();
                                    }
                                });
                            };
                        }(item.upload);
                    };

                    // Successful upload, invoke callback, if given
                    uploader.onSuccessItem = function (item, response, status, headers) {
                        var url = bucket + key;
                        var successFunc = $scope.$eval($linkAttr["nourSuccess"]);
                        if (successFunc) successFunc(url);
                    };

                    // Failed upload, show error message
                    uploader.onErrorItem = function (item, response, status, headers) {
                        if (status == 0) {
                            uploader.error = "connection";
                        } else {
                            uploader.error = "unexpected";
                        }
                    };

                    // Store configured uploader class in controller scope (still accessible for any further configuration)
                    $scope[uploaderId] = uploader;
                }
            }
        }
    }
}]);