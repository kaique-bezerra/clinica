package clinica_back.clinica_back.Chat.Mensagem;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "mensagem_ia")
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensagem")
    private Long idMensagem;

    @JoinColumn(name = "idChat",nullable = false)

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String Conteudo;

    @CreationTimestamp // O hibernate adc a data e hora automaticamente, com o horário atual...
    @Column(nullable = false, name = "data_hora", updatable = false)
    private LocalDateTime dataHora;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PapelMensagemEnum papel;
}
