package ec.webmarket.restful.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import ec.webmarket.restful.domain.Paciente;

/**
 * Repositorio de acceso a datos para la entidad Paciente.
 * Extiende JpaRepository, lo que permite realizar operaciones CRUD sin necesidad de implementarlas manualmente.
 */
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    /**
     * Busca un paciente por su número de cédula.
     * 
     * @param cedula Número de cédula del paciente
     * @return Un Optional que contiene el paciente si se encuentra
     */
    Optional<Paciente> findByCedula(String cedula);
}
