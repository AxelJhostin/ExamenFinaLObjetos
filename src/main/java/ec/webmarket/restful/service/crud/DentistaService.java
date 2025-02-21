package ec.webmarket.restful.service.crud;

import ec.webmarket.restful.domain.Dentista;
import ec.webmarket.restful.domain.Usuario;
import ec.webmarket.restful.dto.v1.DentistaDTO;
import ec.webmarket.restful.persistence.DentistaRepository;
import ec.webmarket.restful.persistence.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.Optional;

@Service
public class DentistaService {

    @Autowired
    private DentistaRepository dentistaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Registra un nuevo dentista en la base de datos.
     * 
     * @param dentistaDTO Datos del dentista
     * @return Datos del dentista registrado
     */
    @Transactional
    public DentistaDTO register(DentistaDTO dentistaDTO) {
        // Verificar si ya existe un dentista con la misma cédula
        if (dentistaRepository.findByCedula(dentistaDTO.getCedula()).isPresent()) {
            throw new RuntimeException("Ya existe un odontólogo con esta cédula.");
        }

        // Crear un nuevo usuario para el dentista
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(dentistaDTO.getEmail());
        usuario.setClave("123456"); // 🔹 Se asigna una clave por defecto
        usuario.setTipoUsuario(Usuario.Rol.ODONTOLOGO);
        
        usuario = usuarioRepository.save(usuario); // Guardar el usuario en la base de datos

        // Crear el objeto Dentista con el usuario asignado
        Dentista dentista = modelMapper.map(dentistaDTO, Dentista.class);
        dentista.setUsuario(usuario); // Asociar el usuario al dentista
        
        dentista = dentistaRepository.save(dentista); // Guardar el dentista en la base de datos

        return modelMapper.map(dentista, DentistaDTO.class);
    }
}

