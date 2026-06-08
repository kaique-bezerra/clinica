package clinica_back.clinica_back.Consulta.HorarioDisponivel;

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
@Table(name = "horario_disponivel", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "data_disponivel", "hora_disponivel" }) }) // Falta adc id_medico como unico
public class HorarioDisponivel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horario_disponivel")
    private Long idHorarioDisponivel;

    @NotNull
    @JoinColumn(name = "id_usuario_med", nullable = false)

    @Column(name = "data_disponivel", nullable = false)
    private LocalDate dataDisponivel;

    @Column(name = "hora_disponivel", nullable = false)
    private LocalTime horaDisponivel;
}
