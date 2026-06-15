package clinica_back.clinica_back.features.Consulta.AgendaPadrao.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Setter
public  class  AgendaPadraoRequestDTO{
private Long idMedico;
private DayOfWeek diaSemana;
private LocalTime horaInicio;
private LocalTime horaFim;
private Integer intervaloMinutos;
}