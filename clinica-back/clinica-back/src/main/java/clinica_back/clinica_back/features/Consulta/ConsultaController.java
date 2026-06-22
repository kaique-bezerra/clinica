package clinica_back.clinica_back.features.Consulta;

import clinica_back.clinica_back.features.Consulta.DTOs.ConsultaRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaService consultaService;

    @PostMapping
    public ResponseEntity<Consulta> cadastrar(
            @RequestBody @Valid ConsultaRequestDTO dto) {

        Consulta consulta = consultaService.cadastrar(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(consulta);
    }

    @PatchMapping("/{id}/realizar")
    public ResponseEntity<Consulta> realizar(
            @PathVariable Long id) {

        Consulta consulta = consultaService.StatusConsulta(id);

        return ResponseEntity.ok(consulta);
    }
}