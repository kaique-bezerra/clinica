package clinica_back.clinica_back.features.Usuario;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
public class Usuario implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_usuario")
  private Long idUsuario;

  @Column(nullable = false, length = 30)
  private String nome;

  @Column(nullable = false, length = 50)
  private String sobrenome;

  @Column(nullable = false, unique = true, length = 50)
  private String email;

  @Column(nullable = false, length = 20)
  private String telefone;

  @Column(nullable = false, unique = true, length = 14)
  private String cpf;

  @Column(nullable = false, length = 255)
  private String senha;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 13)
  private PerfilUsuario perfil;

  @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
  private Endereco endereco;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + perfil.name()));
  }

  @Override
  public String getPassword() {
    return senha;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true; 
  }

  @Override
  public boolean isAccountNonLocked() {
    return true; 
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true; 
  }

  @Override
  public boolean isEnabled() {
    return true; 
  }

}
