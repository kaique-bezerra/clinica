package clinica_back.clinica_back.features.Usuario.Paciente;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import clinica_back.clinica_back.features.Auditoria.AcaoAuditoriaEnum;
import clinica_back.clinica_back.features.Auditoria.LogAuditoriaService;
import clinica_back.clinica_back.features.Usuario.PerfilUsuario;
import clinica_back.clinica_back.features.Usuario.UsuarioRepository;
import clinica_back.clinica_back.features.Usuario.Endereco.Endereco;
import clinica_back.clinica_back.features.Usuario.Paciente.Convenio.Convenio;
import clinica_back.clinica_back.features.Usuario.Paciente.DadosClinicos.DadosClinicos;
import clinica_back.clinica_back.features.Usuario.Paciente.DadosClinicos.Alergia.Alergia;
import clinica_back.clinica_back.features.Usuario.Paciente.DadosClinicos.Alergia.dto.AlergiaRequestDTO;
import clinica_back.clinica_back.features.Usuario.Paciente.DadosClinicos.DoencaCronica.DoencaCronica;
import clinica_back.clinica_back.features.Usuario.Paciente.DadosClinicos.DoencaCronica.dto.DoencaCronicaRequestDTO;
import clinica_back.clinica_back.features.Usuario.Paciente.dto.PacienteRequestCadastrarDTO;
import clinica_back.clinica_back.features.Usuario.Paciente.dto.PacienteRequestUpdateDTO;
import clinica_back.clinica_back.features.Usuario.Paciente.dto.PacienteResponseDTO;
import clinica_back.clinica_back.shared.exceptions.RecursoNaoEncontradoException;
import clinica_back.clinica_back.shared.exceptions.RegistroDuplicadoException;
import clinica_back.clinica_back.shared.util.AuditoriaUtil;
import clinica_back.clinica_back.shared.util.DataUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final LogAuditoriaService logAuditoriaService;
    private final UsuarioRepository usuarioRepository;
    private final PacienteRepository pacienteRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public PacienteResponseDTO cadastrar(PacienteRequestCadastrarDTO dto) {

        Paciente paciente = new Paciente();

        if (usuarioRepository.existsByCpf(dto.getCpf())) {
            throw new RegistroDuplicadoException("Registro Duplicado!");
        }

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RegistroDuplicadoException("Registro Duplicado!");
        }

        paciente.setNome(dto.getNome());
        paciente.setSobrenome(dto.getSobrenome());
        paciente.setTelefone(dto.getTelefone());
        paciente.setCpf(dto.getCpf());
        paciente.setEmail(dto.getEmail());
        paciente.setSenha(passwordEncoder.encode(dto.getSenha()));
        paciente.setPerfil(PerfilUsuario.PACIENTE);
        paciente.setSexo(dto.getSexo());
        paciente.setProfissao(dto.getProfissao());
        paciente.setDataNascimento(dto.getDataNascimento());
        paciente.setIdade(DataUtil.calcularIdade(dto.getDataNascimento()));

        Endereco endereco = new Endereco();

        endereco.setRua(dto.getRua());
        endereco.setNumero(dto.getNumero());
        endereco.setBairro(dto.getBairro());
        endereco.setCidade(dto.getCidade());
        endereco.setEstado(dto.getEstado());
        endereco.setCep(dto.getCep());

        endereco.setUsuario(paciente);
        paciente.setEndereco(endereco);

        DadosClinicos dadosClinicos = new DadosClinicos();

        dadosClinicos.setTipoSanguineo(dto.getDadosClinicos().getTipoSanguineo());
        dadosClinicos.setAltura(dto.getDadosClinicos().getAltura());
        dadosClinicos.setPeso(dto.getDadosClinicos().getPeso());

        dadosClinicos.setPaciente(paciente);
        paciente.setDadosClinicos(dadosClinicos);

        List<Alergia> alergias = new ArrayList<Alergia>();

        if (dto.getDadosClinicos().getAlergias() != null) {
            for (AlergiaRequestDTO alergiaDTO : dto.getDadosClinicos().getAlergias()) {

                Alergia alergia = new Alergia();

                alergia.setNomeAlergia(alergiaDTO.getNome());
                alergia.setDadosClinicos(dadosClinicos);

                alergias.add(alergia);
            }
        }

        dadosClinicos.setAlergias(alergias);

        List<DoencaCronica> doencasCronicas = new ArrayList<DoencaCronica>();

        if (dto.getDadosClinicos().getDoencasCronicas() != null) {
            for (DoencaCronicaRequestDTO doencaCronicaDTO : dto.getDadosClinicos().getDoencasCronicas()) {

                DoencaCronica doencaCronica = new DoencaCronica();

                doencaCronica.setNomeDoenca(doencaCronicaDTO.getNome());
                doencaCronica.setDadosClinicos(dadosClinicos);

                doencasCronicas.add(doencaCronica);
            }
        }

        dadosClinicos.setDoencasCronicas(doencasCronicas);

        if (dto.getConvenio() != null) {

            Convenio convenio = new Convenio();

            convenio.setPlano(dto.getConvenio().getPlano());
            convenio.setNumero(dto.getConvenio().getNumero());
            convenio.setData(dto.getConvenio().getData());

            convenio.setPaciente(paciente);
            paciente.setConvenio(convenio);
        }

        Paciente pacienteSalvo = pacienteRepository.save(paciente);
        logAuditoriaService.registrar(AcaoAuditoriaEnum.CREATE, "PACIENTE", pacienteSalvo.getIdUsuario(),
                "Cadastrou paciente: " + pacienteSalvo.getNome());

        return new PacienteResponseDTO(
                pacienteSalvo.getIdUsuario(),
                pacienteSalvo.getNome(),
                pacienteSalvo.getSobrenome(),
                paciente.getCpf(),
                pacienteSalvo.getEmail(),
                pacienteSalvo.getTelefone(),
                pacienteSalvo.getProfissao(),
                pacienteSalvo.getDataNascimento(),
                pacienteSalvo.getIdade(),
                pacienteSalvo.getDadosClinicos().getTipoSanguineo(),
                pacienteSalvo.getDadosClinicos().getAltura(),
                pacienteSalvo.getDadosClinicos().getPeso());
    }

    public List<PacienteResponseDTO> listarTodos() {

        List<Paciente> pacientes = pacienteRepository.findAll();
        List<PacienteResponseDTO> resposta = new ArrayList<>();

        for (Paciente paciente : pacientes) {
            resposta.add(new PacienteResponseDTO(
                    paciente.getIdUsuario(),
                    paciente.getNome(),
                    paciente.getCpf(),
                    paciente.getSobrenome(),
                    paciente.getEmail(),
                    paciente.getTelefone(),
                    paciente.getProfissao(),
                    paciente.getDataNascimento(),
                    paciente.getIdade(),
                    paciente.getDadosClinicos().getTipoSanguineo(),
                    paciente.getDadosClinicos().getAltura(),
                    paciente.getDadosClinicos().getPeso()));
        }

        return resposta;
    }

    public PacienteResponseDTO buscarPorId(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente com ID " + id + " não encontrado"));

        return new PacienteResponseDTO(
                paciente.getIdUsuario(),
                paciente.getNome(),
                paciente.getCpf(),
                paciente.getSobrenome(),
                paciente.getEmail(),
                paciente.getTelefone(),
                paciente.getProfissao(),
                paciente.getDataNascimento(),
                paciente.getIdade(),
                paciente.getDadosClinicos().getTipoSanguineo(),
                paciente.getDadosClinicos().getAltura(),
                paciente.getDadosClinicos().getPeso());
    }

    @Transactional
    public PacienteResponseDTO atualizarDados(Long id, PacienteRequestUpdateDTO dto) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente com ID " + id + " não encontrado"));

        if (!paciente.getEmail().equals(dto.getEmail()) && usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RegistroDuplicadoException("Registro Duplicado!");
        }

        paciente.setNome(dto.getNome());
        paciente.setSobrenome(dto.getSobrenome());
        paciente.setTelefone(dto.getTelefone());
        paciente.setEmail(dto.getEmail());
        paciente.setProfissao(dto.getProfissao());
        paciente.setDataNascimento(dto.getDataNascimento());
        paciente.setIdade(DataUtil.calcularIdade(dto.getDataNascimento()));

        Paciente pacienteSalvo = pacienteRepository.save(paciente);

        StringBuilder descricao = new StringBuilder("Atualizou: ");

        descricao.append(AuditoriaUtil.registrarAlteracao("Nome", paciente.getNome(), dto.getNome()));

        descricao.append(AuditoriaUtil.registrarAlteracao("Sobrenome", paciente.getSobrenome(), dto.getSobrenome()));

        descricao.append(AuditoriaUtil.registrarAlteracao("Telefone", paciente.getTelefone(), dto.getTelefone()));

        descricao.append(AuditoriaUtil.registrarAlteracao("Email", paciente.getEmail(), dto.getEmail()));

        descricao.append(AuditoriaUtil.registrarAlteracao("Profissão", paciente.getProfissao(), dto.getProfissao()));

        descricao.append(AuditoriaUtil.registrarAlteracao("Data de nascimento", paciente.getDataNascimento(),
                dto.getDataNascimento()));

        logAuditoriaService.registrar(AcaoAuditoriaEnum.UPDATE, "PACIENTE", pacienteSalvo.getIdUsuario(),
                descricao.toString());

        return new PacienteResponseDTO(
                pacienteSalvo.getIdUsuario(),
                pacienteSalvo.getNome(),
                paciente.getCpf(),
                pacienteSalvo.getSobrenome(),
                pacienteSalvo.getEmail(),
                pacienteSalvo.getTelefone(),
                pacienteSalvo.getProfissao(),
                pacienteSalvo.getDataNascimento(),
                pacienteSalvo.getIdade(),
                pacienteSalvo.getDadosClinicos().getTipoSanguineo(),
                pacienteSalvo.getDadosClinicos().getAltura(),
                pacienteSalvo.getDadosClinicos().getPeso());
    }

    @Transactional
    public void deletar(Long id) {
        Paciente paciente = pacienteRepository.findById(id).orElseThrow(
                () -> new RecursoNaoEncontradoException("Paciente com ID " + id + " não encontrado!"));
        logAuditoriaService.registrar(AcaoAuditoriaEnum.DELETE, "PACIENTE", paciente.getIdUsuario(),
                "Deletou o paciente: " + paciente.getNome() + " " + paciente.getSobrenome() + "| CPF: "
                        + paciente.getCpf());
        pacienteRepository.delete(paciente);
    }
}