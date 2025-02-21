package ec.webmarket.restful.service.crud;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.webmarket.restful.domain.Paciente;
import ec.webmarket.restful.domain.Usuario;
import ec.webmarket.restful.dto.v1.PacienteDTO;
import ec.webmarket.restful.persistence.PacienteRepository;
import ec.webmarket.restful.persistence.UsuarioRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public List<PacienteDTO> findAll() {
        return pacienteRepository.findAll()
                .stream()
                .map(paciente -> modelMapper.map(paciente, PacienteDTO.class))
                .collect(Collectors.toList());
    }

    public PacienteDTO create(PacienteDTO pacienteDTO) {
        pacienteDTO.setFechaNacimiento(LocalDate.now());

        if (pacienteDTO.getFechaNacimiento() == null) {
            throw new RuntimeException("La fecha de nacimiento es obligatoria.");
        }

        Paciente paciente = modelMapper.map(pacienteDTO, Paciente.class);

        // Crear usuario automáticamente
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(pacienteDTO.getEmail());
        usuario.setClave("defaultPassword"); // 🔹 Sin cifrado, como lo pediste
        usuario.setTipoUsuario(Usuario.Rol.PACIENTE);

        usuario = usuarioRepository.save(usuario);
        paciente.setUsuario(usuario);

        return modelMapper.map(pacienteRepository.save(paciente), PacienteDTO.class);
    }

    public PacienteDTO update(Long id, PacienteDTO pacienteDTO) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        paciente.setNombre(pacienteDTO.getNombre());
        paciente.setApellido(pacienteDTO.getApellido());
        paciente.setEmail(pacienteDTO.getEmail());
        paciente.setTelefono(pacienteDTO.getTelefono());
        paciente.setDireccionResidencia(pacienteDTO.getDireccionResidencia());

        return modelMapper.map(pacienteRepository.save(paciente), PacienteDTO.class);
    }

    public void delete(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new RuntimeException("Paciente no encontrado");
        }
        pacienteRepository.deleteById(id);
    }
}
