package clinica_back.clinica_back.features.Usuario.dto;

import clinica_back.clinica_back.features.Usuario.PerfilUsuario;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsuarioResponseDTO {

    private Long idUsuario;

    private String nome;

    private String sobrenome;

    private String email;

    private String telefone;

    private PerfilUsuario perfil;
}
