package clinica_back.clinica_back.features.Usuario.Paciente;

import java.time.LocalDate;
import java.util.List;

import clinica_back.clinica_back.features.Chat.Chat;
import clinica_back.clinica_back.features.Consulta.Consulta;
import clinica_back.clinica_back.features.Usuario.Usuario;
import clinica_back.clinica_back.features.Usuario.Paciente.Convenio.Convenio;
import clinica_back.clinica_back.features.Usuario.Paciente.DadosClinicos.DadosClinicos;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
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

    @Column(nullable = false, length = 30)
    private String profissao;

    @Column(nullable = false, name = "data_nascimento")
    private LocalDate dataNascimento;

    private Integer idade;

    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private DadosClinicos dadosClinicos;

    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Convenio convenio;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chat> chats;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consulta> consultas;
}
