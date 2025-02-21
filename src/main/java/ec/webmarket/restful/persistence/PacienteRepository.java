package ec.webmarket.restful.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import ec.webmarket.restful.domain.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByCedula(String cedula);
}
