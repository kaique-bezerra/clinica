package clinica_back.clinica_back.features.Usuario;

import org.springframework.stereotype.Service;

import clinica_back.clinica_back.features.Auditoria.AcaoAuditoriaEnum;
import clinica_back.clinica_back.features.Auditoria.LogAuditoriaService;
import clinica_back.clinica_back.features.Usuario.Endereco.Endereco;
import clinica_back.clinica_back.features.Usuario.Endereco.EnderecoRepository;
import clinica_back.clinica_back.features.Usuario.Endereco.dto.EnderecoRequestDTO;
import clinica_back.clinica_back.features.Usuario.Endereco.dto.EnderecoResponseDTO;
import clinica_back.clinica_back.features.Usuario.dto.UsuarioResponseDTO;
import clinica_back.clinica_back.shared.exceptions.RecursoNaoEncontradoException;
import clinica_back.clinica_back.shared.util.AuditoriaUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

        private final EnderecoRepository enderecoRepository;
        private final LogAuditoriaService logAuditoriaService;
        private final UsuarioRepository usuarioRepository;

        public UsuarioResponseDTO buscarPorId(Long id) {
                Usuario usuario = usuarioRepository.findById(id)
                                .orElseThrow(() -> new RecursoNaoEncontradoException(
                                                "Usuário com ID " + id + " não encontrado!"));

                return new UsuarioResponseDTO(
                                usuario.getIdUsuario(),
                                usuario.getNome(),
                                usuario.getSobrenome(),
                                usuario.getEmail(),
                                usuario.getTelefone(),
                                usuario.getPerfil());
        }

        @Transactional
        public EnderecoResponseDTO atualizarEndereco(Long idUsuario, EnderecoRequestDTO dto) {
                Usuario usuario = usuarioRepository.findById(idUsuario)
                                .orElseThrow(
                                                () -> new RecursoNaoEncontradoException(
                                                                "Usuário com ID " + idUsuario + " não encontrado"));

                Endereco endereco = usuario.getEndereco();

                StringBuilder descricao = new StringBuilder("Atualizou endereço: ");

                descricao.append(AuditoriaUtil.registrarAlteracao("Rua", endereco.getRua(), dto.getRua()));

                descricao.append(AuditoriaUtil.registrarAlteracao("Número", endereco.getNumero(), dto.getNumero()));

                descricao.append(AuditoriaUtil.registrarAlteracao("Bairro", endereco.getBairro(), dto.getBairro()));

                descricao.append(AuditoriaUtil.registrarAlteracao("Cidade", endereco.getCidade(), dto.getCidade()));

                descricao.append(AuditoriaUtil.registrarAlteracao("Estado", endereco.getEstado(), dto.getEstado()));

                descricao.append(AuditoriaUtil.registrarAlteracao("CEP", endereco.getCep(), dto.getCep()));

                endereco.setRua(dto.getRua());
                endereco.setNumero(dto.getNumero());
                endereco.setBairro(dto.getBairro());
                endereco.setCidade(dto.getCidade());
                endereco.setEstado(dto.getEstado());
                endereco.setCep(dto.getCep());

                Endereco enderecoSalvo = enderecoRepository.save(endereco);

                logAuditoriaService.registrar(AcaoAuditoriaEnum.UPDATE, "ENDERECO", enderecoSalvo.getId(),
                                descricao.toString());

                return new EnderecoResponseDTO(
                                enderecoSalvo.getRua(),
                                enderecoSalvo.getNumero(),
                                enderecoSalvo.getBairro(),
                                enderecoSalvo.getCidade(),
                                enderecoSalvo.getEstado(),
                                enderecoSalvo.getCep());
        }

        public EnderecoResponseDTO listarEndereco(Long idUsuario) {

                Usuario usuario = usuarioRepository.findById(idUsuario)
                                .orElseThrow(() -> new RecursoNaoEncontradoException(
                                                "Usuário com ID " + idUsuario + " não encontrado."));
                Endereco endereco = usuario.getEndereco();

                if (endereco == null) {
                        throw new RecursoNaoEncontradoException("Endereço não encontrado.");
                }

                return new EnderecoResponseDTO(
                                endereco.getRua(),
                                endereco.getNumero(),
                                endereco.getBairro(),
                                endereco.getCidade(),
                                endereco.getEstado(),
                                endereco.getCep());
        }
}
