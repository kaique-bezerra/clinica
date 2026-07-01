package clinica_back.clinica_back.features.Usuario.Medico;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import clinica_back.clinica_back.features.Auditoria.AcaoAuditoriaEnum;
import clinica_back.clinica_back.features.Auditoria.LogAuditoriaService;
import clinica_back.clinica_back.features.Usuario.PerfilUsuario;
import clinica_back.clinica_back.features.Usuario.UsuarioRepository;
import clinica_back.clinica_back.features.Usuario.Endereco.Endereco;
import clinica_back.clinica_back.features.Usuario.Medico.dto.MedicoRequestCadastroDTO;
import clinica_back.clinica_back.features.Usuario.Medico.dto.MedicoRequestUpdateDTO;
import clinica_back.clinica_back.features.Usuario.Medico.dto.MedicoResponseDTO;
import clinica_back.clinica_back.shared.exceptions.RecursoNaoEncontradoException;
import clinica_back.clinica_back.shared.exceptions.RegraNegocioException;
import clinica_back.clinica_back.shared.util.AuditoriaUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicoService {

    private final LogAuditoriaService logAuditoriaService;
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
            throw new RegraNegocioException("Em0ail já cadastrado!");
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

        logAuditoriaService.registrar(AcaoAuditoriaEnum.CREATE, "Medico", medicoSalvo.getIdUsuario(),
                "Cadastrou médico: " + medicoSalvo.getNome() + " " + medicoSalvo.getSobrenome() + " | CPF: "
                        + medicoSalvo.getCpf());

        return new MedicoResponseDTO(
                medicoSalvo.getIdUsuario(),
                medicoSalvo.getNome(),
                medicoSalvo.getSobrenome(),
                medicoSalvo.getEmail(),
                medicoSalvo.getTelefone(),
                medicoSalvo.getCpf(),
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
                    medico.getCpf(),
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
                medico.getCpf(),
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

        medico.setNome(dto.getNome());
        medico.setSobrenome(dto.getSobrenome());
        medico.setTelefone(dto.getTelefone());
        medico.setEmail(dto.getEmail());
        medico.setEspecialidade(dto.getEspecialidade());

        Medico medicoSalvo = medicoRepository.save(medico);

        StringBuilder descricao = new StringBuilder("Atualizou: ");

        descricao.append(AuditoriaUtil.registrarAlteracao("Nome", medico.getNome(), dto.getNome()));

        descricao.append(AuditoriaUtil.registrarAlteracao("Sobrenome", medico.getSobrenome(), dto.getSobrenome()));

        descricao.append(AuditoriaUtil.registrarAlteracao("Telefone", medico.getTelefone(), dto.getTelefone()));

        descricao.append(AuditoriaUtil.registrarAlteracao("Email", medico.getEmail(), dto.getEmail()));

        descricao.append(
                AuditoriaUtil.registrarAlteracao("Especialidade", medico.getEspecialidade(), dto.getEspecialidade()));

        logAuditoriaService.registrar(AcaoAuditoriaEnum.UPDATE, "MEDICO", medicoSalvo.getIdUsuario(),
                descricao.toString());

        return new MedicoResponseDTO(
                medicoSalvo.getIdUsuario(),
                medicoSalvo.getNome(),
                medicoSalvo.getSobrenome(),
                medicoSalvo.getEmail(),
                medicoSalvo.getTelefone(),
                medicoSalvo.getCpf(),
                medicoSalvo.getCrm(),
                medicoSalvo.getEspecialidade(),
                medicoSalvo.getStatus());
    }

    @Transactional
    public void deletar(Long id) {
        Medico medico = medicoRepository.findById(id).orElseThrow(
                () -> new RecursoNaoEncontradoException("Medico com ID " + id + " não encontrado!"));

        logAuditoriaService.registrar(AcaoAuditoriaEnum.DELETE, "Medico", medico.getIdUsuario(),
                "Deletou médico: " + medico.getNome() + " " + medico.getSobrenome() + " | CPF: "
                        + medico.getCpf());
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
        logAuditoriaService.registrar(AcaoAuditoriaEnum.INATIVAR, "Medico", medico.getIdUsuario(),
                "Inativou médico: " + medico.getNome() + " " + medico.getSobrenome() + " | CPF: "
                        + medico.getCpf());
    }

    @Transactional
    public void ativar(Long id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Medico com ID " + id + " não encontrado!"));

        if (!medico.getStatus().equals(StatusMedico.INATIVO)) {
            throw new RegraNegocioException("O médico já está ATIVO!");
        }

        medico.setStatus(StatusMedico.ATIVO);
        logAuditoriaService.registrar(AcaoAuditoriaEnum.ATIVAR, "Medico", medico.getIdUsuario(),
                "Ativou médico: " + medico.getNome() + " " + medico.getSobrenome() + " | CPF: "
                        + medico.getCpf());
    }
}
