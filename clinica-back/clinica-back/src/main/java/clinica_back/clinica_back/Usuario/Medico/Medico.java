package clinica_back.clinica_back.Usuario.Medico;

import clinica_back.clinica_back.Usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "medico")
@PrimaryKeyJoinColumn(name = "id_usuario")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Medico extends Usuario {

    @NotBlank
    @Size(max = 15)
    @Column(nullable = false, unique = true, length = 15)
    private String crm;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String especialidade;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private StatusMedico status;

}
