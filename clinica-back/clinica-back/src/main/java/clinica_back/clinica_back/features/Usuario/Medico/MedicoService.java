package clinica_back.clinica_back.features.Usuario.Medico;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import clinica_back.clinica_back.features.Usuario.PerfilUsuario;
import clinica_back.clinica_back.features.Usuario.Endereco.Endereco;
import clinica_back.clinica_back.features.Usuario.Medico.dto.MedicoRequestDTO;
import clinica_back.clinica_back.features.Usuario.Medico.dto.MedicoResponseDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicoService {

    private final MedicoRepository medicoRepository;
    private final PasswordEncoder passwordEncoder;

    public MedicoResponseDTO cadastrar(MedicoRequestDTO dto) {

        Medico medico = new Medico();

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
                medicoSalvo.getId_usuario(),
                medicoSalvo.getNome(),
                medicoSalvo.getSobrenome(),
                medicoSalvo.getEmail(),
                medicoSalvo.getTelefone(),
                medicoSalvo.getCrm(),
                medicoSalvo.getEspecialidade(),
                medicoSalvo.getStatus());
    }
}
