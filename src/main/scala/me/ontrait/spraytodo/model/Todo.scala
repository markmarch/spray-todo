package me.ontrait.spraytodo.model

import argonaut._
import Argonaut._

case class Todo(id: Option[Long], task: String, done: Boolean = false)
object Todo {
  implicit def TodoCodec: CodecJson[Todo] =
    casecodec3(Todo.apply, Todo.unapply)("id", "task", "done")
}

object TodoDataStore {
  import scala.collection.mutable.ListBuffer

  private[this] val all = {
    val tasks = List("get milk", "read book", "more coding", "learn how to drive")
    val list = tasks.zipWithIndex.map{ case (task, index) => Todo(Some(index.toLong), task, task.length % 2 == 0)}
    ListBuffer.empty[Todo] ++ list
  }

  def list: List[Todo] = all.to[List]

  def add(todo: Todo): Todo = {
    val maxId = if (all.isEmpty) 0L else { all.maxBy(_.id.getOrElse(0L)).id.getOrElse(0L) }
    val added = todo.copy(id = Some(maxId + 1L))
    all += added
    added
  }

  def find(id: Long): Option[Todo] = all.find(_.id == Some(id))

  def setDone(id: Long): Unit = find(id) match {
    case Some(todo) => all -= todo; all += todo.copy(done = true)
    case None => throw new IllegalArgumentException("Not found todo with id $id")
  }

  def delete(id: Long): Option[Todo] = find(id) match {
    case Some(todo) => all -= todo; Some(todo)
    case _ => None
  }
}