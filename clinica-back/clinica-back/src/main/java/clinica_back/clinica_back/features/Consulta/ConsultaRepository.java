package clinica_back.clinica_back.features.Consulta;

import clinica_back.clinica_back.features.Usuario.Medico.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    boolean existsByMedicoAndDataConsultaAndHoraConsulta(
            Medico medico,
            LocalDate dataConsulta,
            LocalTime horaConsulta
    );
    List<Consulta> findByMedicoAndDataConsulta(
            Medico medico,
            LocalDate dataConsulta
    );

    @Query("""
    SELECT c
    FROM Consulta c
    WHERE c.medico.idUsuario = :idMedico
    ORDER BY c.dataConsulta ASC, c.horaConsulta ASC
""")
    List<Consulta> findByMedicoId(@Param("idMedico") Long idMedico);

    @Query("SELECT c FROM Consulta c WHERE c.paciente.idUsuario = :idPaciente")
    List<Consulta> findByPacienteId(@Param("idPaciente") Long idPaciente);

    List<Consulta> findByDataConsulta(LocalDate data);

    List<Consulta> findByDataConsultaBetween(LocalDate dataInicio, LocalDate dataFim);

    List<Consulta> findByProntuarioIsNull();

}