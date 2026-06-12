package clinica_back.clinica_back.features.Usuario.Medico.dto;

import clinica_back.clinica_back.features.Usuario.Medico.StatusMedico;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MedicoResponseDTO {

    private Long id;

    private String nome;

    private String sobrenome;

    private String email;

    private String telefone;

    private String crm;

    private String especialidade;

    private StatusMedico status;
}
