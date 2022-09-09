package uy.com.ejemplo.application.services.libros.impl

import scalaz.{Id, Kleisli}
import uy.com.ejemplo.application.services.libros.LibroService
import uy.com.ejemplo.application.services.libros.impl.LibroServiceImpl.crearLibroDB
import uy.com.ejemplo.domain.entities.Libro
import uy.com.ejemplo.domain.repositories.libros.LibroRepository
import uy.com.ejemplo.domain.respuestas.MensajeRespuesta

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object LibroMayusculaServiceImpl extends LibroService {

  def crearLibro(libro: Libro): Kleisli[Id.Id, LibroRepository, Future[Either[MensajeRespuesta, MensajeRespuesta]]] = {
    crearLibroDB(libro.copy(nombre = libro.nombre.toUpperCase())).map(futureResultado =>
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

}
