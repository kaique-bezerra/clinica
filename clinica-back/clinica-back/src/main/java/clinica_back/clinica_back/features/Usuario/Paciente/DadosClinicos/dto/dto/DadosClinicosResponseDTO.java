package clinica_back.clinica_back.features.Usuario.Paciente.DadosClinicos.dto.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DadosClinicosResponseDTO {
    
    private BigDecimal peso;
    private BigDecimal altura;
    private String tipoSanguineo;

    private List<String> alergias;
    private List<String> doencasCronicas;
}
