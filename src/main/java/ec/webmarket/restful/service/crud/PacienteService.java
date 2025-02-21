package ec.webmarket.restful.service.crud;

import ec.webmarket.restful.domain.Paciente;
import ec.webmarket.restful.persistence.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Transactional
    public Paciente register(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public Optional<Paciente> getPacienteByCedula(String cedula) {
        return pacienteRepository.findByCedula(cedula);
    }
}
