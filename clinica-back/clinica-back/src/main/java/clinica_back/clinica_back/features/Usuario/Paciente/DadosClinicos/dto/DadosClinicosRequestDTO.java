package clinica_back.clinica_back.features.Usuario.Paciente.DadosClinicos.dto;

import java.math.BigDecimal;
import java.util.List;

import clinica_back.clinica_back.features.Usuario.Paciente.DadosClinicos.Alergia.dto.AlergiaRequestDTO;
import clinica_back.clinica_back.features.Usuario.Paciente.DadosClinicos.DoencaCronica.dto.DoencaCronicaRequestDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DadosClinicosRequestDTO {

    @NotBlank
    private String tipoSanguineo;

    private BigDecimal altura;

    private BigDecimal peso;

    private List<AlergiaRequestDTO> alergias;

    private List<DoencaCronicaRequestDTO> doencasCronicas;
}
