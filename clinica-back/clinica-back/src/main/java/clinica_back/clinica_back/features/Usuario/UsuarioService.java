package clinica_back.clinica_back.features.Usuario;

import org.springframework.stereotype.Service;

import clinica_back.clinica_back.features.Usuario.dto.UsuarioResponseDTO;
import clinica_back.clinica_back.shared.exceptions.RecursoNaoEncontradoException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    // Não precisa de cadastro já que vamos chamar pelas classes filha

    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário com ID " + id + " não encontrado!"));

        return new UsuarioResponseDTO(
                usuario.getIdUsuario(),
                usuario.getNome(),
                usuario.getSobrenome(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getPerfil());
    }
}
