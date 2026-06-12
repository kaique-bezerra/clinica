package clinica_back.clinica_back.features.Usuario.Recepcionista;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import clinica_back.clinica_back.features.Usuario.PerfilUsuario;
import clinica_back.clinica_back.features.Usuario.Endereco.Endereco;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecepcionistaService {

    private final RecepcionistaRepository recepcionistaRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Recepcionista cadastrar(RecepcionistaRequestDTO dto) {

        Recepcionista recepcionista = new Recepcionista();

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

        // Relacionamento bidirecional
        endereco.setUsuario(recepcionista);
        recepcionista.setEndereco(endereco);

        return recepcionistaRepository.save(recepcionista);
    }

    public List<Recepcionista> listarTodos() {
        return recepcionistaRepository.findAll();
    }
}