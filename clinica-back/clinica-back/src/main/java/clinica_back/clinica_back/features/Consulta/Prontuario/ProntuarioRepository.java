package clinica_back.clinica_back.features.Consulta.Prontuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {
    Optional<Prontuario> findByConsultaIdConsulta(Long idConsulta);

}
