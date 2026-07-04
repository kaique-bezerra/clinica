package clinica_back.clinica_back.features.Consulta.AgendaPadrao.HorarioBloqueado;

import clinica_back.clinica_back.features.Consulta.AgendaPadrao.HorarioBloqueado.DTOs.HorarioBloqueadoRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/bloqueios")
@RequiredArgsConstructor
public class HorarioBloqueadoController {

    private final HorarioBloqueadoService bloqueioService;
    
    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<HorarioBloqueado> cadastrar(
            @RequestBody HorarioBloqueadoRequestDTO dto) {

        HorarioBloqueado bloqueio = bloqueioService.cadastrar(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bloqueio);
    }
}
