package clinica_back.clinica_back.features.Consulta;

import clinica_back.clinica_back.features.Usuario.Medico.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    boolean existsByMedicoAndDataConsultaAndHoraConsulta(
            Medico medico,
            LocalDate dataConsulta,
            LocalTime horaConsulta
    );
}
