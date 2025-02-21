package ec.webmarket.restful.api.v1;

import ec.webmarket.restful.dto.v1.UsuarioDTO;
import ec.webmarket.restful.service.crud.UsuarioService;
import ec.webmarket.restful.security.ApiResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controlador para gestionar usuarios en la API.
 * Maneja operaciones como registro, autenticación y actualización de contraseñas.
 */
@RestController
@RequestMapping("/api/v1.0/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Endpoint para registrar un nuevo usuario.
     * 
     * @param usuarioDTO Datos del usuario (nombreUsuario, clave, tipoUsuario)
     * @return Respuesta con éxito y el usuario registrado
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(new ApiResponseDTO<>(true, usuarioService.register(usuarioDTO)));
    }

    /**
     * Endpoint para autenticar un usuario.
     * 
     * @param usuarioDTO Datos del usuario (nombreUsuario y clave)
     * @return Respuesta con éxito si las credenciales son correctas
     */
    @PostMapping("/autenticar")
    public ResponseEntity<?> autenticarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Optional<UsuarioDTO> usuarioAutenticado = usuarioService.autenticarUsuario(
                usuarioDTO.getNombreUsuario(),
                usuarioDTO.getClave()
        );

        if (usuarioAutenticado.isPresent()) {
            return ResponseEntity.ok(new ApiResponseDTO<>(true, usuarioAutenticado.get()));
        } else {
            return ResponseEntity.status(401)
                    .body(new ApiResponseDTO<>(false, "Usuario o contraseña incorrectos"));
        }
    }

    /**
     * Endpoint para actualizar la contraseña de un usuario.
     * 
     * @param usuarioDTO Datos del usuario (id y nueva clave)
     * @return Mensaje de éxito si la contraseña se actualizó correctamente
     */
    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody UsuarioDTO usuarioDTO) {
        usuarioService.updatePassword(usuarioDTO.getId(), usuarioDTO.getClave());
        return ResponseEntity.ok(new ApiResponseDTO<>(true, "Contraseña actualizada correctamente"));
    }
}
