package clinica_back.clinica_back.Usuario.Endereco;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String rua;

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
