package clinica_back.clinica_back.features.Chat.Dto;

import java.util.List;

public record GeminiResponseDTO(
        List<Candidate> candidates
) {
}