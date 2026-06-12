package clinica_back.clinica_back.features.Usuario.Recepcionista;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recepcionistas")
@RequiredArgsConstructor
public class RecepcionistaController {

    private final RecepcionistaService recepcionistaService;

    @PostMapping
    public ResponseEntity<Recepcionista> cadastrar(
            @RequestBody RecepcionistaRequestDTO dto) {

        Recepcionista recepcionista =
                recepcionistaService.cadastrar(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(recepcionista);
    }

    @GetMapping
    public ResponseEntity<List<Recepcionista>> listarRecepcionistas() {
        return ResponseEntity.ok(
                recepcionistaService.listarTodos()
        );
    }
}