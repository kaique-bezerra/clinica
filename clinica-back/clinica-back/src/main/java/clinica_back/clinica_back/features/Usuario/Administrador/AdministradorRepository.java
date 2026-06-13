package clinica_back.clinica_back.features.Usuario.Administrador;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
}
