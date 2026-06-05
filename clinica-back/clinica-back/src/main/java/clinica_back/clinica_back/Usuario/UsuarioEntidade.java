package clinica_back.clinica_back.Usuario;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "Usuario")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder

public class UsuarioEntidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_usuario;

    @NotBlank
    @Size(max = 30)
    @Column(nullable = false, length = 30)
    private String nome;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String sobrenome;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @NotBlank
    @Size(max = 13)
    @Column(nullable = false, length = 13)
    private String telefone;

    @NotBlank
    @Size(max = 11)
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String senha;
}
