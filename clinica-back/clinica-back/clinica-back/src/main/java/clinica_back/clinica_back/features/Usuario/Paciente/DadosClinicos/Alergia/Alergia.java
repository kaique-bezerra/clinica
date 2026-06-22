package clinica_back.clinica_back.features.Usuario.Paciente.DadosClinicos.Alergia;

import clinica_back.clinica_back.features.Usuario.Paciente.DadosClinicos.DadosClinicos;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "alergia")
public class Alergia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alergia")
    private Long idAlergia;

    @ManyToOne
    @JoinColumn(name = "id_dadosClinicos", nullable = false)
    private DadosClinicos dadosClinicos;

    @Column(nullable = false, name = "nome_alergia", length = 50)
    private String nomeAlergia;
}
