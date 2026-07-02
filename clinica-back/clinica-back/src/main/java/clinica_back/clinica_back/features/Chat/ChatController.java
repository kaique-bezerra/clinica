package clinica_back.clinica_back.features.Chat;

import clinica_back.clinica_back.features.Chat.Dto.ChatRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final GeminiService geminiService;
    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMINISTRADOR') or hasRole('PACIENTE')")
    @PostMapping
    public ResponseEntity<String> chat(
            @RequestBody ChatRequestDTO dto
    ) {
        return ResponseEntity.ok(
                geminiService.perguntar(dto.pergunta())
        );
    }
}