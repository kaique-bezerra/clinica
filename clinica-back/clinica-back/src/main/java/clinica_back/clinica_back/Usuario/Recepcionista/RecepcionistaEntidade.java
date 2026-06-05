package clinica_back.clinica_back.Usuario.Recepcionista;

import clinica_back.clinica_back.Usuario.UsuarioEntidade;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "Recepcionista")
@PrimaryKeyJoinColumn(name = "id_usuario")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class RecepcionistaEntidade extends UsuarioEntidade {

}
