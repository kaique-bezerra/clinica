package clinica_back.clinica_back.shared.util;

import java.util.Objects;

public class AuditoriaUtil {

    public static String registrarAlteracao(String campo, Object antigo, Object novo) {
        if (Objects.equals(antigo, novo)) {
            return "";
        }

        return campo + " (" + antigo + " -> " + novo + "); ";
    }
}
