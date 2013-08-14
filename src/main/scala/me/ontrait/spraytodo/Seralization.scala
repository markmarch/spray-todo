package me.ontrait.spraytodo

import argonaut.{ DecodeJson, EncodeJson, Parse }
import spray.http.ContentTypes._
import spray.httpx.marshalling.Marshaller
import spray.httpx.unmarshalling.Unmarshaller

object Marshalling {
  implicit def jsonMarshaller[T: EncodeJson] =
    Marshaller.delegate[T, String](`application/json`) { implicitly[EncodeJson[T]].encode(_).toString }
}

object Unmarshalling {
  implicit def jsonUnmarshaller[T: DecodeJson] =
    Unmarshaller.delegate[String, T](`application/json`) { string ⇒
      Parse.decodeOption[T](string) match {
        case Some(todo) ⇒ todo
        case _          ⇒ throw new IllegalArgumentException(s"unable to parse content $string")
      }
    }
}