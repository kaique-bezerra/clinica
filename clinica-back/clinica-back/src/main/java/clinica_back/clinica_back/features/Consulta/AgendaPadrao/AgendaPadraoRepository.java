package clinica_back.clinica_back.features.Consulta.AgendaPadrao;

import clinica_back.clinica_back.features.Usuario.Medico.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.Optional;

public interface AgendaPadraoRepository extends JpaRepository<AgendaPadrao, Long> {

    Optional<AgendaPadrao> findByMedicoAndDiaSemana(
            Medico medico,
            DayOfWeek diaSemana
    );
}
