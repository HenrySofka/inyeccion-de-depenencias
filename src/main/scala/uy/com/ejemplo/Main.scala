package uy.com.ejemplo

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import uy.com.ejemplo.infrastructure.routes.Routes

object Main {
  def main(args: Array[String]): Unit = {
    implicit val actorSystem: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "akka-http")

    Http().newServerAt("127.0.0.1", 8080).bind(Routes().routes)
  }
}
