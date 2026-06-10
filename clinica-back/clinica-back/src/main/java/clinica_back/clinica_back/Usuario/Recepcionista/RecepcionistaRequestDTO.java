package clinica_back.clinica_back.Usuario.Recepcionista;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecepcionistaRequestDTO {

    private String nome;
    private String sobrenome;
    private String email;
    private String telefone;
    private String cpf;
    private String senha;

    private String rua;
    private Integer numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
}
