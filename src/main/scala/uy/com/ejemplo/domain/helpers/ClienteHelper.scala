package uy.com.ejemplo.domain.helpers

import uy.com.ejemplo.domain.dto.ClienteDto
import uy.com.ejemplo.domain.entities.Cliente

import java.time.LocalDateTime
import java.util.UUID

object ClienteHelper {
  def entityToDto(cliente: Cliente): ClienteDto = {
    ClienteDto(
      cliente.nombre,
      cliente.apellido,
      cliente.telefono,
      cliente.cdedula,
      cliente.librosAlquilados
    )
  }

  def dtoToEntity(clienteDto: ClienteDto): Cliente = {
    Cliente(
      UUID.randomUUID().toString,
      clienteDto.nombre,
      clienteDto.apellido,
      clienteDto.telefono,
      clienteDto.cdedula,
      clienteDto.librosAlquilados,
      LocalDateTime.now().toString
    )
  }

}
