package ec.webmarket.restful.service.crud;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.webmarket.restful.domain.Dentista;
import ec.webmarket.restful.domain.Usuario;
import ec.webmarket.restful.dto.v1.DentistaDTO;
import ec.webmarket.restful.persistence.DentistaRepository;
import ec.webmarket.restful.persistence.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DentistaService {

    @Autowired
    private DentistaRepository dentistaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public List<DentistaDTO> findAll() {
        return dentistaRepository.findAll()
                .stream()
                .map(dentista -> modelMapper.map(dentista, DentistaDTO.class))
                .collect(Collectors.toList());
    }

    public DentistaDTO create(DentistaDTO dentistaDTO) {
        Dentista dentista = modelMapper.map(dentistaDTO, Dentista.class);

        // Crear usuario automáticamente
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(dentistaDTO.getEmail());
        usuario.setClave("defaultPassword"); // 🔹 Sin cifrado, como lo pediste
        usuario.setTipoUsuario(Usuario.Rol.ODONTOLOGO);

        usuario = usuarioRepository.save(usuario);
        dentista.setUsuario(usuario);

        return modelMapper.map(dentistaRepository.save(dentista), DentistaDTO.class);
    }

    public DentistaDTO update(Long id, DentistaDTO dentistaDTO) {
        Dentista dentista = dentistaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dentista no encontrado"));

        dentista.setNombre(dentistaDTO.getNombre());
        dentista.setApellido(dentistaDTO.getApellido());
        dentista.setEspecialidad(dentistaDTO.getEspecialidad());
        dentista.setTelefono(dentistaDTO.getTelefono());
        dentista.setEmail(dentistaDTO.getEmail());

        return modelMapper.map(dentistaRepository.save(dentista), DentistaDTO.class);
    }

    public void delete(Long id) {
        if (!dentistaRepository.existsById(id)) {
            throw new RuntimeException("Dentista no encontrado");
        }
        dentistaRepository.deleteById(id);
    }
}
