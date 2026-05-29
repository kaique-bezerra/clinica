import { useState } from "react";
import MenuLateral from "../Componentes/MenuLateral";
import "./CadastroDeUsuarioAdmin.css";

function CadastroDeUsuarioAdmin() {
  const [menuAberto, setMenuAberto] = useState(false);

  const [nome,setNome] = useState(" ");
  const [email,setEmail] = useState(" ");
  const [telefone,setTelefone] = useState(" ");
  const [senha,setSenha] = useState(" ");
  const [perfil, setPerfil] = useState(" ");


  function handleSubmit(event: React.SubmitEvent<HTMLFormElement>){
    event.preventDefault();

    console.log("Usuário cadastrado com sucesso!");

  }
    const [mensagem, setMensagem] = useState("");


    function agendarConsulta() {

    if (!nome || !email || !telefone || !senha || !perfil) {
      setMensagem(" Preencha todos os campos!");
      return;
    }

    setMensagem(" Usuário cadastrado com sucesso!");

    setNome("");
    setEmail("");
    setTelefone("");
    setSenha("");
    setPerfil("");
  }

    let mensagemTela = null;

  if (mensagem !== "") {
    mensagemTela = (
      <p style={{ marginTop: "10px" }}>
        {mensagem}
      </p>
    );
  }
  
  return (
    <div className="usuarios-container">
      <MenuLateral menuAberto={menuAberto}
      setMenuAberto={setMenuAberto}/>

      <main
        className={`main-content ${
          menuAberto ? "expanded" : ""
        }`}
      >
        <section className="page-header">
          <h1>Cadastro de Usuários</h1>

          <p>
            Cadastre novos usuários e gerencie os
            acessos do sistema.
          </p>
        </section>

        <section className="form-section">
          <form className="user-form" onSubmit={handleSubmit}>
            <div className="form-row">
              <div className="input-group">
                <label>Nome Completo</label>

                <input
                  type="text"
                  placeholder="Digite o nome do usuário"
                  value={nome} onChange={(e)=> setNome(e.target.value)}
                />
              </div>

              <div className="input-group">
                <label>Email</label>

                <input
                  type="email"
                  placeholder="Digite o email"
                  value={email} onChange={(e)=> setEmail(e.target.value)}

                />
              </div>
            </div>

            <div className="form-row">
              <div className="input-group">
                <label>Telefone</label>

                <input
                  type="text"
                  placeholder="(83) 99999-9999"
                  value={telefone} onChange={(e)=> setTelefone(e.target.value)}
                />
              </div>

              <div className="input-group">
                <label>Perfil</label>

                <select value={perfil} onChange={(e)=> setPerfil(e.target.value)}>
                  <option>Selecione</option>
                  <option>Administrador</option>
                  <option>Recepcionista</option>
                  <option>Médico</option>
                </select>
              </div>
            </div>

            <div className="form-row">
              <div className="input-group">
                <label>Senha</label>

                <input
                  type="password"
                  placeholder="Digite a senha"
                />
              </div>

              <div className="input-group">
                <label>Confirmar Senha</label>

                <input
                  type="password"
                  placeholder="Confirme a senha"
                  value={senha} onChange={(e)=> setSenha(e.target.value)}
                />
              </div>
            </div>

            <button
              type="submit"
              className="save-button"
              onClick={agendarConsulta}
            >
              Cadastrar Usuário
            </button>
            {mensagemTela}
          </form>
        </section>

        <section className="users-table-section">
          <div className="table-header">
            <h2>Usuários Cadastrados</h2>

            <input
              type="text"
              placeholder="Buscar usuário"
              className="search-input"
            />
          </div>

          <table>
            <thead>
              <tr>
                <th>Nome</th>
                <th>Email</th>
                <th>Telefone</th>
                <th>Perfil</th>
              </tr>
            </thead>

            <tbody>
              <tr>
                <td>João Silva</td>
                <td>joao@email.com</td>
                <td>(83) 99999-1111</td>
                <td>Administrador</td>
              </tr>

              <tr>
                <td>Maria Fernanda</td>
                <td>maria@email.com</td>
                <td>(83) 98888-2222</td>
                <td>Recepcionista</td>
              </tr>

              <tr>
                <td>Carlos Henrique</td>
                <td>carlos@email.com</td>
                <td>(83) 97777-3333</td>
                <td>Médico</td>
              </tr>
            </tbody>
          </table>
        </section>
      </main>
    </div>
  );
}

export default CadastroDeUsuarioAdmin;