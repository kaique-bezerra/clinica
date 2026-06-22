package clinica_back.clinica_back.features.Consulta.AgendaPadrao.HorarioBloqueado;

import clinica_back.clinica_back.features.Usuario.Medico.Medico;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@Setter
@Entity
public class HorarioBloqueado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario_med", nullable = false)
    private Medico medico;

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "horaInicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "horaFim", nullable = false)
    private LocalTime horaFim;

    @Column(name = "motivo", nullable = false)
    private String motivo;
}
