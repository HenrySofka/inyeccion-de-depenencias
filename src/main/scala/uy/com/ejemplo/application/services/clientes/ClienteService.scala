package uy.com.ejemplo.application.services.clientes

import scalaz.Reader
import uy.com.ejemplo.domain.entities.Cliente
import uy.com.ejemplo.domain.repositories.cliente.ClienteRepository

import scala.concurrent.Future

trait ClienteService {
  def getCliente(_id: String): Reader[ClienteRepository, Future[Option[Cliente]]] = Reader(
    (repository: ClienteRepository) => repository.get(_id)
  )

  def getAllClientes: Reader[ClienteRepository, Future[List[Cliente]]] = Reader(
    (repository: ClienteRepository) => repository.getAll
  )

}
