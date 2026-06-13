package clinica_back.clinica_back.features.Usuario.Administrador.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdministradorResponseDTO {

    private Long id;

    private String nome;

    private String sobrenome;

    private String email;

    private String telefone;

}
