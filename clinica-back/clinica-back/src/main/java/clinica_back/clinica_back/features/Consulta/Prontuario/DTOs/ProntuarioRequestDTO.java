package clinica_back.clinica_back.features.Consulta.Prontuario.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProntuarioRequestDTO {

    private Long idConsulta;

    @NotBlank
    private String queixas;

    @NotBlank
    private String diagnostico;

    @NotBlank
    private String observacoes;

    @NotBlank
    private String prescricao;
}