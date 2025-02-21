package ec.webmarket.restful.service.crud;

import ec.webmarket.restful.domain.Usuario;
import ec.webmarket.restful.dto.v1.UsuarioDTO;
import ec.webmarket.restful.persistence.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Servicio para gestionar la lógica de usuarios.
 * Maneja el registro, autenticación y actualización de contraseñas.
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Logger para imprimir información en la consola y depurar errores.
    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    /**
     * Registra un nuevo usuario en la base de datos.
     * 
     * @param usuarioDTO Datos del usuario (nombreUsuario, clave, tipoUsuario)
     * @return Usuario registrado con su ID asignado
     */
    @Transactional
    public UsuarioDTO register(UsuarioDTO usuarioDTO) {
        // Verifica si el usuario ya existe en la base de datos
        if (usuarioRepository.findByNombreUsuario(usuarioDTO.getNombreUsuario()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya está en uso.");
        }

        // Crea una nueva instancia de Usuario
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuario.setClave(usuarioDTO.getClave()); // 🔹 Se mantiene sin cifrar, como lo pediste
        usuario.setTipoUsuario(Usuario.Rol.valueOf(usuarioDTO.getTipoUsuario().toUpperCase()));

        // Guarda el usuario en la base de datos
        usuarioRepository.save(usuario);
        usuarioDTO.setId(usuario.getId());

        return usuarioDTO;
    }

    /**
     * Autentica a un usuario verificando su nombre de usuario y contraseña.
     * Ahora incluye logs para depuración.
     * 
     * @param nombreUsuario Nombre de usuario
     * @param clave Contraseña en texto plano
     * @return Datos del usuario si las credenciales son correctas
     */
    public Optional<UsuarioDTO> autenticarUsuario(String nombreUsuario, String clave) {
        logger.info("Intentando autenticar usuario: " + nombreUsuario);

        // Busca al usuario en la base de datos por nombre de usuario y clave
        Optional<Usuario> usuario = usuarioRepository.findByNombreUsuarioAndClave(nombreUsuario, clave);

        if (usuario.isPresent()) {
            logger.info("✅ Autenticación exitosa para usuario: " + nombreUsuario);
            
            // Se crea un DTO para devolver solo los datos necesarios del usuario
            UsuarioDTO usuarioDto = new UsuarioDTO();
            usuarioDto.setId(usuario.get().getId());
            usuarioDto.setNombreUsuario(usuario.get().getNombreUsuario());
            usuarioDto.setTipoUsuario(usuario.get().getTipoUsuario().name());
            usuarioDto.setClave(usuario.get().getClave()); // 🔹 Se devuelve la clave para verificar autenticación

            return Optional.of(usuarioDto);
        } else {
            logger.warn("⚠ Autenticación fallida para usuario: " + nombreUsuario);
            return Optional.empty();
        }
    }

    /**
     * Actualiza la contraseña de un usuario.
     * 
     * @param id ID del usuario
     * @param nuevaClave Nueva contraseña
     */
    @Transactional
    public void updatePassword(Long id, String nuevaClave) {
        // Busca al usuario en la base de datos
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualiza la contraseña con la nueva clave
        usuario.setClave(nuevaClave);
        usuarioRepository.save(usuario);
    }
}
