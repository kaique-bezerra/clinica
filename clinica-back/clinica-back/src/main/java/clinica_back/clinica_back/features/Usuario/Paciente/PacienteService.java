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
import clinica_back.clinica_back.features.Usuario.Paciente.DadosClinicos.Alergia.AlergiaRepository;
import clinica_back.clinica_back.features.Usuario.Paciente.DadosClinicos.Alergia.dto.AlergiaRequestDTO;
import clinica_back.clinica_back.features.Usuario.Paciente.DadosClinicos.Alergia.dto.AlergiaResponseDTO;
import clinica_back.clinica_back.features.Usuario.Paciente.DadosClinicos.DoencaCronica.DoencaCronica;
import clinica_back.clinica_back.features.Usuario.Paciente.DadosClinicos.DoencaCronica.DoencaCronicaRepository;
import clinica_back.clinica_back.features.Usuario.Paciente.DadosClinicos.DoencaCronica.dto.DoencaCronicaRequestDTO;
import clinica_back.clinica_back.features.Usuario.Paciente.DadosClinicos.DoencaCronica.dto.DoencaCronicaResponseDTO;
import clinica_back.clinica_back.features.Usuario.Paciente.DadosClinicos.dto.dto.DadosClinicosResponseDTO;
import clinica_back.clinica_back.features.Usuario.Paciente.dto.PacienteCompletoResponseDTO;
import clinica_back.clinica_back.features.Usuario.Paciente.dto.PacienteRequestCadastrarDTO;
import clinica_back.clinica_back.features.Usuario.Paciente.dto.PacienteRequestUpdateDTO;
import clinica_back.clinica_back.features.Usuario.Paciente.dto.PacienteResponseDTO;
import clinica_back.clinica_back.shared.exceptions.RecursoNaoEncontradoException;
import clinica_back.clinica_back.shared.exceptions.RegistroDuplicadoException;
import clinica_back.clinica_back.shared.exceptions.RegraNegocioException;
import clinica_back.clinica_back.shared.util.AuditoriaUtil;
import clinica_back.clinica_back.shared.util.DataUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PacienteService {

        private final AlergiaRepository alergiaRepository;
        private final DoencaCronicaRepository doencaCronicaRepository;
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
                                pacienteSalvo.getCpf(),
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

        public PacienteCompletoResponseDTO buscarPorId(Long id) {
                Paciente paciente = pacienteRepository.findById(id)
                                .orElseThrow(() -> new RecursoNaoEncontradoException(
                                                "Paciente com ID " + id + " não encontrado"));

                DadosClinicos dados = paciente.getDadosClinicos();

                DadosClinicosResponseDTO dadosDTO = new DadosClinicosResponseDTO();

                dadosDTO.setPeso(dados.getPeso());
                dadosDTO.setAltura(dados.getAltura());
                dadosDTO.setTipoSanguineo(dados.getTipoSanguineo());

                dadosDTO.setDoencasCronicas(
                                dados.getDoencasCronicas()
                                                .stream()
                                                .map(doenca -> doenca.getNomeDoenca())
                                                .toList());

                dadosDTO.setAlergias(
                                dados.getAlergias()
                                                .stream()
                                                .map(alergia -> alergia.getNomeAlergia())
                                                .toList());
                Convenio convenio = paciente.getConvenio();
                return new PacienteCompletoResponseDTO(
                                paciente.getIdUsuario(),
                                paciente.getNome(),
                                paciente.getSobrenome(),
                                paciente.getEmail(),
                                paciente.getTelefone(),
                                paciente.getSexo(),
                                paciente.getProfissao(),
                                paciente.getDataNascimento(),
                                paciente.getIdade(),
                                paciente.getEndereco().getRua(),
                                paciente.getEndereco().getNumero(),
                                paciente.getEndereco().getBairro(),
                                paciente.getEndereco().getCidade(),
                                paciente.getEndereco().getEstado(),
                                paciente.getEndereco().getCep(),
                                convenio != null ? convenio.getPlano() : null,
                                convenio != null ? convenio.getNumero() : null,
                                convenio != null ? convenio.getData() : null,

                                dadosDTO);
        }

        @Transactional
        public PacienteResponseDTO atualizarDados(Long id, PacienteRequestUpdateDTO dto) {
                Paciente paciente = pacienteRepository.findById(id)
                                .orElseThrow(() -> new RecursoNaoEncontradoException(
                                                "Paciente com ID " + id + " não encontrado"));

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

                descricao.append(AuditoriaUtil.registrarAlteracao("Sobrenome", paciente.getSobrenome(),
                                dto.getSobrenome()));

                descricao.append(AuditoriaUtil.registrarAlteracao("Telefone", paciente.getTelefone(),
                                dto.getTelefone()));

                descricao.append(AuditoriaUtil.registrarAlteracao("Email", paciente.getEmail(), dto.getEmail()));

                descricao.append(AuditoriaUtil.registrarAlteracao("Profissão", paciente.getProfissao(),
                                dto.getProfissao()));

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

        @Transactional
        public AlergiaResponseDTO adicionarAlergia(Long idPaciente, AlergiaRequestDTO dto) {

                Paciente paciente = pacienteRepository.findById(idPaciente)
                                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado."));

                DadosClinicos dados = paciente.getDadosClinicos();

                Alergia alergia = new Alergia();

                alergia.setNomeAlergia(dto.getNome());
                alergia.setDadosClinicos(dados);

                alergiaRepository.save(alergia);

                logAuditoriaService.registrar(AcaoAuditoriaEnum.UPDATE, "PACIENTE", paciente.getIdUsuario(),
                                "Adicionou alergia: " + dto.getNome());

                return new AlergiaResponseDTO(alergia.getIdAlergia(), alergia.getNomeAlergia());
        }

        @Transactional
        public DoencaCronicaResponseDTO adicionarDoencaCronica(Long idPaciente, DoencaCronicaRequestDTO dto) {

                Paciente paciente = pacienteRepository.findById(idPaciente)
                                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado."));

                DadosClinicos dados = paciente.getDadosClinicos();

                DoencaCronica doencaCronica = new DoencaCronica();

                doencaCronica.setNomeDoenca(dto.getNome());
                doencaCronica.setDadosClinicos(dados);

                doencaCronicaRepository.save(doencaCronica);

                logAuditoriaService.registrar(AcaoAuditoriaEnum.UPDATE, "PACIENTE", paciente.getIdUsuario(),
                                "Adicionou doenca crônica: " + dto.getNome());

                return new DoencaCronicaResponseDTO(
                                doencaCronica.getIdDoencaCronica(),
                                doencaCronica.getNomeDoenca());
        }

        public List<AlergiaResponseDTO> listarAlergias(Long idPaciente) {

                Paciente paciente = pacienteRepository.findById(idPaciente).orElseThrow(
                                () -> new RecursoNaoEncontradoException(
                                                "Paciente com ID " + idPaciente + " não encontrado."));

                return paciente.getDadosClinicos().getAlergias().stream().map(alergia -> new AlergiaResponseDTO(
                                alergia.getIdAlergia(),
                                alergia.getNomeAlergia())).toList();
        }

        public List<DoencaCronicaResponseDTO> listarDoencasCronicas(Long idPaciente) {

                Paciente paciente = pacienteRepository.findById(idPaciente).orElseThrow(
                                () -> new RecursoNaoEncontradoException(
                                                "Paciente com ID " + idPaciente + " não encontrado."));

                return paciente.getDadosClinicos().getDoencasCronicas().stream()
                                .map(doencaCronica -> new DoencaCronicaResponseDTO(
                                                doencaCronica.getIdDoencaCronica(),
                                                doencaCronica.getNomeDoenca()))
                                .toList();
        }

        @Transactional
        public void removerAlergia(Long idPaciente, Long idAlergia) {

                Paciente paciente = pacienteRepository.findById(idPaciente).orElseThrow(
                                () -> new RecursoNaoEncontradoException(
                                                "Paciente com ID " + idPaciente + " não encontrado."));

                Alergia alergia = alergiaRepository.findById(idAlergia)
                                .orElseThrow(() -> new RecursoNaoEncontradoException("Alergia não encontrada."));

                if (!alergia.getDadosClinicos().getPaciente().getIdUsuario().equals(idPaciente)) {
                        throw new RegraNegocioException("A alergia não pertence a este paciente.");
                }

                logAuditoriaService.registrar(AcaoAuditoriaEnum.DELETE, "ALERGIA", alergia.getIdAlergia(),
                                "Removeu a alergia '" + alergia.getNomeAlergia() + "' do paciente " + paciente.getNome()
                                                + " "
                                                + paciente.getSobrenome() + " - CPF: " + paciente.getCpf());
                alergiaRepository.delete(alergia);
        }

        @Transactional
        public void removerDoencaCronica(Long idPaciente, Long idDoencaCronica) {

                Paciente paciente = pacienteRepository.findById(idPaciente).orElseThrow(
                                () -> new RecursoNaoEncontradoException(
                                                "Paciente com ID " + idPaciente + " não encontrado."));

                DoencaCronica doencaCronica = doencaCronicaRepository.findById(idDoencaCronica)
                                .orElseThrow(() -> new RecursoNaoEncontradoException("Doença Crônica não encontrada."));

                if (!doencaCronica.getDadosClinicos().getPaciente().getIdUsuario().equals(idPaciente)) {
                        throw new RegraNegocioException("A Deonça crônica não pertence a este paciente.");
                }

                logAuditoriaService.registrar(AcaoAuditoriaEnum.DELETE, "DOENCACRONICA",
                                doencaCronica.getIdDoencaCronica(),
                                "Removeu a doença crônica '" + doencaCronica.getNomeDoenca() + "' do paciente "
                                                + paciente.getNome()
                                                + " "
                                                + paciente.getSobrenome() + " - CPF: " + paciente.getCpf());
                doencaCronicaRepository.delete(doencaCronica);
        }
}