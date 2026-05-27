import "./CadastroDeUsuarioAdmin.css";
import { Link } from "react-router-dom";

import { useState } from "react";

function CadastroDeUsuarioAdmin() {
  const [menuAberto, setMenuAberto] = useState(false);

  return (
    <div className="usuarios-container">
      <button
        className="menu-button"
        onClick={() => setMenuAberto(!menuAberto)}
      >
        ☰
      </button>

      <aside
        className={`sidebar ${
          menuAberto ? "open" : "closed"
        }`}
      >
        <div className="logo">
          <h2>MedSync</h2>
        </div>

        <nav className="menu">
          <ul>
            <Link className="link" to="/dashboard-admin"><li>
              Dashboard
            </li></Link>
            
            <Link className="link" to="/calendario-admin"><li>
              Calendário
            </li></Link>

            <Link className="link" to="/prontuario-admin"><li>
              prontuário
            </li></Link>

            <Link className="link" to="/agendamento-admin"><li>
                Agendamento de Consultas
            </li></Link>

            <Link className="link" to="/cadastroDePacientes-admin"><li >
                Cadastro de pacientes
            </li></Link>

            <Link className="link" to="/cadastroDeUsuarios-admin"><li className="active">
                Cadastro de Usuários
            </li></Link>
            
            <Link className="link" to="/cadastroDeMedicos-admin"><li>
                Gerenciamento de Médicos
            </li></Link>
            <Link className="link" to="/perfil-admin">
            <li>
              Sair
              </li>    
            </Link>

          </ul>
        </nav>
      </aside>

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
          <form className="user-form">
            <div className="form-row">
              <div className="input-group">
                <label>Nome Completo</label>

                <input
                  type="text"
                  placeholder="Digite o nome do usuário"
                />
              </div>

              <div className="input-group">
                <label>Email</label>

                <input
                  type="email"
                  placeholder="Digite o email"
                />
              </div>
            </div>

            <div className="form-row">
              <div className="input-group">
                <label>Telefone</label>

                <input
                  type="text"
                  placeholder="(83) 99999-9999"
                />
              </div>

              <div className="input-group">
                <label>Perfil</label>

                <select>
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
                />
              </div>
            </div>

            <button
              type="button"
              className="save-button"
            >
              Cadastrar Usuário
            </button>
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