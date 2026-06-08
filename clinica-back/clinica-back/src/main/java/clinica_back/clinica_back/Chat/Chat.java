package clinica_back.clinica_back.Chat;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

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
    @JoinColumn(name = "id_usuario_pac", nullable = false)

    // updatable evita q a hora possa ser alterada depois q for criada...
    @CreationTimestamp // O hibernate adc a data e hora automaticamente, com o horário atual...
    @Column(nullable = false, name = "hora_criacao", updatable = false)
    private LocalDateTime horaCriacao;
}
