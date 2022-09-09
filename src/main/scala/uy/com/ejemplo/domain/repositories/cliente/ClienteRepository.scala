package uy.com.ejemplo.domain.repositories.cliente

import reactivemongo.api.commands.WriteResult
import uy.com.ejemplo.domain.entities.Cliente

import scala.concurrent.Future

trait ClienteRepository {
  def get(_id: String): Future[Option[Cliente]]

  def getAll: Future[List[Cliente]]

  def save(libro: Cliente): Future[WriteResult]

  def delete(idLibro: String): Future[Boolean]
}
