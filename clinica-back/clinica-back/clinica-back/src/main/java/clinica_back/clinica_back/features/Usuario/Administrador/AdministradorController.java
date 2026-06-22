package clinica_back.clinica_back.features.Usuario.Administrador;

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

import clinica_back.clinica_back.features.Usuario.Administrador.dto.AdministradorRequestDTO;
import clinica_back.clinica_back.features.Usuario.Administrador.dto.AdministradorRequestUpdateDTO;
import clinica_back.clinica_back.features.Usuario.Administrador.dto.AdministradorResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/administrador")
@RequiredArgsConstructor
public class AdministradorController {

    private final AdministradorService administradorService;

    @PostMapping
    public ResponseEntity<AdministradorResponseDTO> cadastrar(@Valid @RequestBody AdministradorRequestDTO dto) {
        AdministradorResponseDTO administrador = administradorService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(administrador);
    }

    @GetMapping
    public ResponseEntity<List<AdministradorResponseDTO>> listarTodos() {
        return ResponseEntity.ok(administradorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdministradorResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(administradorService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdministradorResponseDTO> atualizarDados(@PathVariable Long id,
            @RequestBody AdministradorRequestUpdateDTO administrador) {
        return ResponseEntity.ok(administradorService.atualizarDados(id, administrador));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        administradorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
