package clinica_back.clinica_back.features.Usuario;

import clinica_back.clinica_back.features.Usuario.Endereco.Endereco;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder

public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id_usuario;

  @NotBlank
  @Size(max = 30)
  @Column(nullable = false, length = 30)
  private String nome;

  @NotBlank
  @Size(max = 50)
  @Column(nullable = false, length = 50)
  private String sobrenome;

  @Email
  @NotBlank
  @Size(max = 50)
  @Column(nullable = false, unique = true, length = 50)
  private String email;

  @NotBlank
  @Size(max = 20)
  @Column(nullable = false, length = 20)
  private String telefone;

  @NotBlank
  @Size(max = 14)
  @Column(nullable = false, unique = true, length = 14)
  private String cpf;

  @NotBlank
  @Size(max = 255)
  @Column(nullable = false, length = 255)
  private String senha;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 13)
  private PerfilUsuario perfil;

  // o @ note null pode da problema porque o hibernaide valida antes de persistir
  @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
  private Endereco endereco;

}
