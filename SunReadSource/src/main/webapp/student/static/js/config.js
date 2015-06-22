angular.module('nourConfig', [])
    .constant('config', {
        HOST: "/api/",
        IMAGESERVER: "http://images.sunreading.cn", 
        NOTEPIC: "/api/upload/notepic", 
        USERICON: "/api/upload/usericon", 
        BOOKPIC: "/api/upload/bookpic",
        STARTDATA: "2015"
    }
);