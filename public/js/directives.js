'use strict';

/* Directives */


angular.module('myApp.directives', []).
  directive('dailyEntry', ['$document', function($document) {
    function isArrayKeys(keyCode){
      return (keyCode >=37 && keyCode <= 40) ? true : false;
    }

    return {
      restrict: 'A',
      link: function(scope, element, attrs) {

        element.numericInput({allowFloat: true});

        element.bind('keyup', function(event) {
          if (isArrayKeys(event.which)) {
            this.select();
          }
        });
        element.bind('focus, click', function(event) {
          this.select();
        });
        element.bind('keydown', function(event) {
          var x = { curr: scope.dayEntryIndex,
                    min: 0,
                    max: scope.entry.dayEntries.length - 1 },
              y = { curr: scope.entryIndex,
                    min: 0,
                    max: scope.timesheet.entries.length - 1 };

          var key = function (keyCode) {
            switch(keyCode) {
              case 37: return 'left';
              case 38: return 'up';
              case 39: return 'right';
              case 40: return 'down';
              default: return 'ignore';
            }
          };

          var xyChanges = {
            left: function() {
              if (x.curr > x.min) { return {x: -1, y: 0}; }
            },
            up: function() {
              if (y.curr > y.min) { return {x: 0, y: -1 }; }
            },
            right: function() {
              if (x.curr < x.max) { return {x: 1, y: 0}; }
            },
            down: function() {
              if (y.curr < y.max) { return {x: 0, y: 1 }; }
            },
            ignore: function() {
              return null;
            }
          };
          var add = xyChanges[key(event.which)]();
          if (add) {
            $document.find(
              ['.entry', x.curr+add.x, y.curr+add.y].join('-')
            ).select();
          }
        });
      }
    };
  }]).
  directive('appVersion', ['version', function(version) {
    return function(scope, elm, attrs) {
      elm.text(version);
    };
  }]);
