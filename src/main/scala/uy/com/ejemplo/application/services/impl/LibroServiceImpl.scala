package uy.com.ejemplo.application.services.impl

import scalaz.Reader
import uy.com.ejemplo.application.services.LibroService
import uy.com.ejemplo.domain.entities.Libro
import uy.com.ejemplo.domain.repositories.LibroRepository
import uy.com.ejemplo.domain.repositories.impl.LibroRepositoryImpl
import uy.com.ejemplo.domain.respuestas._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class LibroServiceImpl(repository: LibroRepository) extends LibroService {

  private def run[A](reader: Reader[LibroRepository, A]): A = {
    reader(repository)
  }

  def obtenerLibroXIsbn(isbn: String): Future[Either[MensajeRespuesta, MensajeRespuestaConEntidad[Libro]]] =
    run(getLibro(isbn).map(futureLibro => futureLibro))
      .flatMap(optLibro =>
        if (optLibro.isDefined)
          Future(Right(MensajeRespuestaConEntidad[Libro](optLibro.get,
            s"libro con isbn: [$isbn] encontrado correctamente", 200)))
        else
          Future(Left(MensajeRespuesta(s"libro con isbn: [$isbn] no se pudo encontrar", 404)))
      )

  def obtenerTodosLibros(): Future[Either[MensajeRespuesta, MensajeRespuestaConEntidad[List[Libro]]]] = {
    run(getAllLibros.map(futureLibros => futureLibros))
      .flatMap(libros =>
        if (libros.nonEmpty)
          Future(Right(MensajeRespuestaConEntidad[List[Libro]](libros,
            s"los libros se encontraron correctamente", 200)))
        else
          Future(Left(MensajeRespuesta(s"no se pudieron encontrar los libros", 404)))
      )

  }

  def crearLibro(libro: Libro): Future[Either[MensajeRespuesta, MensajeRespuesta]] = {
    run(crearLibroDB(libro).map(futureResultado => futureResultado))
      .map(e => e.writeConcernError match {
        case Some(value) =>
          Left(MensajeRespuesta(s"Error al insertar el libro con ID: [${libro._id}], error encontrado" +
            s"[${value.errmsg}]", 500))
        case None =>
          Right(MensajeRespuesta(s"Se creo correctamente", 201))
      })

  }

  def eliminiarLibroPorId(idLibro: String): Future[Either[MensajeRespuesta, MensajeRespuesta]] = {
    run(eliminarLibro(idLibro).map(futureResponse => futureResponse))
      .flatMap(respuesta => {
        if (respuesta)
          Future(Right(MensajeRespuesta(s"se elimino el libro con id $idLibro correctamente", 202)))
        else
          Future(Left(MensajeRespuesta(s"no se encontro el libro con id $idLibro", 404)))
      })

  }

}

object LibroServiceImpl extends LibroServiceImpl(LibroRepositoryImpl)
