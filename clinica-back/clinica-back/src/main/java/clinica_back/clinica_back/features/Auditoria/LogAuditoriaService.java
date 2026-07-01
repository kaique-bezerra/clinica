package clinica_back.clinica_back.features.Auditoria;

import org.springframework.stereotype.Service;

import clinica_back.clinica_back.features.Usuario.Usuario;
import clinica_back.clinica_back.shared.util.UsuarioLogadoUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogAuditoriaService {

    private final LogAuditoriaRepository logAuditoriaRepository;

    public void registrar(AcaoAuditoriaEnum acao, String entidadeAfetada, Long idAfetado, String descricao) {
        LogAuditoria log = new LogAuditoria();
        Usuario usuario = UsuarioLogadoUtil.getUsuarioLogado();

        log.setUsuario(usuario);
        log.setAcao(acao);
        log.setEntidadeAfetada(entidadeAfetada);
        log.setDescricao(descricao);
        log.setIdAfetado(idAfetado);
        log.setPerfilUsuario(usuario.getPerfil());
        log.setEmailUsuario(usuario.getEmail());

        logAuditoriaRepository.save(log);
    }
}
