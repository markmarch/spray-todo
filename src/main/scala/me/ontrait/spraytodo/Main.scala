package me.ontrait.spraytodo

import akka.actor.{ ActorSystem, Props }
import akka.io.IO
import spray.can.Http

object Main {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("spray-todo")

    val service = system.actorOf(Props[SprayTodoServiceActor], "todo-service")

    IO(Http) ! Http.Bind(service, "localhost", port = 8080)
  }
}