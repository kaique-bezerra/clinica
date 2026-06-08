package clinica_back.clinica_back.Consulta.Prontuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "prontuario")
public class Prontuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prontuario")
    private Long idProntuario;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String queixas;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String diagnostico;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String observacoes;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String prescricao;
}
