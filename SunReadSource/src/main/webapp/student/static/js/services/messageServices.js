var sendMessageServices = angular.module('sendMessageServices', ['ngResource', "nourConfig"]);

/*
    message object(s)
*/
sendMessageServices.factory('SendMessages', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "from/:sendUserId/to/:recieveUserId/messages",
            {sendUserId:'@sendUserId', recieveUserId:'@recieveUserId'}, {}
        );
}]);


var getMessageServices = angular.module('getMessageServices', ['ngResource', "nourConfig"]);

getMessageServices.factory('GetMessages', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "/:source/:id/messages?page=:page&size=:size&sortBy=id",
            {source:'@source', id:'@id',page:'@_page', size:'@_size'}, {}
        );
}]);


var deleteMessagesServices = angular.module('deleteMessagesServices', ['ngResource', "nourConfig"]);

deleteMessagesServices.factory('DeleteMessages', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "/messages/:id",
            {id:'@id'}
        );
}]);
