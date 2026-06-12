package clinica_back.clinica_back.features.Usuario.Medico;

import java.util.List;

import clinica_back.clinica_back.features.Consulta.HorarioDisponivel.HorarioDisponivel;
import clinica_back.clinica_back.features.Usuario.Usuario;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "medico")
@PrimaryKeyJoinColumn(name = "id_usuario")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Medico extends Usuario {

    @NotBlank
    @Size(max = 15)
    @Column(nullable = false, unique = true, length = 15)
    private String crm;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String especialidade;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private StatusMedico status;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HorarioDisponivel> horariosDisponiveis;

}
