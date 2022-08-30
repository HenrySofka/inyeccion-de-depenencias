package uy.com.ejemplo.domain.entities

import reactivemongo.api.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}

case class Libro(_id: String,
                 isbn: String,
                 nombre: String)

object Libro {
  implicit def libroWriter: BSONDocumentWriter[Libro] = Macros.writer[Libro] //Escritura

  implicit def libroReader: BSONDocumentReader[Libro] = Macros.reader[Libro] //Lectura
}