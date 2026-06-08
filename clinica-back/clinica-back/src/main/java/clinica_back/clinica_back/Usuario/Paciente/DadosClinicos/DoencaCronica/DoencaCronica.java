package clinica_back.clinica_back.Usuario.Paciente.DadosClinicos.DoencaCronica;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "doenca_cronica")
public class DoencaCronica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_doenca_cronica")
    private Long idDoencaCronica;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, name = "nome_doenca", length = 50)
    private String nomeDoenca;
}
