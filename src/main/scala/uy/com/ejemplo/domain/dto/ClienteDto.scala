package uy.com.ejemplo.domain.dto

import reactivemongo.api.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}
import uy.com.ejemplo.domain.entities.{Cliente, Libro}

case class ClienteDto(nombre: String,
                      apellido: String,
                      telefono: String,
                      cdedula: String,
                      librosAlquilados: List[Libro])

object ClienteDto {

  implicit def clienteDtoWriter: BSONDocumentWriter[ClienteDto] = Macros.writer[ClienteDto] //Escritura

  implicit def clienteDtoReader: BSONDocumentReader[ClienteDto] = Macros.reader[ClienteDto] //Lectura
}
