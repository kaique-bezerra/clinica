package clinica_back.clinica_back.features.Usuario.Medico;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clinica_back.clinica_back.features.Usuario.Medico.dto.MedicoRequestCadastroDTO;
import clinica_back.clinica_back.features.Usuario.Medico.dto.MedicoRequestUpdateDTO;
import clinica_back.clinica_back.features.Usuario.Medico.dto.MedicoResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/medico")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoService medicoService;

    @PostMapping
    public ResponseEntity<MedicoResponseDTO> cadastrar(@Valid @RequestBody MedicoRequestCadastroDTO dto) {
        MedicoResponseDTO medico = medicoService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(medico);
    }

    @GetMapping
    public ResponseEntity<List<MedicoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(medicoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(medicoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> atualizarDados(@PathVariable Long id,
            @RequestBody MedicoRequestUpdateDTO medico) {
        return ResponseEntity.ok(medicoService.atualizarDados(id, medico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        medicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
