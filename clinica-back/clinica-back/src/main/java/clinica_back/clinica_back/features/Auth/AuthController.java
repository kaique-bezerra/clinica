package clinica_back.clinica_back.features.Auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clinica_back.clinica_back.features.Auth.dto.LoginRequestDTO;
import clinica_back.clinica_back.features.Auth.dto.LoginResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> fazerLogin(@Valid @RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(authService.fazerLogin(dto));
    }

}
