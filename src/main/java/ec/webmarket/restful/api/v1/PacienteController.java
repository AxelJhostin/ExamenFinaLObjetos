package ec.webmarket.restful.api.v1;

import ec.webmarket.restful.dto.v1.PacienteDTO;
import ec.webmarket.restful.security.ApiResponseDTO;
import ec.webmarket.restful.service.crud.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<?> createPaciente(@RequestBody PacienteDTO pacienteDTO) {
        return ResponseEntity.ok(new ApiResponseDTO<>(true, pacienteService.create(pacienteDTO)));
    }

    @GetMapping
    public ResponseEntity<?> getAllPacientes() {
        return ResponseEntity.ok(new ApiResponseDTO<>(true, pacienteService.findAll()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePaciente(@PathVariable Long id, @RequestBody PacienteDTO pacienteDTO) {
        return ResponseEntity.ok(new ApiResponseDTO<>(true, pacienteService.update(id, pacienteDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePaciente(@PathVariable Long id) {
        pacienteService.delete(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(true, "Paciente eliminado correctamente"));
    }
}
