package clinica_back.clinica_back.Auditoria;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @NotNull
    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private AcaoAuditoria acao;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50, name = "tabela_afetada")
    private String tabelaAfetada;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String descricao;

    @Column(nullable = false, name = "id_afetado")
    private Long idAfetado;

    // updatable evita q a hora possa ser alterada depois q for criada...
    @CreationTimestamp // O hibernate adc a data e hora automaticamente, com o horário atual...
    @Column(nullable = false, name = "data_hora", updatable = false)
    private LocalDateTime dataHora;

}
