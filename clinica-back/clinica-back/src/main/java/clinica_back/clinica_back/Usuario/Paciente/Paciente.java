package clinica_back.clinica_back.Usuario.Paciente;

import java.util.Date;
import java.util.List;

import clinica_back.clinica_back.Chat.Chat;
import clinica_back.clinica_back.Usuario.Usuario;
import clinica_back.clinica_back.Usuario.Paciente.Convenio.Convenio;
import clinica_back.clinica_back.Usuario.Paciente.DadosClinicos.DadosClinicos;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "paciente")
@PrimaryKeyJoinColumn(name = "id_usuario")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Paciente extends Usuario {

    @Column(nullable = false, length = 1)
    private Character sexo;

    @NotBlank
    @Size(max = 30)
    @Column(nullable = false, length = 30)
    private String profissao;

    @NotNull
    @Column(nullable = false)
    private Date data_nascimento;

    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private DadosClinicos dadosClinicos;

    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Convenio convenio;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chat> chats;
}
