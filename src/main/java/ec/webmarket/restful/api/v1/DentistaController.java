package ec.webmarket.restful.api.v1;

import ec.webmarket.restful.dto.v1.DentistaDTO;
import ec.webmarket.restful.dto.v1.ApiResponseDTO;

import ec.webmarket.restful.service.crud.DentistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/dentistas")
public class DentistaController {

    @Autowired
    private DentistaService dentistaService;

    /**
     * Registra un nuevo dentista en la base de datos.
     * 
     * @param dentistaDTO Datos del dentista a registrar
     * @return Datos del dentista registrado
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<Object>> registerDentista(@RequestBody DentistaDTO dentistaDTO) {
        DentistaDTO dentistaCreado = dentistaService.register(dentistaDTO);
        return ResponseEntity.ok(new ApiResponseDTO<>(true, dentistaCreado));
    }
}
