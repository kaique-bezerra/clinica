package clinica_back.clinica_back.features.Usuario.Paciente.Convenio.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConvenioRequestDTO {

    @NotBlank
    private String plano;

    @NotBlank
    private String numero;

    @NotNull
    private LocalDate data;

}
