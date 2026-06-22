package clinica_back.clinica_back.shared.util;

import java.time.LocalDate;
import java.time.Period;

public class DataUtil {

    public static Integer calcularIdade(LocalDate dataNascimento) {
    return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

}
