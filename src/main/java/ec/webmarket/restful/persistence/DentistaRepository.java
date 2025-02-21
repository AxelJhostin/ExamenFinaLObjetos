package ec.webmarket.restful.persistence;

import ec.webmarket.restful.domain.Dentista;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DentistaRepository extends JpaRepository<Dentista, Long> {
    Optional<Dentista> findByCedula(String cedula);
}
