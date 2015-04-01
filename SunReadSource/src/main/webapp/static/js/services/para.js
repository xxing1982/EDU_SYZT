var paraServices = angular.module('paraServices', ['ngResource', "nourConfig"]);

paraServices.factory('para', function() {
 var savedData = {}
 function set(data) {
   savedData = data;
 }
 function get() {
  return savedData;
 }

 return {
  set: set,
  get: get
 }

});