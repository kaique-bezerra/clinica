package clinica_back.clinica_back.features.Usuario.Endereco.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EnderecoRequestDTO {
    
    @Size(max = 50)
    private String rua;

    private Integer numero;

    @Size(max = 40)
    private String bairro;

    @Size(max = 30)
    private String cidade;

    @Size(max = 2)
    private String estado;

    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP deve conter no mínimo 8 dígitos ou estar no formato 00000-000")
    private String cep;
}
