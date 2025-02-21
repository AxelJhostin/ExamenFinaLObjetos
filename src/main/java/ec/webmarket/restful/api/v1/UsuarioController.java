package ec.webmarket.restful.api.v1;

import ec.webmarket.restful.dto.v1.UsuarioDTO;
import ec.webmarket.restful.service.crud.UsuarioService;
import ec.webmarket.restful.security.ApiResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(new ApiResponseDTO<>(true, usuarioService.register(usuarioDTO)));
    }
}
