package uy.com.ejemplo.infrastructure.routes

import akka.http.scaladsl.server.Directives._
import spray.json.DefaultJsonProtocol._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors
import scalaz.Reader
import spray.json.RootJsonFormat
import uy.com.ejemplo.application.services.LibroService
import uy.com.ejemplo.application.services.impl.LibroServiceImpl
import uy.com.ejemplo.domain.dto.LibroDto
import uy.com.ejemplo.domain.entities.Libro
import uy.com.ejemplo.domain.helpers.LibroHelper
import uy.com.ejemplo.domain.repositories.LibroRepository
import uy.com.ejemplo.domain.repositories.impl._
import uy.com.ejemplo.domain.respuestas.MensajeRespuestaConEntidad

case class LibroRoute(repository: LibroRepository) extends LibroService {

  implicit val libroMarshallerJson: RootJsonFormat[Libro] = jsonFormat3(Libro.apply)
  implicit val libroDtoMarshallerJson: RootJsonFormat[LibroDto] = jsonFormat2(LibroDto.apply)
  implicit val respuestaConLibroMarshallerJson: RootJsonFormat[MensajeRespuestaConEntidad[Libro]] = jsonFormat3(MensajeRespuestaConEntidad.apply _)
  implicit val respuestaConLibrosMarshallerJson: RootJsonFormat[MensajeRespuestaConEntidad[List[Libro]]] = jsonFormat3(MensajeRespuestaConEntidad.apply _)

  private def run[A](reader: Reader[LibroRepository, A]): A = {
    reader(repository)
  }

  private lazy val obtenerLibros: Route =
    path("api-libreria" / "libros") {
      get {
        onSuccess(run(LibroServiceImpl.obtenerTodosLibros())) {
          case Right(value) => complete(value)
          case Left(value) => complete(value.statusCode, value.mensaje)
        }
      }
    }

  private lazy val obtenerLibroPorIsbn: Route =
    path("api-libreria" / "libros" / "buscar-por-isbn" / Segment) {
      isbn =>
        get {
          onSuccess(run(LibroServiceImpl.obtenerLibroXIsbn(isbn))) {
            case Right(value) => complete(value)
            case Left(value) => complete(value.statusCode, value.mensaje)
          }
        }
    }

  private lazy val eliminarLibroPorIsbn: Route =
    path("api-libreria" / "libros" / "eliminar-por-isbn" / Segment) {
      isbn =>
        delete {
          onSuccess(run(LibroServiceImpl.eliminarLibro(isbn))) {
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
            onSuccess(run(LibroServiceImpl.crearLibro(LibroHelper.dtoToEntity(libroDto)))) {
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

object LibroRoute extends LibroRoute(LibroRepositoryImpl) {
}
