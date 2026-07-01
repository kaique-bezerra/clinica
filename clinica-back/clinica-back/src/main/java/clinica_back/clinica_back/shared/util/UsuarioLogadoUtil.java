package clinica_back.clinica_back.shared.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import clinica_back.clinica_back.features.Usuario.Usuario;

public class UsuarioLogadoUtil {

    public static Usuario getUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof Usuario usuario)) {
            throw new IllegalStateException("Nenhum usuário autenticado.");
        }
        return usuario;
    }

}
