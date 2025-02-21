package ec.webmarket.restful.service.crud;

import ec.webmarket.restful.domain.Usuario;
import ec.webmarket.restful.dto.v1.UsuarioDTO;
import ec.webmarket.restful.persistence.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private ModelMapper modelMapper = new ModelMapper();

    // ✅ Registro de usuario (corrigiendo cifrado de contraseña)
    @Transactional
    public UsuarioDTO register(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.findByNombreUsuario(usuarioDTO.getNombreUsuario()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya está en uso.");
        }

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());

        // 🔐 Cifrar la clave antes de guardarla
        String claveCifrada = passwordEncoder.encode(usuarioDTO.getClave());
        usuario.setClave(claveCifrada);

        usuario.setTipoUsuario(Usuario.Rol.valueOf(usuarioDTO.getTipoUsuario().toUpperCase()));

        usuarioRepository.save(usuario);

        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setClave(null); // No exponer la clave en la respuesta

        return usuarioDTO;
    }

    // ✅ Autenticación de usuario (comparando con la clave cifrada)
    public boolean authenticate(String nombreUsuario, String clave) {
        Optional<Usuario> usuario = usuarioRepository.findByNombreUsuario(nombreUsuario);

        if (usuario.isPresent()) {
            // 🔍 Imprimir contraseñas para depuración
            System.out.println("🔑 Clave ingresada: " + clave);
            System.out.println("🔒 Clave almacenada en BD: " + usuario.get().getClave());

            // ✅ Comparar la clave ingresada con la clave cifrada en la BD
            if (passwordEncoder.matches(clave, usuario.get().getClave())) {
                System.out.println("✅ Contraseña válida para el usuario: " + nombreUsuario);
                return true;
            } else {
                System.out.println("❌ Contraseña incorrecta para el usuario: " + nombreUsuario);
            }
        } else {
            System.out.println("❌ Usuario no encontrado: " + nombreUsuario);
        }

        return false;
    }
}
