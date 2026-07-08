package clinica_back.clinica_back.features.Auth.userdetails;

import clinica_back.clinica_back.features.Usuario.Usuario;
import clinica_back.clinica_back.features.Usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
                implements UserDetailsService {

        private final UsuarioRepository usuarioRepository;

        @Override
        public UserDetails loadUserByUsername(String email)
                        throws UsernameNotFoundException {

                Usuario usuario = usuarioRepository.findByEmail(email)
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                "Usuário não encontrado"));

                return new CustomUserDetails(usuario);
        }
}