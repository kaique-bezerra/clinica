package clinica_back.clinica_back.Usuario.Paciente.DadosClinicos;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dados_clinicos")
public class DadosClinicos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_dadosClinicos;

    @NotBlank
    @Column(length = 3)
    private String tipo_sanguineo;

    @Column(precision = 3, scale = 2)
    private BigDecimal altura;

    @Column(precision = 5, scale = 2)
    private BigDecimal peso;
}
