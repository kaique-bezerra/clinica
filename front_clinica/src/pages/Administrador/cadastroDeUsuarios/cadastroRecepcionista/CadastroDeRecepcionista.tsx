import { useState } from "react";
import MenuLateral from "../../Componentes/MenuLateral";
import "./CadastroDeRecepcionistaAdmin.css";

function CadastroDeRecepcionistaAdmin() {
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

  // --- SUBMIT CONECTADO AO SPRING BOOT ---
  async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();

    // Recupera o token salvo no login
    const token = localStorage.getItem("token");

    // CORREÇÃO 1: Monta o payload convertendo o campo número para Number
    const payloadValido = {
      ...formData,
      numero: Number(formData.numero)
    };

    try {
      const response = await fetch("http://localhost:8080/recepcionista", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}` 
        },
        body: JSON.stringify(payloadValido), 
      });

      if (!response.ok) {
        if (response.status === 403) {
          alert("Erro 403: O perfil ADMINISTRADOR não tem permissão para acessar esta rota no backend ou o token expirou.");
          return;
        }
        
        const textError = await response.text();
        console.error("Erro retornado pelo backend:", textError);
        alert("Erro ao cadastrar o recepcionista. Verifique os logs do console.");
        return;
      }

      alert("Recepcionista cadastrado com sucesso! 🎉");
      
      // Reseta o formulário
      setFormData({
        nome: "", sobrenome: "", telefone: "", cpf: "", email: "", senha: "",
        rua: "", numero: "", bairro: "", cidade: "", estado: "", cep: "",
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
          <h1>Cadastro de Recepcionistas</h1>
          <p>Preencha os dados do recepcionista organizados por categoria.</p>
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
            </fieldset> {/* 👈 CORREÇÃO: Tag reinserida aqui para fechar os dados pessoais */}

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

            <button type="submit" className="save-button">
              Concluir Cadastro do Recepcionista
            </button>
          </form>
        </section>
      </main>
    </div>
  );
}

export default CadastroDeRecepcionistaAdmin;