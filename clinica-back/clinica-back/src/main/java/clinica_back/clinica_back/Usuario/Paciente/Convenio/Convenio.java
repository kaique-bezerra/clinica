package clinica_back.clinica_back.Usuario.Paciente.Convenio;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Convenio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_convenio;

    @NotBlank
    @Column(length = 30, nullable = false)
    private String plano;

    @NotBlank
    @Column(length = 20, nullable = false)
    private String numero;

    @Column(nullable = false)
    private LocalDate data;
}
