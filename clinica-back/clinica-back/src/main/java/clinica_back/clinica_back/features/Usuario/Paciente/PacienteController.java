package clinica_back.clinica_back.features.Usuario.Paciente;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clinica_back.clinica_back.features.Usuario.Paciente.DadosClinicos.Alergia.dto.AlergiaRequestDTO;
import clinica_back.clinica_back.features.Usuario.Paciente.DadosClinicos.Alergia.dto.AlergiaResponseDTO;
import clinica_back.clinica_back.features.Usuario.Paciente.DadosClinicos.DoencaCronica.dto.DoencaCronicaRequestDTO;
import clinica_back.clinica_back.features.Usuario.Paciente.DadosClinicos.DoencaCronica.dto.DoencaCronicaResponseDTO;
import clinica_back.clinica_back.features.Usuario.Paciente.dto.PacienteCompletoResponseDTO;
import clinica_back.clinica_back.features.Usuario.Paciente.dto.PacienteRequestCadastrarDTO;
import clinica_back.clinica_back.features.Usuario.Paciente.dto.PacienteRequestUpdateDTO;
import clinica_back.clinica_back.features.Usuario.Paciente.dto.PacienteResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/paciente")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @PreAuthorize("hasAuthority('RECEPCIONISTA') or hasAuthority('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<PacienteResponseDTO> cadastrar(@Valid @RequestBody PacienteRequestCadastrarDTO dto) {
        PacienteResponseDTO paciente = pacienteService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(paciente);
    }

    @PreAuthorize("hasAuthority('ROLE_RECEPCIONISTA') or hasAuthority('ROLE_ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> listarTodos() {
        return ResponseEntity.ok(pacienteService.listarTodos());
    }

    @PreAuthorize("hasAuthority('ROLE_RECEPCIONISTA') or hasAuthority('ROLE_ADMINISTRADOR') or hasAuthority('ROLE_PACIENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<PacienteCompletoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pacienteService.buscarPorId(id));
    }

    @PreAuthorize("hasAuthority('ROLE_RECEPCIONISTA') or hasAuthority('ROLE_ADMINISTRADOR')")
    @PutMapping("{id}")
    public ResponseEntity<PacienteResponseDTO> atualizarDados(@PathVariable Long id,
            @RequestBody PacienteRequestUpdateDTO paciente) {
        return ResponseEntity.ok(pacienteService.atualizarDados(id, paciente));
    }

    @PreAuthorize("hasAuthority('ROLE_RECEPCIONISTA') or hasAuthority('ROLE_ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pacienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("alergias/{id}")
    public ResponseEntity<AlergiaResponseDTO> adicionarAlergia(@PathVariable Long id,
            @Valid @RequestBody AlergiaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pacienteService.adicionarAlergia(id, dto));
    }

    @GetMapping("alergias/{id}")
    public ResponseEntity<List<AlergiaResponseDTO>> listarAlergias(@PathVariable Long id) {

        return ResponseEntity.ok(pacienteService.listarAlergias(id));
    }

    @DeleteMapping("alergias/{idUsuario}/{idAlergia}")
    public ResponseEntity<Void> removerAlergia(@PathVariable Long idUsuario, @PathVariable Long idAlergia) {
        pacienteService.removerAlergia(idUsuario, idAlergia);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("doencasCronicas/{id}")
    public ResponseEntity<DoencaCronicaResponseDTO> adicionarDoencaCronica(@PathVariable Long id,
            @Valid @RequestBody DoencaCronicaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pacienteService.adicionarDoencaCronica(id, dto));
    }

    @GetMapping("doencasCronicas/{id}")
    public ResponseEntity<List<DoencaCronicaResponseDTO>> listarDoencasCronicas(@PathVariable Long id) {
        return ResponseEntity.ok(pacienteService.listarDoencasCronicas(id));
    }

    @DeleteMapping("doencasCronicas/{idUsuario}/{idDoencaCronica}")
    public ResponseEntity<Void> removerDoencaCronica(@PathVariable Long idUsuario, @PathVariable Long idDoencaCronica) {
        pacienteService.removerDoencaCronica(idUsuario, idDoencaCronica);
        return ResponseEntity.noContent().build();
    }
}
