package clinica_back.clinica_back.features.Usuario.Recepcionista;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clinica_back.clinica_back.features.Usuario.Recepcionista.dto.RecepcionistaRequestDTO;
import clinica_back.clinica_back.features.Usuario.Recepcionista.dto.RecepcionistaResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/recepcionistas")
@RequiredArgsConstructor
public class RecepcionistaController {

        private final RecepcionistaService recepcionistaService;

        @PostMapping
        public ResponseEntity<RecepcionistaResponseDTO> cadastrar(@Valid @RequestBody RecepcionistaRequestDTO dto) {

                RecepcionistaResponseDTO recepcionista = recepcionistaService.cadastrar(dto);

                return ResponseEntity.status(HttpStatus.CREATED).body(recepcionista);
        }

}