package clinica_back.clinica_back.Usuario.Paciente.DadosClinicos.DoencaCronica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoencaCronicaRequestDTO {

    @NotBlank
    @Size(max = 100)
    private String nome;
}
