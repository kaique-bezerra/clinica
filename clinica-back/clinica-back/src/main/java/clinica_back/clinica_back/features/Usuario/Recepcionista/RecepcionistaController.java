package clinica_back.clinica_back.features.Usuario.Recepcionista;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import clinica_back.clinica_back.features.Usuario.Recepcionista.dto.RecepcionistaRequestDTO;
import clinica_back.clinica_back.features.Usuario.Recepcionista.dto.RecepcionistaRequestUpdateDTO;
import clinica_back.clinica_back.features.Usuario.Recepcionista.dto.RecepcionistaResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/recepcionista")
@RequiredArgsConstructor
public class RecepcionistaController {

        private final RecepcionistaService recepcionistaService;

        @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
        @PostMapping
        public ResponseEntity<RecepcionistaResponseDTO> cadastrar(@Valid @RequestBody RecepcionistaRequestDTO dto) {
                RecepcionistaResponseDTO recepcionista = recepcionistaService.cadastrar(dto);
                return ResponseEntity.status(HttpStatus.CREATED).body(recepcionista);
        }

        @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
        @GetMapping
        public ResponseEntity<List<RecepcionistaResponseDTO>> listarTodos() {
                return ResponseEntity.ok(recepcionistaService.listarTodos());
        }

        @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
        @GetMapping("/{id}")
        public ResponseEntity<RecepcionistaResponseDTO> buscarPorId(@PathVariable Long id) {
                return ResponseEntity.ok(recepcionistaService.buscarPorId(id));
        }

        @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
        @PutMapping("/{id}")
        public ResponseEntity<RecepcionistaResponseDTO> atualizarDados(@PathVariable Long id,
                        @RequestBody RecepcionistaRequestUpdateDTO recepcionista) {
                return ResponseEntity.ok(recepcionistaService.atualizarDados(id, recepcionista));
        }

        @PreAuthorize("hasAuthority('ROLE_ADMINISTRADOR')")
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletar(@PathVariable Long id) {
                recepcionistaService.deletar(id);
                return ResponseEntity.noContent().build();
        }
}