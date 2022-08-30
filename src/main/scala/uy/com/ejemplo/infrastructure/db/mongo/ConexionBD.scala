package uy.com.ejemplo.infrastructure.db.mongo

import reactivemongo.api.MongoConnection.ParsedURI
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.{AsyncDriver, MongoConnection}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ConexionBD(mongoURL: String, bdNombre: String) {
  val mongoDriver: AsyncDriver = AsyncDriver()

  lazy val parsedURI: Future[ParsedURI] = MongoConnection.fromString(mongoURL)
  lazy val conexion: Future[MongoConnection] = parsedURI.flatMap(uri => mongoDriver.connect(uri))

  def getColeccion(nombreColeccion: String): Future[BSONCollection] = {
    val bd = conexion.flatMap(_.database(bdNombre))
    val coleccion: Future[BSONCollection] = bd.map(_.collection(nombreColeccion))
    coleccion
  }
}

object ConexionBD {

  val conexionMongoDB: ConexionBD = new ConexionBD(
    "mongodb+srv://admin:admin@proyectos.bsqdx.mongodb.net/biblioteca-scala?retryWrites=true&w=majority",
    "biblioteca-scala")
}
