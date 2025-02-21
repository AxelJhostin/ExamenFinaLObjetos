package ec.webmarket.restful.api.v1;

import ec.webmarket.restful.domain.Paciente;
import ec.webmarket.restful.dto.v1.ApiResponseDTO;
import ec.webmarket.restful.service.crud.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1.0/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<Paciente>> registerPaciente(@RequestBody Paciente paciente) {
        Paciente nuevoPaciente = pacienteService.register(paciente);
        return ResponseEntity.ok(new ApiResponseDTO<>(true, nuevoPaciente));
    }

    @GetMapping("/{cedula}")
    public ResponseEntity<ApiResponseDTO<Paciente>> getPacientePorCedula(@PathVariable String cedula) {
        Optional<Paciente> pacienteOpt = pacienteService.getPacienteByCedula(cedula);

        return pacienteOpt.map(paciente -> 
            ResponseEntity.ok(new ApiResponseDTO<>(true, paciente))
        ).orElseGet(() -> 
            ResponseEntity.status(404).body(new ApiResponseDTO<>(false, "Paciente no encontrado"))
        );
    }
}
