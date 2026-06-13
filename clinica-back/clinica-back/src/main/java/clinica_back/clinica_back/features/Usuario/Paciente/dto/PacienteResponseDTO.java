package clinica_back.clinica_back.features.Usuario.Paciente.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

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

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    private Integer idade;

    private String tipoSanguineo;

    private BigDecimal altura;

    private BigDecimal peso;
}
