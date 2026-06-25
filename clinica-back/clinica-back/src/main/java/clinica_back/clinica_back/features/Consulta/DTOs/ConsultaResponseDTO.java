package clinica_back.clinica_back.features.Consulta.DTOs;

import clinica_back.clinica_back.features.Consulta.StatusConsulta;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ConsultaResponseDTO {

    private Long idConsulta;
    private String nomePaciente;
    private String nomeMedico;
    private LocalDate dataConsulta;
    private LocalTime horaConsulta;
    private StatusConsulta statusConsulta;
}