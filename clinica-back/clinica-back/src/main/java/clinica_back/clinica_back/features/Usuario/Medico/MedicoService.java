package clinica_back.clinica_back.features.Usuario.Medico;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import clinica_back.clinica_back.features.Usuario.PerfilUsuario;
import clinica_back.clinica_back.features.Usuario.UsuarioRepository;
import clinica_back.clinica_back.features.Usuario.Endereco.Endereco;
import clinica_back.clinica_back.features.Usuario.Medico.dto.MedicoRequestCadastroDTO;
import clinica_back.clinica_back.features.Usuario.Medico.dto.MedicoRequestUpdateDTO;
import clinica_back.clinica_back.features.Usuario.Medico.dto.MedicoResponseDTO;
import clinica_back.clinica_back.shared.exceptions.RecursoNaoEncontradoException;
import clinica_back.clinica_back.shared.exceptions.RegraNegocioException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicoService {

    private final UsuarioRepository usuarioRepository;
    private final MedicoRepository medicoRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MedicoResponseDTO cadastrar(MedicoRequestCadastroDTO dto) {

        Medico medico = new Medico();

        if (usuarioRepository.existsByCpf(dto.getCpf())) {
            throw new RegraNegocioException("CPF já cadastrado!");
        }

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RegraNegocioException("Email já cadastrado!");
        }

        if (medicoRepository.existsByCrm(dto.getCrm())) {
            throw new RecursoNaoEncontradoException("CRM já cadastrado!");
        }

        medico.setNome(dto.getNome());
        medico.setSobrenome(dto.getSobrenome());
        medico.setTelefone(dto.getTelefone());
        medico.setCpf(dto.getCpf());
        medico.setEmail(dto.getEmail());
        medico.setSenha(passwordEncoder.encode(dto.getSenha()));
        medico.setPerfil(PerfilUsuario.MEDICO);
        medico.setStatus(StatusMedico.ATIVO);
        medico.setCrm(dto.getCrm());
        medico.setEspecialidade(dto.getEspecialidade());

        Endereco endereco = new Endereco();

        endereco.setRua(dto.getRua());
        endereco.setNumero(dto.getNumero());
        endereco.setBairro(dto.getBairro());
        endereco.setCidade(dto.getCidade());
        endereco.setEstado(dto.getEstado());
        endereco.setCep(dto.getCep());

        endereco.setUsuario(medico);
        medico.setEndereco(endereco);

        Medico medicoSalvo = medicoRepository.save(medico);

        return new MedicoResponseDTO(
                medicoSalvo.getIdUsuario(),
                medicoSalvo.getNome(),
                medicoSalvo.getSobrenome(),
                medicoSalvo.getEmail(),
                medicoSalvo.getTelefone(),
                medicoSalvo.getCrm(),
                medicoSalvo.getEspecialidade(),
                medicoSalvo.getStatus());
    }

    public List<MedicoResponseDTO> listarTodos() {

        List<Medico> medicos = medicoRepository.findAll();
        List<MedicoResponseDTO> resposta = new ArrayList<>();

        for (Medico medico : medicos) {
            resposta.add(new MedicoResponseDTO(
                    medico.getIdUsuario(),
                    medico.getNome(),
                    medico.getSobrenome(),
                    medico.getEmail(),
                    medico.getTelefone(),
                    medico.getCrm(),
                    medico.getEspecialidade(),
                    medico.getStatus()));
        }

        return resposta;
    }

    public MedicoResponseDTO buscarPorId(Long id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Médico com ID " + id + " não encontrado"));

        return new MedicoResponseDTO(
                medico.getIdUsuario(),
                medico.getNome(),
                medico.getSobrenome(),
                medico.getEmail(),
                medico.getTelefone(),
                medico.getCrm(),
                medico.getEspecialidade(),
                medico.getStatus());
    }

    @Transactional
    public MedicoResponseDTO atualizarDados(Long id, MedicoRequestUpdateDTO dto) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Médico com ID " + id + " não encontrado"));

        if (!medico.getEmail().equals(dto.getEmail()) && usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RegraNegocioException("Email já cadastrado!");
        }

        if (!medico.getCpf().equals(dto.getCpf()) && usuarioRepository.existsByCpf(dto.getCpf())) {
            throw new RegraNegocioException("CPF já cadastrado!");
        }

        if (!medico.getCrm().equals(dto.getCrm()) && medicoRepository.existsByCrm(dto.getCrm())) {
            throw new RegraNegocioException("CRM já cadastrado!");

        }

        medico.setNome(dto.getNome());
        medico.setSobrenome(dto.getSobrenome());
        medico.setTelefone(dto.getTelefone());
        medico.setCpf(dto.getCpf());
        medico.setEmail(dto.getEmail());
        medico.setCrm(dto.getCrm());
        medico.setEspecialidade(dto.getEspecialidade());

        Medico medicoSalvo = medicoRepository.save(medico);

        return new MedicoResponseDTO(
                medicoSalvo.getIdUsuario(), medicoSalvo.getNome(), medicoSalvo.getSobrenome(), medicoSalvo.getEmail(),
                medicoSalvo.getTelefone(),
                medicoSalvo.getCrm(), medicoSalvo.getEspecialidade(), medicoSalvo.getStatus());
    }

    @Transactional
    public void deletar(Long id) {
        Medico medico = medicoRepository.findById(id).orElseThrow(
                () -> new RecursoNaoEncontradoException("Medico com ID " + id + " não encontrado!"));
        medicoRepository.delete(medico);
    }

    @Transactional
    public void inativar(Long id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Medico com ID " + id + " não encontrado!"));

        if (!medico.getStatus().equals(StatusMedico.ATIVO)) {
            throw new RegraNegocioException("O médico já está INATIVO!");
        }

        medico.setStatus(StatusMedico.INATIVO);
    }

    @Transactional
    public void ativar(Long id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Medico com ID " + id + " não encontrado!"));

        if (!medico.getStatus().equals(StatusMedico.INATIVO)) {
            throw new RegraNegocioException("O médico já está ATIVO!");
        }

        medico.setStatus(StatusMedico.ATIVO);
    }
}
