angular.module('sprayTodoApp')
  .controller('TodoCtrl', ['$scope', '$resource', ($scope, $resource) ->
    Todo = $resource(
      '/todo/:id',
      {id: '@id'},
      {
        update: {
          method: 'PUT'
        }
      }
    )

    $scope.task = ''

    $scope.create = (task) ->
      mkTodo({task: task, done: false}).$save(refresh)
      clear()

    $scope.update = (t) ->
      mkTodo(t).$update()

    $scope.delete = (id) ->
      mkTodo({id: id}).$delete(refresh)

    clear = () ->
      $scope.task = ''

    mkTodo = (t) ->
      todo = new Todo
      todo.id = t.id
      todo.task = t.task
      todo.done = t.done
      todo

    refresh = () -> $scope.todos = Todo.query()

    refresh()
  ])
