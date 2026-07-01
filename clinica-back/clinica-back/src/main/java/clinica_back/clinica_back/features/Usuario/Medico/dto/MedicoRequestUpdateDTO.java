package clinica_back.clinica_back.features.Usuario.Medico.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicoRequestUpdateDTO {

    @NotBlank
    @Size(max = 30)
    private String nome;

    @NotBlank
    @Size(max = 50)
    private String sobrenome;

    @NotBlank
    @Pattern(regexp = "\\(\\d{2}\\)\\d{5}-\\d{4}", message = "Telefone deve conter 11 dígitos ou estar no formato (00)00000-00000")
    private String telefone;

    @Email
    @NotBlank
    @Size(max = 50)
    private String email;

    @NotBlank
    @Size(max = 50)
    private String especialidade;

}