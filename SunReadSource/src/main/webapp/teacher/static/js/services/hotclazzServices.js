
var hotclazzServices = angular.module("hotclazzServices",["ngResource","nourConfig"]);

hotclazzServices.factory("Hotclazz",["$resource","config",
       function($resource,config){
		
			return $resource(config.HOST + ":by/:id/hotclazzs",
					{by:"@by",id:"@id"}
			)
	
}])