package uy.com.ejemplo.domain.respuestas

trait Respuesta

case class MensajeRespuesta(mensaje: String, statusCode: Int) extends Respuesta

case class MensajeRespuestaConEntidad[A](entidad: A, mensaje: String, statusCode: Int)