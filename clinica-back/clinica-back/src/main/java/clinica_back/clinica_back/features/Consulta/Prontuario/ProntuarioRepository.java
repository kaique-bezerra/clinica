package clinica_back.clinica_back.features.Consulta.Prontuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {
    Optional<Prontuario> findByConsultaIdConsulta(Long idConsulta);

}
