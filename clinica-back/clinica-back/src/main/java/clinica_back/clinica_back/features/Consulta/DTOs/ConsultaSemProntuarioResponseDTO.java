package clinica_back.clinica_back.features.Consulta.DTOs;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaSemProntuarioResponseDTO {

    private Long idConsulta;
    private String nomePaciente;
    private String nomeMedico;
    private LocalDate dataConsulta;
    private LocalTime horaConsulta;
}
