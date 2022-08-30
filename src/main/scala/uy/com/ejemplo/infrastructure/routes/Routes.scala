package uy.com.ejemplo.infrastructure.routes

import akka.http.scaladsl.server.Directives.concat
import akka.http.scaladsl.server.Route
import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors

case class Routes() {
  /* Colocar todas las Rutas en una variable utilizando cors */
  val routes: Route = cors() {
    concat(LibroRoute.routes)
  }
}
