package clinica_back.clinica_back.features.Consulta.Prontuario;

import clinica_back.clinica_back.features.Consulta.Prontuario.DTOs.ProntuarioRequestDTO;
import clinica_back.clinica_back.features.Consulta.Prontuario.DTOs.ProntuarioResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/prontuarios")
@RequiredArgsConstructor
public class ProntuarioController {

    private final ProntuarioService prontuarioService;
    @PreAuthorize("hasAuthority('ROLE_MEDICO') or hasAuthority('ROLE_ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<ProntuarioResponseDTO> cadastrar(
            @RequestBody @Valid ProntuarioRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(prontuarioService.cadastrar(dto));
    }
}