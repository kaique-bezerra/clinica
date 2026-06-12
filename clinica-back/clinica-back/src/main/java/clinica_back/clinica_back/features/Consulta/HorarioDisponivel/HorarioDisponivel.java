package clinica_back.clinica_back.features.Consulta.HorarioDisponivel;

import java.time.LocalDate;
import java.time.LocalTime;

import clinica_back.clinica_back.features.Usuario.Medico.Medico;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
        @UniqueConstraint(columnNames = { "data_disponivel", "hora_disponivel", "id_usuario_med" }) })
public class HorarioDisponivel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horario_disponivel")
    private Long idHorarioDisponivel;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_usuario_med", nullable = false)
    private Medico medico;

    @Column(name = "data_disponivel", nullable = false)
    private LocalDate dataDisponivel;

    @Column(name = "hora_disponivel", nullable = false)
    private LocalTime horaDisponivel;
}
