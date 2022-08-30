package uy.com.ejemplo.application.services.impl

import scalaz.{Id, Kleisli}
import uy.com.ejemplo.application.services.LibroService
import uy.com.ejemplo.domain.entities.Libro
import uy.com.ejemplo.domain.repositories.LibroRepository
import uy.com.ejemplo.domain.respuestas._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object LibroServiceImpl extends LibroService {

  def obtenerLibroXIsbn(isbn: String): Kleisli[Id.Id, LibroRepository, Future[Either[MensajeRespuesta, MensajeRespuestaConEntidad[Libro]]]] =
    getLibro(isbn).map(futureLibro => futureLibro
      .flatMap(optLibro =>
        if (optLibro.isDefined)
          Future(Right(MensajeRespuestaConEntidad[Libro](optLibro.get,
            s"libro con isbn: [$isbn] encontrado correctamente", 200)))
        else
          Future(Left(MensajeRespuesta(s"libro con isbn: [$isbn] no se pudo encontrar", 404)))
      )
    )

  def obtenerTodosLibros(): Kleisli[Id.Id, LibroRepository,
    Future[Either[MensajeRespuesta, MensajeRespuestaConEntidad[List[Libro]]]]] = {
    getAllLibros.map(futureLibros =>
      futureLibros
        .flatMap(libros =>
          if (libros.nonEmpty)
            Future(Right(MensajeRespuestaConEntidad[List[Libro]](libros,
              s"los libros se encontraron correctamente", 200)))
          else
            Future(Left(MensajeRespuesta(s"no se pudieron encontrar los libros", 404)))
        )
    )
  }

  def crearLibro(libro: Libro): Kleisli[Id.Id, LibroRepository,
    Future[Either[MensajeRespuesta, MensajeRespuesta]]] = {
    crearLibroDB(libro).map(futureResultado =>
      futureResultado
        .map(e => e.writeConcernError match {
          case Some(value) =>
            Left(MensajeRespuesta(s"Error al insertar el libro con ID: [${libro._id}], error encontrado" +
              s"[${value.errmsg}]", 500))
          case None =>
            Right(MensajeRespuesta(s"Se creo correctamente", 201))
        })
    )
  }

  def eliminiarLibroPorId(idLibro: String): Kleisli[Id.Id, LibroRepository, Future[MensajeRespuesta]] = {
    eliminarLibro(idLibro).map(futureResponse =>
      futureResponse
        .flatMap {
          case Right(value) => Future(value)
          case Left(value) => Future(value)
        }
    )
  }

}
