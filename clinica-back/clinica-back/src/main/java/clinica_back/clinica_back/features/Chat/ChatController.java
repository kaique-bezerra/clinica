package clinica_back.clinica_back.features.Chat;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clinica_back.clinica_back.features.Chat.Dto.ChatRequestDTO;
import clinica_back.clinica_back.features.Chat.Mensagem.dto.MensagemResponseDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PreAuthorize("hasRole('PACIENTE')")
    @PostMapping
    public ResponseEntity<String> chat(
            @RequestBody ChatRequestDTO dto, Authentication authentication) {
        return ResponseEntity.ok(
                chatService.perguntar(authentication.getName(), dto.pergunta()));
    }

    @GetMapping
    @PreAuthorize("hasRole('PACIENTE')")
    public ResponseEntity<List<MensagemResponseDTO>> buscarHistorico(
            Authentication authentication) {

        return ResponseEntity.ok(
                chatService.buscarHistorico(authentication.getName()));
    }
}