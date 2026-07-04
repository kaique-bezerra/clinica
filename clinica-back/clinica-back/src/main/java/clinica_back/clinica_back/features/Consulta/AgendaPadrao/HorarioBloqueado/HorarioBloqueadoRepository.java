package clinica_back.clinica_back.features.Consulta.AgendaPadrao.HorarioBloqueado;

import clinica_back.clinica_back.features.Usuario.Medico.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface HorarioBloqueadoRepository extends JpaRepository<HorarioBloqueado,Long> {

    boolean existsByMedicoAndDataAndHoraInicioLessThanEqualAndHoraFimGreaterThanEqual(
            Medico medico,
            LocalDate data,
            LocalTime horaInicio,
            LocalTime horaFim
    );
    List<HorarioBloqueado> findByMedicoAndData(
            Medico medico,
            LocalDate data
    );
}
