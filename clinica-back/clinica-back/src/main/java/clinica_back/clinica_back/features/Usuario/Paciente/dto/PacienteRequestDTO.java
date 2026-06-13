package clinica_back.clinica_back.features.Usuario.Paciente.dto;

import java.time.LocalDate;

import clinica_back.clinica_back.features.Usuario.Paciente.Convenio.dto.ConvenioRequestDTO;
import clinica_back.clinica_back.features.Usuario.Paciente.DadosClinicos.dto.DadosClinicosRequestDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacienteRequestDTO {

    @NotBlank
    @Size(max = 30)
    private String nome;

    @NotBlank
    @Size(max = 50)
    private String sobrenome;

    @NotBlank
    @Pattern(regexp = "\\d{11}|\\(\\d{2}\\)\\d{5}-\\d{4}", message = "Telefone deve conter 11 dígitos ou estar no formato (00)00000-00000")
    private String telefone;

    @NotBlank
    @Pattern(regexp = "\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF deve conter 11 dígitos ou estar no formato 000.000.000-00")
    private String cpf;

    @Email
    @NotBlank
    @Size(max = 50)
    private String email;

    @NotBlank // A senha passa pelo dto antes de virar hash
    @Size(min = 6, max = 255)
    private String senha;

    @Size(max = 50)
    private String rua;

    private Integer numero;

    @Size(max = 40)
    private String bairro;

    @Size(max = 30)
    private String cidade;

    @Size(max = 2)
    private String estado;

    @Pattern(regexp = "\\d{8}|\\d{5}-\\d{3}", message = "CEP deve conter no mínimo 8 dígitos ou estar no formato 00000-000")
    private String cep;

    private Character sexo;

    @NotBlank
    @Size(max = 30)
    private String profissao;

    @NotNull
    private LocalDate dataNascimento;

    private ConvenioRequestDTO convenio;

    private DadosClinicosRequestDTO dadosClinicos;

}