package clinica_back.clinica_back.Consulta.Prontuario;

import clinica_back.clinica_back.Consulta.Consulta;
import jakarta.persistence.*;
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

    @JoinColumn(name = "id_consulta",nullable = false,unique = true)
    @OneToOne
    private Consulta consulta;

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
