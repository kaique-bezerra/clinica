package clinica_back.clinica_back.Usuario.Medico.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicoRequestDTO {

    @NotBlank
    @Size(max = 30)
    private String nome;

    @NotBlank
    @Size(max = 50)
    private String sobrenome;

    @NotBlank
    @Pattern(regexp = "\\d{11}|\\(\\d{2}\\)\\d{5}-\\d{4}")
    private String telefone;

    @NotBlank
    @Pattern(regexp = "\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")
    private String cpf;

    @Email
    @NotBlank
    @Size(max = 50)
    private String email;

    @NotBlank // A senha passa pelo dto antes de virar hash
    @Size(min = 6, max = 255)
    private String senha;

    @NotBlank
    @Size(max = 15)
    private String crm;

    @NotBlank
    @Size(max = 50)
    private String especialidade;

    @Size(max = 50)
    private String rua;

    private Integer numero;

    @Size(max = 40)
    private String bairro;

    @Size(max = 30)
    private String cidade;

    @Size(max = 2)
    private String estado;

    @Pattern(regexp = "\\d{8}|\\d{5}-\\d{3}")
    private String cep;
}