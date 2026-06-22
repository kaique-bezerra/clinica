package clinica_back.clinica_back.features.Consulta.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ConsultaRequestDTO {

    private Long idPaciente;
    private Long idMedico;
    private LocalDate dataConsulta;
    private LocalTime horaConsulta;
}