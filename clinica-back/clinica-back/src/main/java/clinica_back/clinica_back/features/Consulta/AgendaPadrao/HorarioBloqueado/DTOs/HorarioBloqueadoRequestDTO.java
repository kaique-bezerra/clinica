package clinica_back.clinica_back.features.Consulta.AgendaPadrao.HorarioBloqueado.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HorarioBloqueadoRequestDTO {

    @NotNull
    private Long idMedico;

    @NotNull
    private LocalDate data;

    @NotNull
    private LocalTime horaInicio;

    @NotNull
    private LocalTime horaFim;

    @NotBlank
    private String motivo;
}