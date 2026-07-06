package clinica_back.clinica_back.features.Chat.Mensagem.dto;

import java.time.LocalDateTime;

import clinica_back.clinica_back.features.Chat.Mensagem.PapelMensagemEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MensagemResponseDTO {

    private Long id;
    private String conteudo;
    private PapelMensagemEnum papel;
    private LocalDateTime dataHora;

}
