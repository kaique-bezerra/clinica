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

    // Mudamos para buscar comparando o ID direto na coluna mapeada pelo objeto
    @Query("SELECT c FROM Consulta c WHERE c.medico.id = :idMedico")
    List<Consulta> findByMedicoId(@Param("idMedico") Long idMedico);

    @Query("SELECT c FROM Consulta c WHERE c.paciente.id = :idPaciente")
    List<Consulta> findByPacienteId(@Param("idPaciente") Long idPaciente);

    List<Consulta> findByDataConsulta(LocalDate data);

    List<Consulta> findByDataConsultaBetween(LocalDate dataInicio, LocalDate dataFim);
}