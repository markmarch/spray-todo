angular.module('sprayTodoApp', ['ngResource'])
  .config(
    ['$routeProvider', ($routeProvider) ->
      $routeProvider.when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
        })
      .when('/todos', {
        templateUrl: 'views/todos.html',
        controller: 'TodoCtrl'
        })
      .otherwise({ redirectTo: '/'})
      return
    ]
  )
