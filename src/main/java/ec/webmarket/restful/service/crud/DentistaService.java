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

/**
 * Servicio que maneja la lógica de negocio relacionada con los dentistas.
 */
@Service
public class DentistaService {

    @Autowired
    private DentistaRepository dentistaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Crea un nuevo dentista en la base de datos.
     * Primero, verifica si la cédula ya existe para evitar duplicados.
     *
     * @param dentistaDTO Datos del dentista a registrar
     * @return Dentista registrado convertido en DTO
     */
    public DentistaDTO create(DentistaDTO dentistaDTO) {
        // Verificar si ya existe un dentista con la misma cédula
        if (dentistaRepository.findByCedula(dentistaDTO.getCedula()).isPresent()) {
            throw new RuntimeException("Ya existe un odontólogo con esta cédula.");
        }

        // Convertir DTO a entidad Dentista
        Dentista dentista = modelMapper.map(dentistaDTO, Dentista.class);

        // Crear automáticamente un usuario vinculado al dentista
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(dentistaDTO.getEmail());
        usuario.setClave("defaultPassword"); // 🔹 Sin cifrado, como lo pediste
        usuario.setTipoUsuario(Usuario.Rol.ODONTOLOGO);

        // Guardar el usuario en la base de datos
        usuario = usuarioRepository.save(usuario);
        
        // Asociar el usuario al dentista y guardarlo
        dentista.setUsuario(usuario);

        return modelMapper.map(dentistaRepository.save(dentista), DentistaDTO.class);
    }

    /**
     * Obtiene la lista de todos los dentistas registrados en la base de datos.
     *
     * @return Lista de dentistas en formato DTO
     */
    public List<DentistaDTO> findAll() {
        return dentistaRepository.findAll()
                .stream()
                .map(dentista -> modelMapper.map(dentista, DentistaDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Actualiza los datos de un dentista existente.
     *
     * @param id ID del dentista a actualizar
     * @param dentistaDTO Datos actualizados del dentista
     * @return Dentista actualizado en formato DTO
     */
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

    /**
     * Elimina un dentista por su ID.
     *
     * @param id ID del dentista a eliminar
     */
    public void delete(Long id) {
        if (!dentistaRepository.existsById(id)) {
            throw new RuntimeException("Dentista no encontrado");
        }
        dentistaRepository.deleteById(id);
    }
}
