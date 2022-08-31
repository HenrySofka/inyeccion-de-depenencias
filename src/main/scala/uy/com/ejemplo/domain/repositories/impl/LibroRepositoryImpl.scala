package uy.com.ejemplo.domain.repositories.impl

import reactivemongo.api.Cursor
import reactivemongo.api.bson.BSONDocument
import reactivemongo.api.commands.WriteResult
import uy.com.ejemplo.domain.entities.Libro
import uy.com.ejemplo.domain.repositories.LibroRepository
import uy.com.ejemplo.infrastructure.db.mongo.ConexionBD

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object LibroRepositoryImpl extends LibroRepository {
  private val conexionMongoDB: ConexionBD = ConexionBD.conexionMongoDB

  override def get(isbn: String): Future[Option[Libro]] = {
    conexionMongoDB.getColeccion("Libros").flatMap(collection =>
      collection.find(BSONDocument("isbn" -> isbn)).one[Libro])
  }

  override def getAll: Future[List[Libro]] = {
    conexionMongoDB.getColeccion("Libros")
      .flatMap(_.find(BSONDocument()).cursor[Libro]()
        .collect(err = Cursor.FailOnError[List[Libro]]()))
  }

  override def save(libro: Libro): Future[WriteResult] = {
    conexionMongoDB.getColeccion("Libros").flatMap { col =>
      col.insert.one(libro)
    }
  }

  override def delete(idLibro: String): Future[Boolean] = {
    conexionMongoDB.getColeccion("Libros").flatMap(collection =>
      collection.findAndRemove(BSONDocument("_id" -> idLibro)))
      .map(e => e.value.isDefined)
  }
}












