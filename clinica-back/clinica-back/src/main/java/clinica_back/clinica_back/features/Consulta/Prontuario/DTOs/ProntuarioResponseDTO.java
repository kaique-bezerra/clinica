package clinica_back.clinica_back.features.Consulta.Prontuario.DTOs;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProntuarioResponseDTO {

    private Long idProntuario;
    private Long idConsulta;
    private String queixas;
    private String diagnostico;
    private String observacoes;
    private String prescricao;
}