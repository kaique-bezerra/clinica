package clinica_back.clinica_back.Usuario.Paciente;

import clinica_back.clinica_back.Usuario.UsuarioEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Entity
@Table(name = "Paciente")
@PrimaryKeyJoinColumn(name = "id_usuario")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class PacienteEntity extends UsuarioEntity {

    @NotBlank
    @Column(nullable = false, length = 1)
    private char sexo;

    @NotBlank
    @Size(max = 30)
    @Column(nullable = false, length = 30)
    private String profissao;

    @NotNull
    @Column(nullable = false)
    private Date data_nascimento;
}
