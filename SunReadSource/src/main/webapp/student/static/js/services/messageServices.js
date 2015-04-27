var sendMessageServices = angular.module('sendMessageServices', ['ngResource', "nourConfig"]);

/*
    message object(s)
*/
sendMessageServices.factory('SendMessages', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "/from/:sendUserId/to/:receiveUserId/messages",
            {sendUserId:'@sendUserId', receiveUserId:'@receiveUserId'}, {}
        );
}]);


var getMessageServices = angular.module('getMessageServices', ['ngResource', "nourConfig"]);

getMessageServices.factory('GetMessages', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "/:source/:id/messages?page=:page&size=:size&sortBy=id",
            {source:'@source', id:'@id',page:'@_page', size:'@_size'}, {}
        );
}]);
