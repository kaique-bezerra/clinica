package clinica_back.clinica_back.features.Auth;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import clinica_back.clinica_back.features.Usuario.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String gerarToken(Usuario usuario) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.builder()
                .subject(usuario.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .claim("perfil", usuario.getPerfil().name())
                .signWith(key)
                .compact();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    private Claims extrairClaims(String token) {
        return Jwts.parser().verifyWith((SecretKey) getSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    public String extrairEmail(String token) {
        return extrairClaims(token).getSubject();
    }

    private Date extrairExpiracao(String token) {
        return extrairClaims(token).getExpiration();
    }

    private Boolean tokenExpirado(String token) {
        return extrairExpiracao(token).before(new Date());
    }

    public Boolean validarToken(String token, Usuario usuario) {
        String email = extrairEmail(token);
        return email.equals(usuario.getEmail()) && !tokenExpirado(token);
    }
}
