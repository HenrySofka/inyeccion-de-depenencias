package uy.com.ejemplo.domain.dto

import reactivemongo.api.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}

case class LibroDto(isbn: String,
                    nombre: String)

object LibroDto {
  implicit def libroDtoWriter: BSONDocumentWriter[LibroDto] = Macros.writer[LibroDto] //Escritura

  implicit def libroDtoReader: BSONDocumentReader[LibroDto] = Macros.reader[LibroDto] //Lectura
}