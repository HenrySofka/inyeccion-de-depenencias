package uy.com.ejemplo.domain.repositories

import reactivemongo.api.bson.BSONDocument
import reactivemongo.api.commands.WriteResult
import uy.com.ejemplo.domain.entities.Libro
import uy.com.ejemplo.domain.respuestas.MensajeRespuesta

import scala.concurrent.Future

trait LibroRepository {
  def get(isbn: String): Future[Option[Libro]]

  def getAll: Future[List[Libro]]

  def save(libro: Libro): Future[WriteResult]

  def delete(idLibro: String): Future[Boolean]

}

