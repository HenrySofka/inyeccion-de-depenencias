package uy.com.ejemplo.domain.helpers

import uy.com.ejemplo.domain.dto.LibroDto
import uy.com.ejemplo.domain.entities.Libro

import java.util.UUID

object LibroHelper {
  def entityToDto(libro: Libro): LibroDto = {
    LibroDto(
      libro.isbn,
      libro.nombre
    )
  }

  def dtoToEntity(libroDto: LibroDto): Libro = {
    Libro(
      UUID.randomUUID().toString,
      libroDto.isbn,
      libroDto.nombre
    )
  }
}
