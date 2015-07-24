var messageServices = angular.module('messageServices', ['ngResource', "nourConfig"]);

/*
    message object(s)
*/


messageServices.factory('SendMessages', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "from/:sendUserId/to/:recieveUserId/messages",
            {sendUserId:'@sendUserId', recieveUserId:'@recieveUserId'}, {}
        );
}]);



messageServices.factory('GetMessages', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "/:source/:id/messages?page=:page&size=:size&sortBy=id",
            {source:'@source', id:'@id',page:'@_page', size:'@_size'}, {}
        );
}]);



messageServices.factory('DeleteMessages', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "/messages/:id",
            {id:'@id'}
        );
}]);
