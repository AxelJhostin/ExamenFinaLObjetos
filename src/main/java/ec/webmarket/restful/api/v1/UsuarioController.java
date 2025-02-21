package ec.webmarket.restful.api.v1;

import ec.webmarket.restful.dto.v1.UsuarioDTO;
import ec.webmarket.restful.security.ApiResponseDTO;
import ec.webmarket.restful.service.crud.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controlador para gestionar usuarios en la API.
 * Maneja operaciones como obtener un usuario por ID, autenticar y asignar roles.
 */
@RestController
@RequestMapping("/api/v1.0/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Obtiene los datos de un usuario por su ID.
     * 
     * @param id ID del usuario a buscar
     * @return Respuesta con éxito y los datos del usuario si se encuentra,
     *         de lo contrario, devuelve un error 404 con un mensaje de error.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Object>> getUsuarioById(@PathVariable Long id) {
        Optional<UsuarioDTO> usuarioOpt = usuarioService.getById(id);

        // Verificamos si el usuario existe y retornamos la respuesta correcta
        if (usuarioOpt.isPresent()) {
            return ResponseEntity.ok(new ApiResponseDTO<>(true, usuarioOpt.get()));
        } else {
            return ResponseEntity.status(404).body(new ApiResponseDTO<>(false, "Usuario no encontrado"));
        }
    }


    /**
     * Asigna un nuevo rol a un usuario existente.
     * 
     * @param id ID del usuario al que se le asignará el rol
     * @param role Nuevo rol a asignar (Debe ser "PACIENTE" o "ODONTOLOGO")
     * @return Mensaje de confirmación si la asignación es exitosa.
     */
    @PutMapping("/{id}/role")
    public ResponseEntity<ApiResponseDTO<String>> assignUserRole(@PathVariable Long id, @RequestParam String role) {
        usuarioService.assignRole(id, role);
        return ResponseEntity.ok(new ApiResponseDTO<>(true, "Rol asignado correctamente"));
    }

    /**
     * Autentica a un usuario verificando su nombre de usuario y clave.
     * 
     * @param usuarioDTO Contiene el nombre de usuario y clave.
     * @return Respuesta con éxito si las credenciales son correctas,
     *         de lo contrario, devuelve un error 401 (Unauthorized).
     */
    @PostMapping("/autenticar")
    public ResponseEntity<ApiResponseDTO<Object>> autenticarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Optional<UsuarioDTO> usuarioOpt = usuarioService.autenticarUsuario(usuarioDTO.getNombreUsuario(), usuarioDTO.getClave());

        // Verificamos si el usuario existe y retornamos la respuesta correcta
        if (usuarioOpt.isPresent()) {
            return ResponseEntity.ok(new ApiResponseDTO<>(true, usuarioOpt.get()));
        } else {
            return ResponseEntity.status(401).body(new ApiResponseDTO<>(false, "Usuario o contraseña incorrectos"));
        }
    }
    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<Object>> registerUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO usuarioCreado = usuarioService.register(usuarioDTO);
        return ResponseEntity.ok(new ApiResponseDTO<>(true, usuarioCreado));
    }


}
