package clinica_back.clinica_back.Usuario.Paciente.DadosClinicos.Alergia;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "alergia")
public class Alergia {

    @Id
    private Long id;
}
