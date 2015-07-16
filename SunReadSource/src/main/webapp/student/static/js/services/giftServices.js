var getGiftsServices = angular.module('getGiftsServices', ['ngResource', "nourConfig"]);

/*
    getGifts object(s)
*/
getGiftsServices.factory('GetGifts', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "/schools/:schoolId/gifts?page=:page&size=:size",
            {schoolId:'@_schoolId',page:'@_page', size:'@_size'}, {}
        );
}]);


var getGiftsExNumServices = angular.module('getGiftsExNumServices', ['ngResource', "nourConfig"]);

/*
    getGifts object(s)
*/
getGiftsExNumServices.factory('GetGiftsExNum', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "students/:id/exchanges?page=:page&size=:size&sortBy=creationTime&direction=desc",
            {id:'@_id', page:'@_page', size:'@_size'}, {}
        );
}]);


var exchangeGiftsServices = angular.module('exchangeGiftsServices', ['ngResource', "nourConfig"]);
/*
    getGifts object(s)
*/
exchangeGiftsServices.factory('ExchangeGifts', ['$resource', 'config',
	function($resource, config){
		return $resource(config.HOST + "students/:studentId/gifts/:giftId?count=:count",
            {studentId:'@_studentId', giftId:'@_giftId',count:'@_count'},
						{update:{
							method:'PUT'
						}
        });
}]);

// var exchangeGiftsServices = angular.module('exchangeGiftsServices', ['ngResource', "nourConfig"]);
// /*
//     getGifts object(s)
// */
// exchangeGiftsServices.factory('ExchangeGifts', ['$resource', 'config',
// 	function($resource, config){
// 		return $resource(config.HOST + "students/1/gifts/3?count=10",
//             {},
// 						{update:{
// 							method:'PUT'
// 						}
//         });
// }]);
