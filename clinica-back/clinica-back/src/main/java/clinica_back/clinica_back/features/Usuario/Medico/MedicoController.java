package clinica_back.clinica_back.features.Usuario.Medico;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import clinica_back.clinica_back.features.Usuario.Medico.dto.MedicoRequestCadastroDTO;
import clinica_back.clinica_back.features.Usuario.Medico.dto.MedicoRequestUpdateDTO;
import clinica_back.clinica_back.features.Usuario.Medico.dto.MedicoResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
    @RequestMapping("/medico")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoService medicoService;

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<MedicoResponseDTO> cadastrar(@Valid @RequestBody MedicoRequestCadastroDTO dto) {
        MedicoResponseDTO medico = medicoService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(medico);
    }
    @PreAuthorize("hasAuthority('ROLE_RECEPCIONISTA') or hasAuthority('ROLE_ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<List<MedicoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(medicoService.listarTodos());
    }
    @PreAuthorize("hasAuthority('ROLE_RECEPCIONISTA') or hasAuthority('ROLE_ADMINISTRADOR')")
    @GetMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(medicoService.buscarPorId(id));
    }
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> atualizarDados(@PathVariable Long id,
            @RequestBody MedicoRequestUpdateDTO medico) {
        return ResponseEntity.ok(medicoService.atualizarDados(id, medico));
    }
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        medicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        medicoService.inativar(id);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        medicoService.ativar(id);
        return ResponseEntity.noContent().build();
    }
}
