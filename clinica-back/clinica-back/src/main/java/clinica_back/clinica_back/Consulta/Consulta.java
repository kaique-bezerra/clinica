package clinica_back.clinica_back.Consulta;

import java.time.LocalDate;
import java.time.LocalTime;

import clinica_back.clinica_back.Consulta.Prontuario.Prontuario;
import clinica_back.clinica_back.Usuario.Medico.Medico;
import clinica_back.clinica_back.Usuario.Paciente.Paciente;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@Table(name = "consulta", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "data_consulta", "hora_consulta", "id_usuario_med" }) })
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consulta")
    private Long idConsulta;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_usuario_pac", nullable = false)
    private Paciente paciente;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_usuario_med", nullable = false)
    private Medico medico;

    @Column(name = "data_consulta", nullable = false)
    private LocalDate dataConsulta;

    @Column(name = "hora_consulta", nullable = false)
    private LocalTime horaConsulta;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private StatusConsulta statusConsulta;

    @OneToOne(mappedBy = "consulta", cascade = CascadeType.ALL, orphanRemoval = true)
    private Prontuario prontuario;
}
