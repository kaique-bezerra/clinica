package clinica_back.clinica_back.Usuario.Endereco;

import clinica_back.clinica_back.Usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Table(name = "endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "id_usuario",nullable = false,unique = true)
    private Usuario usuario;

    @Column(length = 50)
    private String rua;

    @Column(length = 5)
    private Integer numero;

    @Column(length = 40)
    private String bairro;

    @Column(length = 30)
    private String cidade;

    @Column(length = 2)
    private String estado;

    @Column(length = 9)
    private String cep;

}
