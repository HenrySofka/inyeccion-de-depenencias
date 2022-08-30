package uy.com.ejemplo.application.services

import reactivemongo.api.bson.BSONDocument
import reactivemongo.api.commands.WriteResult
import scalaz.Reader
import uy.com.ejemplo.domain.entities.Libro
import uy.com.ejemplo.domain.repositories.LibroRepository
import uy.com.ejemplo.domain.respuestas.MensajeRespuesta

import scala.concurrent.Future

trait LibroService {

  def getLibro(isbn: String): Reader[LibroRepository, Future[Option[Libro]]] = Reader(
    (repository: LibroRepository) => repository.get(isbn)
  )

  def getAllLibros: Reader[LibroRepository, Future[List[Libro]]] = Reader(
    (repository: LibroRepository) => repository.getAll
  )

  def crearLibroDB(libro: Libro): Reader[LibroRepository, Future[WriteResult]] = Reader(
    (repository: LibroRepository) => repository.save(libro)
  )

  def eliminarLibro(idLibro: String): Reader[LibroRepository, Future[Boolean]] = Reader(
    (repository: LibroRepository) => repository.delete(idLibro)
  )

}
