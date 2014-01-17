'use strict';

angular.module('myTimesheetApp', [
  'ngRoute',
  'ngResource',
  'myApp.filters',
  'myApp.services',
  'myApp.factories',
  'myApp.directives',
  'myApp.controllers'
]).config(['$routeProvider', '$httpProvider',
  function($routeProvider, $httpProvider) {

  $httpProvider.interceptors.push('ttApiErrorInterceptor');

  $routeProvider.

    when('/', {
      resolve: {
        myProfile: ['User', function(User) { return User.myProfile().$promise; }]
      },
      templateUrl: 'partials/weekly_timesheet.html',
      controller: 'MyTimesheetCtrl'}).

    when('/:year-:isoWeek', {
      resolve: {
        myProfile: ['User', function(User) { return User.myProfile().$promise; }]
      },
      templateUrl: 'partials/weekly_timesheet.html',
      controller: 'MyTimesheetCtrl'}).

    otherwise({redirectTo: '/'});

}]);

angular.module('otherTimesheetApp', [
  'ngRoute',
  'ngResource',
  'myApp.filters',
  'myApp.services',
  'myApp.factories',
  'myApp.directives',
  'myApp.controllers'
]).config(['$routeProvider', '$httpProvider',
  function($routeProvider, $httpProvider) {

  $httpProvider.interceptors.push('ttApiErrorInterceptor');

  $routeProvider.

    when('/', {
      resolve: {
        myProfile: ['User', function(User) { return User.myProfile().$promise; }]
      },
      templateUrl: 'partials/weekly_timesheet.html',
      controller: 'OtherTimesheetCtrl'}).

    when('/:user', {
      resolve: {
        myProfile: ['User', function(User) { return User.myProfile().$promise; }]
      },
      templateUrl: 'partials/weekly_timesheet.html',
      controller: 'OtherTimesheetCtrl'}).

    when('/:user/:year-:isoWeek', {
      resolve: {
        myProfile: ['User', function(User) { return User.myProfile().$promise; }]
      },
      templateUrl: 'partials/weekly_timesheet.html',
      controller: 'OtherTimesheetCtrl'}).

    otherwise({redirectTo: '/'});

}]);

