package me.ontrait.spraytodo

import akka.actor.Actor
import spray.http.StatusCodes._
import spray.routing.HttpService
import spray.util.SprayActorLogging

import me.ontrait.spraytodo.model.{ Todo, TodoDataStore ⇒ Todos }

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
      } ~ path(LongNumber) { id ⇒
        Todos.find(id) match {
          case Some(todo) ⇒ complete(todo)
          case _          ⇒ complete(NotFound)
        }
      }
    } ~ post {
      path("") {
        entity(as[Todo]) { todo ⇒
          Todos.add(todo)
          complete(Created)
        }
      }
    } ~ put {
      path(LongNumber) { id ⇒
        entity(as[Todo]) { todo ⇒
          try {
            Todos.update(todo)
            complete(OK)
          } catch {
            case e: IllegalArgumentException ⇒ complete(BadRequest)
          }

        }
      }
    } ~ delete {
      path(LongNumber) { id ⇒
        Todos.delete(id) match {
          case Some(todo) ⇒ complete(todo)
          case None       ⇒ complete(BadRequest)
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