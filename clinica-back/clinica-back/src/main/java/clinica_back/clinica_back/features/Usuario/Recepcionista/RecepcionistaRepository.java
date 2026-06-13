package clinica_back.clinica_back.features.Usuario.Recepcionista;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecepcionistaRepository extends JpaRepository<Recepcionista, Long> {

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
}
