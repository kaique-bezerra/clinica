package clinica_back.clinica_back.features.Usuario.Recepcionista;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import clinica_back.clinica_back.features.Usuario.PerfilUsuario;
import clinica_back.clinica_back.features.Usuario.UsuarioRepository;
import clinica_back.clinica_back.features.Usuario.Endereco.Endereco;
import clinica_back.clinica_back.features.Usuario.Recepcionista.dto.RecepcionistaRequestDTO;
import clinica_back.clinica_back.features.Usuario.Recepcionista.dto.RecepcionistaRequestUpdateDTO;
import clinica_back.clinica_back.features.Usuario.Recepcionista.dto.RecepcionistaResponseDTO;
import clinica_back.clinica_back.shared.exceptions.RecursoNaoEncontradoException;
import clinica_back.clinica_back.shared.exceptions.RegraNegocioException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecepcionistaService {

    private final UsuarioRepository usuarioRepository;
    private final RecepcionistaRepository recepcionistaRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public RecepcionistaResponseDTO cadastrar(RecepcionistaRequestDTO dto) {

        Recepcionista recepcionista = new Recepcionista();

        if (usuarioRepository.existsByCpf(dto.getCpf())) {
            throw new RegraNegocioException("CPF já cadastrado!");
        }

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RegraNegocioException("Email já cadastrado!");
        }

        recepcionista.setNome(dto.getNome());
        recepcionista.setSobrenome(dto.getSobrenome());
        recepcionista.setEmail(dto.getEmail());
        recepcionista.setTelefone(dto.getTelefone());
        recepcionista.setCpf(dto.getCpf());

        recepcionista.setSenha(
                passwordEncoder.encode(dto.getSenha()));

        recepcionista.setPerfil(PerfilUsuario.RECEPCIONISTA);

        Endereco endereco = new Endereco();

        endereco.setRua(dto.getRua());
        endereco.setNumero(dto.getNumero());
        endereco.setBairro(dto.getBairro());
        endereco.setCidade(dto.getCidade());
        endereco.setEstado(dto.getEstado());
        endereco.setCep(dto.getCep());

        endereco.setUsuario(recepcionista);
        recepcionista.setEndereco(endereco);

        Recepcionista recepcionistaSalva = recepcionistaRepository.save(recepcionista);

        return new RecepcionistaResponseDTO(
                recepcionistaSalva.getIdUsuario(),
                recepcionistaSalva.getNome(),
                recepcionistaSalva.getSobrenome(),
                recepcionistaSalva.getEmail(),
                recepcionistaSalva.getTelefone(),
                recepcionista.getCpf());
    }

    public List<RecepcionistaResponseDTO> listarTodos() {

        List<Recepcionista> recepcionistas = recepcionistaRepository.findAll();
        List<RecepcionistaResponseDTO> resposta = new ArrayList<>();

        for (Recepcionista recepcionista : recepcionistas) {
            resposta.add(new RecepcionistaResponseDTO(
                    recepcionista.getIdUsuario(),
                    recepcionista.getNome(),
                    recepcionista.getSobrenome(),
                    recepcionista.getEmail(),
                    recepcionista.getTelefone(),
                    recepcionista.getCpf()));

        }

        return resposta;
    }

    public RecepcionistaResponseDTO buscarPorId(Long id) {
        Recepcionista recepcionista = recepcionistaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Recepcionista com ID " + id + " não encontrado"));

        return new RecepcionistaResponseDTO(
                recepcionista.getIdUsuario(),
                recepcionista.getNome(),
                recepcionista.getSobrenome(),
                recepcionista.getEmail(),
                recepcionista.getTelefone(),
                recepcionista.getCpf());

    }

    @Transactional
    public RecepcionistaResponseDTO atualizarDados(Long id, RecepcionistaRequestUpdateDTO dto) {
        Recepcionista recepcionista = recepcionistaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Recepcionista com ID " + id + " não encontrado"));

        if (!recepcionista.getEmail().equals(dto.getEmail()) && usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RegraNegocioException("Email já cadastrado!");
        }

        if (!recepcionista.getCpf().equals(dto.getCpf()) && usuarioRepository.existsByCpf(dto.getCpf())) {
            throw new RegraNegocioException("CPF já cadastrado!");
        }

        recepcionista.setNome(dto.getNome());
        recepcionista.setSobrenome(dto.getSobrenome());
        recepcionista.setTelefone(dto.getTelefone());
        recepcionista.setCpf(dto.getCpf());
        recepcionista.setEmail(dto.getEmail());

        Recepcionista recepcionistaSalvo = recepcionistaRepository.save(recepcionista);

        return new RecepcionistaResponseDTO(
                recepcionistaSalvo.getIdUsuario(), recepcionistaSalvo.getNome(), recepcionistaSalvo.getSobrenome(),
                recepcionistaSalvo.getEmail(),
                recepcionistaSalvo.getTelefone(),
                recepcionista.getCpf());

    }

    @Transactional
    public void deletar(Long id) {
        Recepcionista recepcionista = recepcionistaRepository.findById(id).orElseThrow(
                () -> new RecursoNaoEncontradoException("Recepcionista com ID " + id + " não encontrado!"));
        recepcionistaRepository.delete(recepcionista);
    }

}