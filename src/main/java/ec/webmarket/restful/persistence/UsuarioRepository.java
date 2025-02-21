package ec.webmarket.restful.persistence;

import ec.webmarket.restful.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repositorio de acceso a datos para la entidad Usuario.
 * Extiende JpaRepository, lo que permite realizar operaciones CRUD sin necesidad de implementar métodos básicos.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su nombre de usuario.
     * 
     * @param nombreUsuario Nombre de usuario a buscar
     * @return Un Optional que contiene el usuario si se encuentra
     */
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    /**
     * Busca un usuario por su nombre de usuario y clave (sin cifrar).
     * 
     * @param nombreUsuario Nombre de usuario
     * @param clave Clave en texto plano
     * @return Un Optional con el usuario si las credenciales coinciden
     */
    Optional<Usuario> findByNombreUsuarioAndClave(String nombreUsuario, String clave);
}
