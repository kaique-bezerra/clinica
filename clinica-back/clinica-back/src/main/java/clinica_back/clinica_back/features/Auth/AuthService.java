package clinica_back.clinica_back.features.Auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import clinica_back.clinica_back.features.Auth.dto.LoginRequestDTO;
import clinica_back.clinica_back.features.Auth.dto.LoginResponseDTO;
import clinica_back.clinica_back.features.Usuario.Usuario;
import clinica_back.clinica_back.features.Usuario.UsuarioRepository;
import clinica_back.clinica_back.shared.exceptions.RecursoNaoEncontradoException;
import clinica_back.clinica_back.shared.exceptions.RegraNegocioException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponseDTO fazerLogin(LoginRequestDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Email inválido!"));

        if (!passwordEncoder.matches(dto.getSenha(), usuario.getSenha())) {
            throw new RegraNegocioException("Email ou senha inválida");
        }

        String token = jwtService.gerarToken(usuario);

        return new LoginResponseDTO(token,
                usuario.getPerfil().name(),
                usuario.getIdUsuario(),
                usuario.getNome());
    }

}
