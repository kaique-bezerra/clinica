package clinica_back.clinica_back.features.Consulta;

import clinica_back.clinica_back.features.Consulta.DTOs.ConsultaRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaService consultaService;

    @PreAuthorize("hasAuthority('ROLE_RECEPCIONISTA') or hasAuthority('ROLE_ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<Consulta> cadastrar(
            @RequestBody @Valid ConsultaRequestDTO dto) {

        Consulta consulta = consultaService.cadastrar(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(consulta);
    }

    @PreAuthorize("hasAuthority('ROLE_RECEPCIONISTA') or hasAuthority('ROLE_ADMINISTRADOR')")
    @PatchMapping("/{id}/realizar")
    public ResponseEntity<Consulta> realizar(
            @PathVariable Long id) {

        Consulta consulta = consultaService.StatusConsulta(id);

        return ResponseEntity.ok(consulta);
    }
}
