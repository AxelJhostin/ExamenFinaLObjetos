package ec.webmarket.restful.service.crud;

import ec.webmarket.restful.domain.Usuario;
import ec.webmarket.restful.dto.v1.UsuarioDTO;
import ec.webmarket.restful.persistence.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.Optional;

/**
 * Servicio para gestionar la lógica de usuarios.
 * Maneja el registro, autenticación, actualización de contraseñas y asignación de roles.
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Obtiene los datos de un usuario por su ID.
     * 
     * @param id ID del usuario a buscar
     * @return Un Optional que contiene los datos del usuario en formato DTO
     */
    public Optional<UsuarioDTO> getById(Long id) {
        return usuarioRepository.findById(id)
            .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class));
    }

    /**
     * Asigna un nuevo rol a un usuario existente.
     * 
     * @param id ID del usuario al que se le asignará el rol
     * @param role Nuevo rol a asignar (Debe ser "PACIENTE" o "ODONTOLOGO")
     */
    @Transactional
    public void assignRole(Long id, String role) {
        // Buscar el usuario en la base de datos por su ID
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Convertir el string del rol a ENUM y asignarlo al usuario
        usuario.setTipoUsuario(Usuario.Rol.valueOf(role.toUpperCase()));

        // Guardar el usuario actualizado en la base de datos
        usuarioRepository.save(usuario);
    }
    
    @Transactional
    public UsuarioDTO register(UsuarioDTO usuarioDTO) {
        // Verificar si el usuario ya existe
        if (usuarioRepository.findByNombreUsuario(usuarioDTO.getNombreUsuario()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya está en uso.");
        }

        // Crear el usuario en la base de datos
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuario.setClave(usuarioDTO.getClave()); // 🔹 Sin cifrar, según lo solicitado
        usuario.setTipoUsuario(Usuario.Rol.valueOf(usuarioDTO.getTipoUsuario().toUpperCase()));

        usuarioRepository.save(usuario);
        usuarioDTO.setId(usuario.getId());

        return usuarioDTO;
    }


    /**
     * Autentica a un usuario verificando su nombre de usuario y contraseña.
     * 
     * @param nombreUsuario Nombre de usuario del sistema.
     * @param clave Clave en texto plano.
     * @return UsuarioDTO si las credenciales son correctas, o vacío si son incorrectas.
     */
    public Optional<UsuarioDTO> autenticarUsuario(String nombreUsuario, String clave) {
        Optional<Usuario> usuario = usuarioRepository.findByNombreUsuarioAndClave(nombreUsuario, clave);

        return usuario.map(u -> {
            UsuarioDTO usuarioDto = new UsuarioDTO();
            usuarioDto.setId(u.getId());
            usuarioDto.setNombreUsuario(u.getNombreUsuario());
            usuarioDto.setTipoUsuario(u.getTipoUsuario().name());
            usuarioDto.setClave(u.getClave()); // 🔹 Se muestra la clave en la respuesta solo para pruebas

            return usuarioDto;
        });
    }
}
