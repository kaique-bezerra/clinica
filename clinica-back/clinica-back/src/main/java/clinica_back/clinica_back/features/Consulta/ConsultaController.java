package clinica_back.clinica_back.features.Consulta;

import clinica_back.clinica_back.features.Consulta.DTOs.ConsultaRequestDTO;
import clinica_back.clinica_back.features.Consulta.DTOs.ConsultaResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaService consultaService;

    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRADOR') or hasRole('MEDICO') or hasRole('PACIENTE')")
    @PostMapping
    public ResponseEntity<Consulta> cadastrar(
            @RequestBody @Valid ConsultaRequestDTO dto) {

        Consulta consulta = consultaService.cadastrar(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(consulta);
    }

    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRADOR') or hasRole('MEDICO')")
    @GetMapping
    public ResponseEntity<List<ConsultaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(consultaService.listarTodas());
    }

    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRADOR') or hasRole('MEDICO')")
    @GetMapping("/medico/{idMedico}")
    public ResponseEntity<List<ConsultaResponseDTO>> listarPorMedico(
            @PathVariable Long idMedico) {
        return ResponseEntity.ok(consultaService.listarPorMedico(idMedico));
    }

    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRADOR') or hasRole('MEDICO')")
    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<List<ConsultaResponseDTO>> listarPorPaciente(
            @PathVariable Long idPaciente) {
        return ResponseEntity.ok(consultaService.listarPorPaciente(idPaciente));
    }

    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRADOR') or hasRole('MEDICO')")
    @GetMapping("/dia")
    public ResponseEntity<List<ConsultaResponseDTO>> listarPorDia(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate data) {

        LocalDate dataBusca = (data != null) ? data : LocalDate.now();

        return ResponseEntity.ok(
                consultaService.listarPorDia(dataBusca)
        );
    }

    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRADOR') or hasRole('MEDICO')")
    @GetMapping("/semana")
    public ResponseEntity<List<ConsultaResponseDTO>> listarPorSemana() {
        return ResponseEntity.ok(
                consultaService.listarPorSemanaAtual()
        );
    }

}