package clinica_back.clinica_back.features.Usuario;

import org.springframework.stereotype.Service;

import clinica_back.clinica_back.features.Usuario.Endereco.Endereco;
import clinica_back.clinica_back.features.Usuario.Endereco.dto.EnderecoRequestDTO;
import clinica_back.clinica_back.features.Usuario.Endereco.dto.EnderecoResponseDTO;
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

    public EnderecoResponseDTO atualizarEndereco(Long idUsuario, EnderecoRequestDTO dto) {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(
                        () -> new RecursoNaoEncontradoException("Usuário com ID " + idUsuario + " não encontrado!"));

        Endereco endereco = usuario.getEndereco();

        endereco.setRua(dto.getRua());
        endereco.setNumero(dto.getNumero());
        endereco.setBairro(dto.getBairro());
        endereco.setCidade(dto.getCidade());
        endereco.setEstado(dto.getEstado());
        endereco.setCep(dto.getCep());

        usuarioRepository.save(usuario);

        return new EnderecoResponseDTO(endereco.getRua(), endereco.getNumero(), endereco.getBairro(),
                endereco.getCidade(), endereco.getEstado(), endereco.getCep());
    }
}
