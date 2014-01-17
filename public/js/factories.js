angular.module('myApp.factories', []).
factory('ttApiErrorInterceptor', [
        '$q', '$rootScope',
function($q, $rootScope) {
  var service = {
    'response': function(response) {
      //delete $rootScope.errorMessage;
      return response || $q.when(response);
    },
    'responseError': function(rejection) {
      var problem = rejection.data, msg;

      msg = problem.title;
      if (problem.details) {
        msg += " (Details: " + problem.details + ")";
      }

      $rootScope.errorMessage = msg;

      setTimeout(function() {
        $rootScope.$apply(function() {
          $rootScope.errorMessage = null;
        });
      }, 5000);

      return $q.reject(rejection);
    }
  };
  return service;
}]);
