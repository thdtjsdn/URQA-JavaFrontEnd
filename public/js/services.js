'use strict';

/* Services */
moment.lang('en', {
  week: {
    dow: 1, // week starts with Monday
    doy: 4 // The week that contains Jan 4th is the first week of the year.
  }
});


// Demonstrate how to register services
// In this case it is a simple value service.
angular.module('myApp.services', []).
  service('TimesheetPath', function() {
    function path(uid, m) {
      var uid_segment = (uid === 'mine') ? "" : "/" + uid;
      return uid_segment + "/" + m.format('GGGG-w');
    }
    var service = {
      weekPath: function(uid, year, isoWeek) {
        return path(uid, moment().year(year).isoWeek(isoWeek) );
      },
      thisWeekPath: function(uid) {
        return path(uid, moment() );
      },
      nextWeekPath: function(uid, year, isoWeek) {
        return path(uid, moment().year(year).isoWeek(isoWeek).add('week', 1) );
      },
      prevWeekPath: function(uid, year, isoWeek) {
        return path(uid, moment().year(year).isoWeek(isoWeek).subtract('week', 1));
      }
    };
    return service;
  }).

  service('TimesheetUtil', function() {
    var service = {
      startDateForWeek: function(year, isoWeek) {
        return moment().year(year).isoWeek(isoWeek).startOf('week').toDate();
      },
      endDateForWeek: function(year, isoWeek) {
        return moment().year(year).isoWeek(isoWeek).startOf('week').
          add('days', 6).toDate();
      },
      datesForWeek: function(year, isoWeek) {
        var i,
            day = moment().year(year).isoWeek(isoWeek).startOf('week'),
            dates = [];
        for(i=0; i < 7; i++) {
          dates.push( day.clone().toDate() );
          day = day.add('day', 1);
        }
        return dates;
      }
    };
    return service;
  }).

  service('Timesheet', [
          '$log',
          'TimesheetUtil', '$resource',
          function($log,
                   TimesheetUtil, $resource) {

    var service = {
      forWeek: function(uid, year, isoWeek) {
        var api = $resource('/weekly_timesheets/:uid/:year-:isoWeek', {}, {
          'query':  { method:'GET', isArray:false }
        });
        return api.query({uid: uid, year: year, isoWeek: isoWeek});
      },
      createWeeklyEntry: function(timesheet) {
        var api = $resource(
          '/weekly_timesheets/:uid/:year-:isoWeek/entries', {});
        api.save({uid: timesheet.uid, year: timesheet.year, isoWeek: timesheet.isoWeek}, {},
                 function(value) { timesheet.entries.push(value); });
      },
      removeWeeklyEntry: function(timesheet, entry) {
        var api = $resource(
          '/weekly_timesheets/:uid/:year-:isoWeek/entries/:id', {});
          api.remove(
            {uid: timesheet.uid, year: timesheet.year, isoWeek: timesheet.isoWeek, id: entry.id});
      },
      saveWeeklyEntry: function(timesheet, entry) {
        var api = $resource(
          '/weekly_timesheets/:uid/:year-:isoWeek/entries/:id', {}, {
          'update':  { method:'PUT' }
          });
          api.update(
            {uid: timesheet.uid, year: timesheet.year, isoWeek: timesheet.isoWeek, id: entry.id},
            {projectCode: entry.projectCode});
      },
      saveDailyEntry: function(timesheet, entry, dayEntry) {
        var api = $resource(
          '/weekly_timesheets/:uid/:year-:isoWeek/entries/:weekly_entry_id/daily_entries',
          {uid: timesheet.uid, year: timesheet.year, isoWeek: timesheet.isoWeek,
           weekly_entry_id: entry.id});

        api.save({}, dayEntry, function(value) {
        });
      },
      copyLastTimesheet: function(timesheet) {
        var api = $resource(
          '/weekly_timesheets/:uid/:year-:isoWeek/entries/copy_prev',
          {uid: timesheet.uid, year: timesheet.year, isoWeek:timesheet.isoWeek}, {
            'save':  { method:'POST', isArray:true }
          });
        timesheet.entries = api.save({}, {});
      }
    };
    return service;
  }]).

  service('User', ['$resource', function($resource) {
    var service = {
      all: function() {
        return $resource('/users', {}).query();
      },

      myProfile: function() {
        return $resource('/users/my_profile', {}, {
          get: {method:'GET'}
        }).get();
      }
    };
    return service;
  }]).

  service('Project', ['$resource', function($resource) {
    var api = $resource('/projects/', {});

    var service = {
      all: function() {
        return api.query();
      },

      createTempProject: function(description) {
        var result = api.save({}, {description: description});
        console.log(result);
        return result;
      }
    };
    return service;
  }]).

  value('version', '0.1');


