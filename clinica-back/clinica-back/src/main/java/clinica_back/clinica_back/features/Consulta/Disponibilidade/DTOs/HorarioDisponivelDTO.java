package clinica_back.clinica_back.features.Consulta.Disponibilidade.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class HorarioDisponivelDTO {

    private LocalTime hora;
}