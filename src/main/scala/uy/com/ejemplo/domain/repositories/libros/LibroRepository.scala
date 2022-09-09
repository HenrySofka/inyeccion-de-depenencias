package uy.com.ejemplo.domain.repositories.libros

import reactivemongo.api.commands.WriteResult
import uy.com.ejemplo.domain.entities.Libro

import scala.concurrent.Future

trait LibroRepository {
  def get(isbn: String): Future[Option[Libro]]

  def getAll: Future[List[Libro]]

  def save(libro: Libro): Future[WriteResult]

  def delete(idLibro: String): Future[Boolean]

}
