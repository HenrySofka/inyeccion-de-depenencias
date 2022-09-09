package uy.com.ejemplo.domain.entities

import reactivemongo.api.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}

import java.time.LocalDate

case class Cliente(_id: String,
                   nombre: String,
                   apellido: String,
                   telefono: String,
                   cdedula: String,
                   librosAlquilados: List[Libro],
                   fechaAlquiler: String)

object Cliente {
  implicit def clienteWriter: BSONDocumentWriter[Cliente] = Macros.writer[Cliente] //Escritura

  implicit def clienteReader: BSONDocumentReader[Cliente] = Macros.reader[Cliente] //Lectura
}