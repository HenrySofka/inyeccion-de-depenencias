package uy.com.ejemplo.domain.repositories.cliente.impl

import reactivemongo.api.Cursor
import reactivemongo.api.bson.BSONDocument
import reactivemongo.api.commands.WriteResult
import uy.com.ejemplo.domain.entities.Cliente
import uy.com.ejemplo.domain.repositories.cliente.ClienteRepository
import uy.com.ejemplo.infrastructure.db.mongo.ConexionBD

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object ClienteRepositoryImpl extends ClienteRepository{
  private val conexionMongoDB: ConexionBD = ConexionBD.conexionMongoDB

  override def get(_id: String): Future[Option[Cliente]] = {
    conexionMongoDB.getColeccion("Clientes").flatMap(collection =>
      collection.find(BSONDocument("isbn" -> _id)).one[Cliente])
  }

  override def getAll: Future[List[Cliente]] = {
    conexionMongoDB.getColeccion("Clientes")
      .flatMap(_.find(BSONDocument()).cursor[Cliente]()
        .collect(err = Cursor.FailOnError[List[Cliente]]()))
  }

  override def save(libro: Cliente): Future[WriteResult] = {
    conexionMongoDB.getColeccion("Clientes").flatMap { col =>
      col.insert.one(libro)
    }
  }

  override def delete(_id: String): Future[Boolean] = {
    conexionMongoDB.getColeccion("Clientes").flatMap(collection =>
      collection.findAndRemove(BSONDocument("_id" -> _id)))
      .map(e => e.value.isDefined)
  }
}
