package ec.webmarket.restful.api.v1;

import ec.webmarket.restful.dto.v1.DentistaDTO;
import ec.webmarket.restful.security.ApiResponseDTO;
import ec.webmarket.restful.service.crud.DentistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/dentistas")
public class DentistaController {

    @Autowired
    private DentistaService dentistaService;

    @PostMapping
    public ResponseEntity<?> createDentista(@RequestBody DentistaDTO dentistaDTO) {
        return ResponseEntity.ok(new ApiResponseDTO<>(true, dentistaService.create(dentistaDTO)));
    }

    @GetMapping
    public ResponseEntity<?> getAllDentistas() {
        return ResponseEntity.ok(new ApiResponseDTO<>(true, dentistaService.findAll()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDentista(@PathVariable Long id, @RequestBody DentistaDTO dentistaDTO) {
        return ResponseEntity.ok(new ApiResponseDTO<>(true, dentistaService.update(id, dentistaDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDentista(@PathVariable Long id) {
        dentistaService.delete(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(true, "Dentista eliminado correctamente"));
    }
}
