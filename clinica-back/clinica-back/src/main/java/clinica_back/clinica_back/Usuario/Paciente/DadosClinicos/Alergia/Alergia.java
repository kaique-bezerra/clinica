package clinica_back.clinica_back.Usuario.Paciente.DadosClinicos.Alergia;

import jakarta.persistence.*;
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
@Table(name = "alergia")
public class Alergia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alergia")
    private Long idAlergia;

    @JoinColumn(name = "id_dadosClinicos",nullable = false)

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, name = "nome_alergia", length = 50)
    private String nomeAlergia;
}
