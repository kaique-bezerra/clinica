package clinica_back.clinica_back.features.Usuario.Paciente.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import clinica_back.clinica_back.features.Usuario.Paciente.DadosClinicos.dto.dto.DadosClinicosResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PacienteCompletoResponseDTO {

    private Long id;

    private String nome;

    private String sobrenome;

    private String email;

    private String telefone;

    private Character sexo;

    private String profissao;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    private Integer idade;

    private String rua;

    private Integer numero;

    private String bairro;

    private String cidade;

    private String estado;

    private String cep;

    private String plano;

    private String numeroConvenio;

    private LocalDate data;

    private DadosClinicosResponseDTO dadosClinicos;
}
