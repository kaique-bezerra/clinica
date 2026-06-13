package clinica_back.clinica_back.features.Usuario.Recepcionista;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import clinica_back.clinica_back.features.Usuario.PerfilUsuario;
import clinica_back.clinica_back.features.Usuario.Endereco.Endereco;
import clinica_back.clinica_back.features.Usuario.Recepcionista.dto.RecepcionistaRequestDTO;
import clinica_back.clinica_back.features.Usuario.Recepcionista.dto.RecepcionistaResponseDTO;
import clinica_back.clinica_back.shared.exceptions.RegraNegocioException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecepcionistaService {

    private final RecepcionistaRepository recepcionistaRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public RecepcionistaResponseDTO cadastrar(RecepcionistaRequestDTO dto) {

        Recepcionista recepcionista = new Recepcionista();

        if (recepcionistaRepository.existsByCpf(dto.getCpf())) {
            throw new RegraNegocioException("CPF já cadastrado!");
        }

        if (recepcionistaRepository.existsByEmail(dto.getEmail())) {
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
                recepcionistaSalva.getTelefone());
    }


}