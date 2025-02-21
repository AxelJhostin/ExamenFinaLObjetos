package ec.webmarket.restful.dto.v1;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacienteDTO {
    private String nombre;
    private String apellido;
    private String cedula;
    private String email;
    private String telefono;
    private String direccionResidencia;
}
