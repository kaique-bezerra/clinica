package clinica_back.clinica_back.Usuario.Paciente.Convenio.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConvenioRequestDTO {

    private String plano;

    private String numero;

    private LocalDate data;

}
