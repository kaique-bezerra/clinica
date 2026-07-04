package clinica_back.clinica_back.features.Auditoria.dto;

import java.time.LocalDateTime;

import clinica_back.clinica_back.features.Auditoria.AcaoAuditoriaEnum;
import clinica_back.clinica_back.features.Usuario.PerfilUsuario;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LogAuditoriaResponseDTO {

    private Long idLogAuditoria;
    private LocalDateTime dataHora;
    private String emailUsuario;
    private PerfilUsuario perfilUsuario;
    private AcaoAuditoriaEnum acao;
    private String entidadeAfetada;
    private Long idAfetado;
    private String descricao;

}
