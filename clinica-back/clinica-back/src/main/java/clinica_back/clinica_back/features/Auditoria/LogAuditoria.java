package clinica_back.clinica_back.features.Auditoria;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import clinica_back.clinica_back.features.Usuario.PerfilUsuario;
import clinica_back.clinica_back.features.Usuario.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "log_auditoria")
public class LogAuditoria {

    @Id
    @Column(name = "id_log_auditoria")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLogAuditoria;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @NotNull
    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private AcaoAuditoriaEnum acao;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50, name = "entidade_afetada")
    private String entidadeAfetada;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String descricao;

    @NotNull
    @Column(nullable = false, name = "id_afetado")
    private Long idAfetado;

    @CreationTimestamp
    @Column(nullable = false, name = "data_hora", updatable = false)
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    @Column(name = "perfil_usuario", nullable = false)
    private PerfilUsuario perfilUsuario;

    @Column(name = "email_usuario", nullable = false)
    private String emailUsuario;

}
