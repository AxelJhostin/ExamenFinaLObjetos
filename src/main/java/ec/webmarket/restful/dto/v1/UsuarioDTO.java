package ec.webmarket.restful.dto.v1;

import lombok.Data;

/**
 * DTO (Data Transfer Object) para la entidad Usuario.
 * Se utiliza para transferir datos de usuarios entre el backend y el frontend,
 * sin exponer la estructura interna de la base de datos.
 */
@Data
public class UsuarioDTO {

    /**
     * Identificador único del usuario.
     */
    private Long id;

    /**
     * Nombre de usuario con el que inicia sesión.
     * Debe ser único en la base de datos.
     */
    private String nombreUsuario;

    /**
     * Clave del usuario (sin cifrar, según lo solicitado).
     */
    private String clave;

    /**
     * Tipo de usuario.
     * Puede ser "PACIENTE" o "ODONTOLOGO".
     */
    private String tipoUsuario;
}
