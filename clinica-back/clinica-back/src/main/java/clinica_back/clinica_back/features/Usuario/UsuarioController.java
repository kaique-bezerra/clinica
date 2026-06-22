package clinica_back.clinica_back.features.Usuario;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clinica_back.clinica_back.features.Usuario.Endereco.dto.EnderecoRequestDTO;
import clinica_back.clinica_back.features.Usuario.Endereco.dto.EnderecoResponseDTO;
import clinica_back.clinica_back.features.Usuario.dto.UsuarioResponseDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }
    @PreAuthorize("hasAuthority('ROLE_RECEPCIONISTA') or hasAuthority('ROLE_ADMINISTRADOR')")
    @PutMapping("/{idUsuario}/atualizarEndereco")
    public ResponseEntity<EnderecoResponseDTO> atualizarEndereco(@PathVariable Long idUsuario,
            @RequestBody EnderecoRequestDTO endereco) {
        return ResponseEntity.ok(usuarioService.atualizarEndereco(idUsuario, endereco));
    }
}
