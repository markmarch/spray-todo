package me.ontrait.spraytodo

import akka.actor.Actor
import spray.http.StatusCodes._
import spray.routing.HttpService
import spray.util.SprayActorLogging

import me.ontrait.spraytodo.model.{Todo, TodoDataStore => Todos}

class SprayTodoServiceActor extends Actor with SprayTodoService {
  def actorRefFactory = context

  def receive = runRoute(route)
}

trait SprayTodoService extends HttpService {
  import Marshalling._
  import Unmarshalling._

  val route = pathPrefix("todo") {
    get {
      path("") {
        complete(Todos.list)
      } ~ path(LongNumber) { id =>
        Todos.find(id) match {
          case Some(todo) => complete(todo)
          case _ => complete(NotFound)
        }
      }
    } ~ put {
      path("") {
        entity(as[Todo]) { todo =>
          complete {
            Todos.add(todo)
          }
        }
      }
    } ~ delete {
      path(LongNumber) { id =>
        Todos.delete(id) match {
          case Some(todo) => complete(todo)
          case None => complete(BadRequest)
        }
      }
    }
  } ~ get {
    path("") {
      getFromResource("web/app/index.html")
    } ~
    getFromResourceDirectory("web/tmp") ~
    getFromResourceDirectory("web/app")
  }
}