package clinica_back.clinica_back.features.Usuario.Paciente.Convenio;

import java.time.LocalDate;

import clinica_back.clinica_back.features.Usuario.Paciente.Paciente;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "convenio")
public class Convenio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_convenio")
    private Long idConvenio;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_usuario_pac", nullable = false)
    private Paciente paciente;

    @Column(length = 30, nullable = false)
    private String plano;

    @Column(length = 20, nullable = false)
    private String numero;

    @Column(nullable = false)
    private LocalDate data;
}
