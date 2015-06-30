angular.module('nourConfig', [])
    .constant('config', {
        HOST: "/api/",
        IMAGESERVER: "http://images.pdreading.com", 
        NOTEPIC: "/api/upload/notepic",
        USERICON: "/api/upload/usericon",
        BOOKPIC: "/api/upload/bookpic"
    }
);
