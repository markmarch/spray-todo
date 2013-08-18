angular.module('sprayTodoApp')
  .controller('MainCtrl', ['$scope', ($scope) ->
    $scope.awesomeThings = [
      'spray',
      'akka',
      'scala',
      'angularjs'
    ]
    return
  ])
