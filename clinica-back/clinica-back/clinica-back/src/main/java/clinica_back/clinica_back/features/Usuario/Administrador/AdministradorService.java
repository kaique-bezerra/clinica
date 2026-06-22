package clinica_back.clinica_back.features.Usuario.Administrador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import clinica_back.clinica_back.features.Usuario.PerfilUsuario;
import clinica_back.clinica_back.features.Usuario.UsuarioRepository;
import clinica_back.clinica_back.features.Usuario.Administrador.dto.AdministradorRequestDTO;
import clinica_back.clinica_back.features.Usuario.Administrador.dto.AdministradorRequestUpdateDTO;
import clinica_back.clinica_back.features.Usuario.Administrador.dto.AdministradorResponseDTO;
import clinica_back.clinica_back.features.Usuario.Endereco.Endereco;
import clinica_back.clinica_back.shared.exceptions.RecursoNaoEncontradoException;
import clinica_back.clinica_back.shared.exceptions.RegraNegocioException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdministradorService {

    private final UsuarioRepository usuarioRepository;
    private final AdministradorRepository administradorRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AdministradorResponseDTO cadastrar(AdministradorRequestDTO dto) {

        Administrador administrador = new Administrador();

        if (usuarioRepository.existsByCpf(dto.getCpf())) {
            throw new RegraNegocioException("CPF já cadastrado!");
        }

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RegraNegocioException("Email já cadastrado!");
        }

        administrador.setNome(dto.getNome());
        administrador.setSobrenome(dto.getSobrenome());
        administrador.setTelefone(dto.getTelefone());
        administrador.setCpf(dto.getCpf());
        administrador.setEmail(dto.getEmail());
        administrador.setSenha(passwordEncoder.encode(dto.getSenha()));
        administrador.setPerfil(PerfilUsuario.ADMINISTRADOR);

        Endereco endereco = new Endereco();

        endereco.setRua(dto.getRua());
        endereco.setNumero(dto.getNumero());
        endereco.setBairro(dto.getBairro());
        endereco.setCidade(dto.getCidade());
        endereco.setEstado(dto.getEstado());
        endereco.setCep(dto.getCep());

        endereco.setUsuario(administrador);
        administrador.setEndereco(endereco);

        Administrador administradorSalvo = administradorRepository.save(administrador);

        return new AdministradorResponseDTO(
                administradorSalvo.getIdUsuario(),
                administradorSalvo.getNome(),
                administradorSalvo.getSobrenome(),
                administradorSalvo.getEmail(),
                administradorSalvo.getTelefone());
    }

    public List<AdministradorResponseDTO> listarTodos() {

        List<Administrador> administradors = administradorRepository.findAll();
        List<AdministradorResponseDTO> resposta = new ArrayList<>();

        for (Administrador administrador : administradors) {
            resposta.add(new AdministradorResponseDTO(
                    administrador.getIdUsuario(),
                    administrador.getNome(),
                    administrador.getSobrenome(),
                    administrador.getEmail(),
                    administrador.getTelefone()));
        }

        return resposta;
    }

    public AdministradorResponseDTO buscarPorId(Long id) {
        Administrador administrador = administradorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Administrador com ID " + id + " não encontrado"));

        return new AdministradorResponseDTO(
                administrador.getIdUsuario(),
                administrador.getNome(),
                administrador.getSobrenome(),
                administrador.getEmail(),
                administrador.getTelefone());
    }

    @Transactional
    public AdministradorResponseDTO atualizarDados(Long id, AdministradorRequestUpdateDTO dto) {
        Administrador administrador = administradorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Administrador com ID " + id + " não encontrado"));

        if (!administrador.getEmail().equals(dto.getEmail()) && usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RegraNegocioException("Email já cadastrado!");
        }

        if (!administrador.getCpf().equals(dto.getCpf()) && usuarioRepository.existsByCpf(dto.getCpf())) {
            throw new RegraNegocioException("CPF já cadastrado!");
        }

        administrador.setNome(dto.getNome());
        administrador.setSobrenome(dto.getSobrenome());
        administrador.setTelefone(dto.getTelefone());
        administrador.setCpf(dto.getCpf());
        administrador.setEmail(dto.getEmail());

        Administrador administradorSalvo = administradorRepository.save(administrador);

        return new AdministradorResponseDTO(
                administradorSalvo.getIdUsuario(), administradorSalvo.getNome(), administradorSalvo.getSobrenome(),
                administradorSalvo.getEmail(),
                administradorSalvo.getTelefone());
    }

    @Transactional
    public void deletar(Long id) {
        Administrador administrador = administradorRepository.findById(id).orElseThrow(
                () -> new RecursoNaoEncontradoException("Administrador com ID " + id + " não encontrado!"));
        administradorRepository.delete(administrador);
    }

}
