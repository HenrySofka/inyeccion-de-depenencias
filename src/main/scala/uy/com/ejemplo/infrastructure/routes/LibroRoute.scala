package uy.com.ejemplo.infrastructure.routes

import akka.http.scaladsl.server.Directives._
import spray.json.DefaultJsonProtocol._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors
import spray.json.RootJsonFormat
import uy.com.ejemplo.application.services.libros.LibroService
import uy.com.ejemplo.application.services.libros.impl.LibroServiceImpl
import uy.com.ejemplo.domain.dto.LibroDto
import uy.com.ejemplo.domain.entities.Libro
import uy.com.ejemplo.domain.helpers.LibroHelper
import uy.com.ejemplo.domain.respuestas.MensajeRespuestaConEntidad

case class LibroRoute() extends LibroService {

  implicit val libroMarshallerJson: RootJsonFormat[Libro] = jsonFormat3(Libro.apply)
  implicit val libroDtoMarshallerJson: RootJsonFormat[LibroDto] = jsonFormat2(LibroDto.apply)
  implicit val respuestaConLibroMarshallerJson: RootJsonFormat[MensajeRespuestaConEntidad[Libro]] = jsonFormat3(MensajeRespuestaConEntidad.apply _)
  implicit val respuestaConLibrosMarshallerJson: RootJsonFormat[MensajeRespuestaConEntidad[List[Libro]]] = jsonFormat3(MensajeRespuestaConEntidad.apply _)


  private lazy val obtenerLibros: Route =
    path("api-libreria" / "libros") {
      get {
        onSuccess(LibroServiceImpl.obtenerTodosLibros()) {
          case Right(value) => complete(value)
          case Left(value) => complete(value.statusCode, value.mensaje)
        }
      }
    }

  private lazy val obtenerLibroPorIsbn: Route =
    path("api-libreria" / "libros" / "buscar-por-isbn" / Segment) {
      isbn =>
        get {
          onSuccess(LibroServiceImpl.obtenerLibroXIsbn(isbn)) {
            case Right(value) => complete(value)
            case Left(value) => complete(value.statusCode, value.mensaje)
          }
        }
    }

  private lazy val eliminarLibroPorIsbn: Route =
    path("api-libreria" / "libros" / "eliminar-por-isbn" / Segment) {
      isbn =>
        delete {
          onSuccess(LibroServiceImpl.eliminiarLibroPorId(isbn)) {
            case Right(value) => complete(value.statusCode, value.mensaje)
            case Left(value) => complete(value.statusCode, value.mensaje)
          }
        }
    }

  private lazy val crearLibro: Route =
    path("api-libreria" / "libros" / "crear") {
      entity(as[LibroDto]) {
        libroDto =>
          post {
            onSuccess(LibroServiceImpl.crearLibro(LibroHelper.dtoToEntity(libroDto))) {
              case Right(value) => complete(value.statusCode, value.mensaje)
              case Left(value) => complete(value.statusCode, value.mensaje)
            }
          }
      }
    }


  val routes: Route = cors() {
    concat(obtenerLibros, obtenerLibroPorIsbn, crearLibro, eliminarLibroPorIsbn)
  }

}
