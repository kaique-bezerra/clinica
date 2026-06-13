package clinica_back.clinica_back.features.Usuario.Administrador;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clinica_back.clinica_back.features.Usuario.Administrador.dto.AdministradorRequestDTO;
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

}
