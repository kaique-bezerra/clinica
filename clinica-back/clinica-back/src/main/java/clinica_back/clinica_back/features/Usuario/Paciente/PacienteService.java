package clinica_back.clinica_back.features.Usuario.Paciente;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import clinica_back.clinica_back.features.Usuario.PerfilUsuario;
import clinica_back.clinica_back.features.Usuario.Endereco.Endereco;
import clinica_back.clinica_back.features.Usuario.Paciente.Convenio.Convenio;
import clinica_back.clinica_back.features.Usuario.Paciente.DadosClinicos.DadosClinicos;
import clinica_back.clinica_back.features.Usuario.Paciente.DadosClinicos.Alergia.Alergia;
import clinica_back.clinica_back.features.Usuario.Paciente.DadosClinicos.Alergia.dto.AlergiaRequestDTO;
import clinica_back.clinica_back.features.Usuario.Paciente.DadosClinicos.DoencaCronica.DoencaCronica;
import clinica_back.clinica_back.features.Usuario.Paciente.DadosClinicos.DoencaCronica.dto.DoencaCronicaRequestDTO;
import clinica_back.clinica_back.features.Usuario.Paciente.dto.PacienteRequestDTO;
import clinica_back.clinica_back.features.Usuario.Paciente.dto.PacienteResponseDTO;
import clinica_back.clinica_back.shared.exceptions.RegraNegocioException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final PasswordEncoder passwordEncoder;

    public PacienteResponseDTO cadastrar(PacienteRequestDTO dto) {

        Paciente paciente = new Paciente();

        if (pacienteRepository.existsByCpf(dto.getCpf())) {
            throw new RegraNegocioException("CPF já cadastrado!");
        }

        if (pacienteRepository.existsByEmail(dto.getEmail())) {
            throw new RegraNegocioException("Email já cadastrado!");
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
        paciente.setData_nascimento(dto.getData_nascimento());

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

        return new PacienteResponseDTO(
                pacienteSalvo.getIdUsuario(),
                pacienteSalvo.getNome(),
                pacienteSalvo.getSobrenome(),
                pacienteSalvo.getEmail(),
                pacienteSalvo.getTelefone(),
                pacienteSalvo.getProfissao(),
                pacienteSalvo.getData_nascimento(),
                pacienteSalvo.getDadosClinicos().getTipoSanguineo(),
                pacienteSalvo.getDadosClinicos().getAltura(),
                pacienteSalvo.getDadosClinicos().getPeso());
    }
}