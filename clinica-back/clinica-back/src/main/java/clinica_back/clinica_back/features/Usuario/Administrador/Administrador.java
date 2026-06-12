package clinica_back.clinica_back.features.Usuario.Administrador;

import clinica_back.clinica_back.features.Usuario.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "Admin")
@PrimaryKeyJoinColumn(name = "id_usuario")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Administrador extends Usuario {

}
