package clinica_back.clinica_back.features.Consulta.AgendaPadrao;

import clinica_back.clinica_back.features.Usuario.Medico.Medico;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;
@Getter
@Setter

    @Entity
    public class AgendaPadrao {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "id_usuario_med", nullable = false)
        private Medico medico;

        @Enumerated(EnumType.STRING)
        @Column(name = "diaSemana", nullable = false)
        private DayOfWeek diaSemana;

        @Column(name = "horaInicio", nullable = false)
        private LocalTime horaInicio;

        @Column(name = "horaFim", nullable = false)
        private LocalTime horaFim;

        @Column(name = "intervaloMinutos", nullable = false)
        private Integer intervaloMinutos;
    }

