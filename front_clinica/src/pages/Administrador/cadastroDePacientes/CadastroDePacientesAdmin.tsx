import { useState } from "react";
import "../../Recepcionista/cadastroDePacientes/Pacientes.css";
import MenuLateral from "../Componentes/MenuLateral";

function CadastroDePacientesAdmin() {

   const [menuAberto, setMenuAberto] = useState(false);

  const [formData, setFormData] = useState({
    nome: "",
    sobrenome: "",
    telefone: "",
    cpf: "",
    email: "",
    senha: "",
    rua: "",
    numero: "",
    bairro: "",
    cidade: "",
    estado: "",
    cep: "",
    sexo: "",
    profissao: "",
    dataNascimento: "",
    convenio: {
      plano: "",
      numero: "",
      data: ""
    },
    dadosClinicos: {
      tipoSanguineo: "",
      altura: "",
      peso: "",
      alergias: [{ nome: "" }],
      doencasCronicas: [{ nome: "" }]
    }
  });

  // --- MÁSCARAS DE INPUT ---
  const aplicarMarcaraCPF = (value: string) => {
    return value
      .replace(/\D/g, "")           
      .substring(0, 11)             
      .replace(/(\d{3})(\d)/, "$1.$2")
      .replace(/(\d{3})(\d)/, "$1.$2")
      .replace(/(\d{3})(\d{1,2})$/, "$1-$2");
  };

  const aplicarMascaraTelefone = (value: string) => {
    return value
      .replace(/\D/g, "")
      .replace(/^(\d{2})(\d)/g, "($1)$2")
      .replace(/(\d{5})(\d)/, "$1-$2")
      .substring(0, 14);
  };

  const aplicarMascaraCEP = (value: string) => {
    return value
      .replace(/\D/g, "")
      .replace(/^(\d{5})(\d)/, "$1-$2")
      .substring(0, 9);
  };

  // --- HANDLERS DE ALTERAÇÃO ---
  function handleChange(event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) {
    const { name, value } = event.target;

    let valorFormatado = value;
    if (name === "cpf") valorFormatado = aplicarMarcaraCPF(value);
    if (name === "telefone") valorFormatado = aplicarMascaraTelefone(value);
    if (name === "cep") valorFormatado = aplicarMascaraCEP(value);

    setFormData((prev) => ({
      ...prev,
      [name]: valorFormatado
    }));
  }

  function handleNestedChange(section: "convenio" | "dadosClinicos", field: string, value: string) {
    setFormData((prev) => ({
      ...prev,
      [section]: {
        ...prev[section],
        [field]: value
      }
    }));
  }

  // --- SUBMIT CONECTADO AO SPRING BOOT ---
  async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();

    // Recupera o token salvo no login
    const token = localStorage.getItem("token");

    const convenioValido = formData.convenio.plano.trim() !== "" 
      ? formData.convenio : null;

    // Monta o payload definitivo convertendo o que é número de verdade
    const payloadValido = {
      ...formData,
      numero: Number(formData.numero), 
      convenio: convenioValido,
      dadosClinicos: {
        ...formData.dadosClinicos,
        altura: Number(formData.dadosClinicos.altura),
        peso: Number(formData.dadosClinicos.peso) 
      }
    };

    console.log("Payload pronto e correto para o Spring Boot:", payloadValido);

    try {
      const response = await fetch("http://localhost:8080/paciente", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          // 1. INJEÇÃO DO TOKEN: Envia as credenciais que o Spring Security exige
          "Authorization": `Bearer ${token}` 
        },
        body: JSON.stringify(payloadValido),
      });

      // 2. CORREÇÃO: Trata respostas de erro antes de executar o .json() vazio
      if (!response.ok) {
        if (response.status === 403) {
          alert("Erro 403: Você não tem permissão ou seu token expirou para realizar cadastros.");
          return;
        }
        
        // Se houver algum corpo de erro vindo do backend tenta extrair
        const textError = await response.text();
        console.error("Erro retornado pelo backend:", textError);
        alert("Erro ao cadastrar o paciente. Verifique os logs do console.");
        return;
      }

      // Se a requisição retornou sucesso (200-299)
      alert("Paciente cadastrado com sucesso! 🎉");
      
      // Reseta o formulário
      setFormData({
        nome: "", sobrenome: "", telefone: "", cpf: "", email: "", senha: "",
        rua: "", numero: "", bairro: "", cidade: "", estado: "", cep: "",
        sexo: "", profissao: "", dataNascimento: "",
        convenio: { plano: "", numero: "", data: "" },
        dadosClinicos: { tipoSanguineo: "", altura: "", peso: "", alergias: [{ nome: "" }], doencasCronicas: [{ nome: "" }] }
      });
        
    } catch (error) {
      console.error("Erro na requisição de rede:", error);
      alert("Não foi possível conectar ao servidor. Certifique-se de que o backend Spring Boot está rodando.");
    }
  }

  return (
    <div className="pacientes-container">
      <MenuLateral menuAberto={menuAberto} setMenuAberto={setMenuAberto} />

      <main className={`main-content ${menuAberto ? "expanded" : ""}`}>
        <section className="page-header">
          <h1>Cadastro de Pacientes</h1>
          <p>Preencha os dados do paciente organizados por categoria.</p>
        </section>

        <section className="form-section">
          <form className="patient-form" onSubmit={handleSubmit}>
            
            {/* Bloco 1: Dados Pessoais */}
            <fieldset className="form-group-box">
              <legend>Dados Pessoais</legend>
              <div className="form-row">
                <div className="input-group">
                  <label>Nome</label>
                  <input type="text" name="nome" value={formData.nome} onChange={handleChange} required placeholder="Ex: Kaique" />
                </div>
                <div className="input-group">
                  <label>Sobrenome</label>
                  <input type="text" name="sobrenome" value={formData.sobrenome} onChange={handleChange} required placeholder="Ex: Bezerra de Oliveira" />
                </div>
              </div>

              <div className="form-row">
                <div className="input-group">
                  <label>CPF</label>
                  <input type="text" name="cpf" value={formData.cpf} onChange={handleChange} required placeholder="000.000.000-00" />
                </div>
                <div className="input-group">
                  <label>Telefone</label>
                  <input type="text" name="telefone" value={formData.telefone} onChange={handleChange} required placeholder="(00)00000-0000" />
                </div>
              </div>

              <div className="form-row">
                <div className="input-group">
                  <label>E-mail</label>
                  <input type="email" name="email" value={formData.email} onChange={handleChange} required placeholder="nome@email.com" />
                </div>
                <div className="input-group">
                  <label>Senha de Acesso</label>
                  <input type="password" name="senha" value={formData.senha} onChange={handleChange} required placeholder="Defina uma senha" />
                </div>
              </div>

              <div className="form-row3">
                <div className="input-group">
                  <label>Data de Nascimento</label>
                  <input type="date" name="dataNascimento" value={formData.dataNascimento} onChange={handleChange} required />
                </div>
                <div className="input-group">
                  <label>Sexo</label>
                  <select name="sexo" value={formData.sexo} onChange={handleChange} required>
                    <option value="">Selecione</option>
                    <option value="M">Masculino</option>
                    <option value="F">Feminino</option>
                    <option value="O">Outro</option>
                  </select>
                </div>
                <div className="input-group">
                  <label>Profissão</label>
                  <input type="text" name="profissao" value={formData.profissao} onChange={handleChange} placeholder="Ex: Frentista" />
                </div>
              </div>
            </fieldset>

            {/* Bloco 2: Endereço */}
            <fieldset className="form-group-box">
              <legend>Endereço Residencial</legend>
              <div className="form-row3">
                <div className="input-group val-cep">
                  <label>CEP</label>
                  <input type="text" name="cep" value={formData.cep} onChange={handleChange} required placeholder="00000-000" />
                </div>
                <div className="input-group val-rua">
                  <label>Rua / Logradouro</label>
                  <input type="text" name="rua" value={formData.rua} onChange={handleChange} required placeholder="Ex: Rua Antônio Bernardo" />
                </div>
                <div className="input-group val-num">
                  <label>Número</label>
                  <input type="number" name="numero" value={formData.numero} onChange={handleChange} required placeholder="123" />
                </div>
              </div>

              <div className="form-row3">
                <div className="input-group">
                  <label>Bairro</label>
                  <input type="text" name="bairro" value={formData.bairro} onChange={handleChange} required placeholder="Ex: José Pinheiro" />
                </div>
                <div className="input-group">
                  <label>Cidade</label>
                  <input type="text" name="cidade" value={formData.cidade} onChange={handleChange} required placeholder="Ex: Campina Grande" />
                </div>
                <div className="input-group">
                  <label>Estado (UF)</label>
                  <input type="text" name="estado" value={formData.estado} onChange={handleChange} required placeholder="Ex: PB" maxLength={2} />
                </div>
              </div>
            </fieldset>

            {/* Bloco 3: Convênio */}
            <fieldset className="form-group-box">
              <legend>Dados do Convênio</legend>
              <div className="form-row3">
                <div className="input-group">
                  <label>Plano</label>
                  <input type="text" value={formData.convenio.plano} onChange={(e) => handleNestedChange("convenio", "plano", e.target.value)} placeholder="Ex: Unimed / Particular" />
                </div>
                <div className="input-group">
                  <label>Número da Carteira</label>
                  <input type="text" value={formData.convenio.numero} onChange={(e) => handleNestedChange("convenio", "numero", e.target.value)} placeholder="Nº da carteirinha" />
                </div>
                <div className="input-group">
                  <label>Validade do Plano</label>
                  <input type="date" value={formData.convenio.data} onChange={(e) => handleNestedChange("convenio", "data", e.target.value)} />
                </div>
              </div>
            </fieldset>

            {/* Bloco 4: Dados Clínicos */}
            <fieldset className="form-group-box">
              <legend>Informações Clínicas Básicas</legend>
              <div className="form-row3">
                <div className="input-group">
                  <label>Tipo Sanguíneo</label>
                  <input type="text" value={formData.dadosClinicos.tipoSanguineo} onChange={(e) => handleNestedChange("dadosClinicos", "tipoSanguineo", e.target.value)} placeholder="Ex: O+" maxLength={3} />
                </div>
                <div className="input-group">
                  <label>Altura (m)</label>
                  <input type="number" step="0.01" value={formData.dadosClinicos.altura} onChange={(e) => handleNestedChange("dadosClinicos", "altura", e.target.value)} placeholder="Ex: 1.82" />
                </div>
                <div className="input-group">
                  <label>Peso (kg)</label>
                  <input type="number" step="0.01" value={formData.dadosClinicos.peso} onChange={(e) => handleNestedChange("dadosClinicos", "peso", e.target.value)} placeholder="Ex: 69.90" />
                </div>
              </div>
            </fieldset>

            <button type="submit" className="save-button">
              Concluir Cadastro do Paciente
            </button>
          </form>
        </section>
      </main>
    </div>
  );
}

export default CadastroDePacientesAdmin;