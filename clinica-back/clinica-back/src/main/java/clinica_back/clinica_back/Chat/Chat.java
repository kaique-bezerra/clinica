package clinica_back.clinica_back.Chat;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import clinica_back.clinica_back.Usuario.Paciente.Paciente;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Table(name = "chat_ia")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chat")
    private Long idChat;

    @NotNull
    @OneToOne // Ainda não decidi se será one to one ou Manytoone
    @JoinColumn(name = "id_usuario_pac", nullable = false)
    private Paciente paciente;

    // updatable evita q a hora possa ser alterada depois q for criada...
    @CreationTimestamp // O hibernate adc a data e hora automaticamente, com o horário atual...
    @Column(nullable = false, name = "hora_criacao", updatable = false)
    private LocalDateTime horaCriacao;
}
