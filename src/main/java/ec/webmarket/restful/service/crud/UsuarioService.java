package ec.webmarket.restful.service.crud;

import ec.webmarket.restful.domain.Usuario;
import ec.webmarket.restful.dto.v1.UsuarioDTO;
import ec.webmarket.restful.persistence.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public UsuarioDTO register(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.findByNombreUsuario(usuarioDTO.getNombreUsuario()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya está en uso.");
        }

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuario.setClave(usuarioDTO.getClave()); // 🔹 Sin cifrar, como lo pediste
        usuario.setTipoUsuario(Usuario.Rol.valueOf(usuarioDTO.getTipoUsuario().toUpperCase()));

        usuarioRepository.save(usuario);
        usuarioDTO.setId(usuario.getId());

        return usuarioDTO;
    }
}
