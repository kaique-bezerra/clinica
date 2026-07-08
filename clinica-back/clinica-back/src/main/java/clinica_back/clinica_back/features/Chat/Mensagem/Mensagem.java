package clinica_back.clinica_back.features.Chat.Mensagem;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import clinica_back.clinica_back.features.Chat.Chat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "mensagem")
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensagem")
    private Long idMensagem;

    @ManyToOne
    @JoinColumn(name = "id_Chat", nullable = false)
    private Chat chat;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String conteudo;

    @CreationTimestamp 
    @Column(nullable = false, name = "data_hora", updatable = false)
    private LocalDateTime dataHora;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PapelMensagemEnum papel;
}
