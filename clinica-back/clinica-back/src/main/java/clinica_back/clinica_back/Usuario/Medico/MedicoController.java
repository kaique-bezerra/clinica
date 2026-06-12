package clinica_back.clinica_back.Usuario.Medico;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clinica_back.clinica_back.Usuario.Medico.dto.MedicoRequestDTO;
import clinica_back.clinica_back.Usuario.Medico.dto.MedicoResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/medico")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoService medicoService;

    @PostMapping
    public ResponseEntity<MedicoResponseDTO> cadastrar(@Valid @RequestBody MedicoRequestDTO dto) {
        MedicoResponseDTO medico = medicoService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(medico);
    }
}
