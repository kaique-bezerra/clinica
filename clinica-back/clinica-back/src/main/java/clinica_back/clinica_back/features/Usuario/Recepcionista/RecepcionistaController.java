package clinica_back.clinica_back.features.Usuario.Recepcionista;

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

import clinica_back.clinica_back.features.Usuario.Recepcionista.dto.RecepcionistaRequestDTO;
import clinica_back.clinica_back.features.Usuario.Recepcionista.dto.RecepcionistaRequestUpdateDTO;
import clinica_back.clinica_back.features.Usuario.Recepcionista.dto.RecepcionistaResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/recepcionista")
@RequiredArgsConstructor
public class RecepcionistaController {

        private final RecepcionistaService recepcionistaService;

        @PostMapping
        public ResponseEntity<RecepcionistaResponseDTO> cadastrar(@Valid @RequestBody RecepcionistaRequestDTO dto) {

                RecepcionistaResponseDTO recepcionista = recepcionistaService.cadastrar(dto);

                return ResponseEntity.status(HttpStatus.CREATED).body(recepcionista);
        }

        @GetMapping
        public ResponseEntity<List<RecepcionistaResponseDTO>> listarTodos() {
                return ResponseEntity.ok(recepcionistaService.listarTodos());
        }

        @GetMapping("/{id}")
        public ResponseEntity<RecepcionistaResponseDTO> buscarPorId(@PathVariable Long id) {
                return ResponseEntity.ok(recepcionistaService.buscarPorId(id));
        }

        @PutMapping("/{id}")
        public ResponseEntity<RecepcionistaResponseDTO> atualizarDados(@PathVariable Long id,
                        @RequestBody RecepcionistaRequestUpdateDTO recepcionista) {
                return ResponseEntity.ok(recepcionistaService.atualizarDados(id, recepcionista));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletar(@PathVariable Long id) {
                recepcionistaService.deletar(id);
                return ResponseEntity.noContent().build();
        }
}