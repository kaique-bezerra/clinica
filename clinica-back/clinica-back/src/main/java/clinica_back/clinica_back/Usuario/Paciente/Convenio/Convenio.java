package clinica_back.clinica_back.Usuario.Paciente.Convenio;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @JoinColumn(name = "id_usuario_pac", nullable = false)

    @NotBlank
    @Column(length = 30, nullable = false)
    private String plano;

    @NotBlank
    @Column(length = 20, nullable = false)
    private String numero;

    @Column(nullable = false)
    private LocalDate data;
}
