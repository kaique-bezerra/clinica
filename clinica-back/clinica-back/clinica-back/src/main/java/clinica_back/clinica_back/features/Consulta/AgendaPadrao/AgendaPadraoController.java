package clinica_back.clinica_back.features.Consulta.AgendaPadrao;

import clinica_back.clinica_back.features.Consulta.AgendaPadrao.DTOs.AgendaPadraoRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agendas")
@RequiredArgsConstructor
public class AgendaPadraoController {

    private final AgendaPadraoService agendaService;

    @PostMapping
    public ResponseEntity<AgendaPadrao> cadastrar(
            @RequestBody @Valid AgendaPadraoRequestDTO dto) {

        AgendaPadrao agenda = agendaService.cadastrar(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(agenda);
    }
}
