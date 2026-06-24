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
    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRADOR')or hasRole('MEDICO')or hasRole('PACIENTE')")
    @PostMapping
    public ResponseEntity<Consulta> cadastrar(
            @RequestBody @Valid ConsultaRequestDTO dto) {

        Consulta consulta = consultaService.cadastrar(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(consulta);
    }


}
