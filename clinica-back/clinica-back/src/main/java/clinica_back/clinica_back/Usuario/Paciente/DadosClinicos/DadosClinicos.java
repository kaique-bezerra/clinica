package clinica_back.clinica_back.Usuario.Paciente.DadosClinicos;

import java.math.BigDecimal;
import java.util.List;

import clinica_back.clinica_back.Usuario.Paciente.Paciente;
import clinica_back.clinica_back.Usuario.Paciente.DadosClinicos.Alergia.Alergia;
import clinica_back.clinica_back.Usuario.Paciente.DadosClinicos.DoencaCronica.DoencaCronica;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
    @Column(length = 3, name = "tipo_sanguineo")
    private String tipoSanguineo;

    @Column(precision = 3, scale = 2)
    private BigDecimal altura;

    @Column(precision = 5, scale = 2)
    private BigDecimal peso;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario_pac", nullable = false, unique = true)
    private Paciente paciente;

    @OneToMany(mappedBy = "dadosClinicos", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DoencaCronica> doencasCronicas;

    @OneToMany(mappedBy = "dadosClinicos", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alergia> alergias;
}
