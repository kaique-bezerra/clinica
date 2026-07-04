package clinica_back.clinica_back.features.Consulta.Disponibilidade;

import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import clinica_back.clinica_back.features.Consulta.Disponibilidade.DTOs.HorarioDisponivelDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/disponibilidade")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class DisponibilidadeController {

    private final DisponibilidadeService disponibilidadeService;

    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRADOR') or hasRole('MEDICO')")
    @GetMapping("/medico/{idMedico}/dia/{data}")
    public ResponseEntity<List<HorarioDisponivelDTO>> listar(
            @PathVariable Long idMedico,
            @PathVariable LocalDate data) {

        return ResponseEntity.ok(
                disponibilidadeService
                        .listarHorariosDisponiveis(idMedico, data)
        );
    }
}