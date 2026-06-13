package clinica_back.clinica_back.features.Usuario.Medico;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import clinica_back.clinica_back.features.Usuario.PerfilUsuario;
import clinica_back.clinica_back.features.Usuario.Endereco.Endereco;
import clinica_back.clinica_back.features.Usuario.Medico.dto.MedicoRequestDTO;
import clinica_back.clinica_back.features.Usuario.Medico.dto.MedicoResponseDTO;
import clinica_back.clinica_back.shared.exceptions.RegraNegocioException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicoService {

    private final MedicoRepository medicoRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MedicoResponseDTO cadastrar(MedicoRequestDTO dto) {

        Medico medico = new Medico();

        if (medicoRepository.existsByCpf(dto.getCpf())) {
            throw new RegraNegocioException("CPF já cadastrado!");
        }

        if (medicoRepository.existsByEmail(dto.getEmail())) {
            throw new RegraNegocioException("Email já cadastrado!");
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

}
