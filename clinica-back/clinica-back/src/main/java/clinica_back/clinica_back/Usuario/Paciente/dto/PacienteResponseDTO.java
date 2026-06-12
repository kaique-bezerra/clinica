package clinica_back.clinica_back.Usuario.Paciente.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PacienteResponseDTO {

    private Long id;

    private String nome;

    private String sobrenome;

    private String email;

    private String telefone;

    private String profissao;

    private Date data_nascimento;

    private String tipoSanguineo;

    private BigDecimal altura;

    private BigDecimal peso;
}
