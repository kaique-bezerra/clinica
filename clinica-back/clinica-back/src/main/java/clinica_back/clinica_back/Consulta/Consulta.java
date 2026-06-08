package clinica_back.clinica_back.Consulta;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;
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
@Table(name = "consulta", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "data_consulta", "hora_consulta" }) }) // Falta adc id_medico
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consulta")
    private Long idConsulta;

    @NotNull
    @JoinColumn(name = "id_usuario_pac", nullable = false)

    @NotNull
    @JoinColumn(name = "id_usuario_med", nullable = false)

    @NotNull
    @JoinColumn(name = "id_usuario_pac", nullable = false)

    @NotNull
    @JoinColumn(name = "id_usuario_med", nullable = false)

    @Column(name = "data_consulta", nullable = false)
    private LocalDate dataConsulta;

    @Column(name = "hora_consulta", nullable = false)
    private LocalTime horaConsulta;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private StatusConsulta statusConsulta;
}
