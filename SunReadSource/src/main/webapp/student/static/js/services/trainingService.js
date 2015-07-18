/**
 * Created by by on 2015/7/17.
 */
var trainingService = angular.module('trainingService', ['ngResource', "nourConfig"]);

trainingService.factory('SpeedTesting', ['$http', 'config', function ($http, config) {
    var api = {};
    api.getArticleWithQuestion = function (callback) {
        $http.get(config.HOST + 'article/test/question')
            .success(function (data) {
                callback(data);
            });
    };


    api.submitSpeedTesting = function (data, callback) {
        $http.post(config.HOST + 'exam/speedpaper', data)
            .success(function (data) {
                callback(data)
            })
    };

    return api;
}]);


trainingService.factory('SpeedTraining', ['$http', 'config', function ($http, config) {
    var api = {};
    api.getTrainArticle = function (callback) {
        $http.get(config.HOST + 'article/train')
            .success(function (data) {
                callback(data)
            })
    };

    api.getTrainArticleByLevel = function (level, callback) {
        $http.get(config.HOST + 'article/train/' + level)
            .success(function (data) {
                callback(data);
            })
    };

    api.getArticleByID = function(id,callback){
        $http.get(config.HOST+'article/'+id)
            .success(function(data){
                callback(data);
            })
    };
    return api;
}]);


trainingService.factory('SpeedList', ['$http', 'config', function ($http, config) {
    var api = {};
    api.getSpeedList = function (num, callback) {
        $http.get(config.HOST + 'speedlist/' + num)
            .success(function (data) {
                callback(data)
            })
    };

    api.getSpeedListFromSchool = function (schoolid, num, callback) {
        $http.get(config.HOST + 'speedschoollist/' + schoolid + '/' + num)
            .success(function (data) {
                callback(data)
            })
    };

    api.getPersonalSpeedList = function (userid, num, callback) {
        $http.get(config.HOST + 'speedpersonlist/' + userid + '/' + num)
            .success(function (data) {
                callback(data)
            })
    };
    return api;
}]);