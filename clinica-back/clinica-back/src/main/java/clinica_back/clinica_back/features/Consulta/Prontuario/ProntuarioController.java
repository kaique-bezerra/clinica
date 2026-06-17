package clinica_back.clinica_back.features.Consulta.Prontuario;

import clinica_back.clinica_back.features.Consulta.Prontuario.DTOs.ProntuarioRequestDTO;
import clinica_back.clinica_back.features.Consulta.Prontuario.DTOs.ProntuarioResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prontuarios")
@RequiredArgsConstructor
public class ProntuarioController {

    private final ProntuarioService prontuarioService;

    @PostMapping
    public ResponseEntity<ProntuarioResponseDTO> cadastrar(
            @RequestBody @Valid ProntuarioRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(prontuarioService.cadastrar(dto));
    }
}