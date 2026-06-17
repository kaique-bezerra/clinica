package clinica_back.clinica_back.features.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);

}
