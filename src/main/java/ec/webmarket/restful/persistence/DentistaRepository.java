package ec.webmarket.restful.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import ec.webmarket.restful.domain.Dentista;

/**
 * Repositorio de acceso a datos para la entidad Dentista.
 * Extiende JpaRepository, lo que permite realizar operaciones CRUD sin necesidad de implementarlas manualmente.
 */
public interface DentistaRepository extends JpaRepository<Dentista, Long> {

    /**
     * Busca un dentista por su número de cédula.
     * 
     * @param cedula Número de cédula del dentista
     * @return Un Optional que contiene el dentista si se encuentra
     */
    Optional<Dentista> findByCedula(String cedula);
}
