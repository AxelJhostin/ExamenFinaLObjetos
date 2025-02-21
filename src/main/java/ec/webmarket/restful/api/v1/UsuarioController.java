package ec.webmarket.restful.api.v1;

import ec.webmarket.restful.dto.v1.UsuarioDTO;
import ec.webmarket.restful.security.ApiResponseDTO;
import ec.webmarket.restful.service.crud.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ec.webmarket.restful.common.ApiConstants;


@RestController
@RequestMapping(ApiConstants.URI_API_V1_USUARIO)
public class UsuarioController {


    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UsuarioDTO usuarioDTO) {
        System.out.println("🚀 Endpoint /register llamado con usuario: " + usuarioDTO.getNombreUsuario());
        return ResponseEntity.ok(new ApiResponseDTO<>(true, usuarioService.register(usuarioDTO)));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String nombreUsuario, @RequestParam String clave) {
        boolean authenticated = usuarioService.authenticate(nombreUsuario, clave);
        if (authenticated) {
            return ResponseEntity.ok(new ApiResponseDTO<>(true, "Autenticación exitosa"));
        } else {
            return ResponseEntity.status(401).body(new ApiResponseDTO<>(false, "Credenciales incorrectas"));
        }
    }
}
