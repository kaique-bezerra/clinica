import "./Medicos-Admin.css";
import { Link } from "react-router-dom";

import { useState } from "react";

function MedicosAdmin() {
  const [menuAberto, setMenuAberto] = useState(false);

  return (
    <div className="medicos-container">
      <button
        className="menu-button"
        onClick={() => setMenuAberto(!menuAberto)}
      >
        ☰ Menu
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

            <Link className="link" to="/cadastroDePacientes-admin"><li>
                Cadastro de pacientes
            </li></Link>

            <Link className="link" to="/cadastroDeUsuarios-admin"><li>
                Cadastro de Usuários
            </li></Link>
            
            <Link className="link" to="/cadastroDeMedicos-admin"><li className="active">
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
          <h1>Gerenciamento de Médicos</h1>

          <p>
            Cadastre, visualize e gerencie os médicos
            disponíveis no sistema.
          </p>
        </section>

        <section className="form-section">
          <form className="doctor-form">
            <div className="form-row">
              <div className="input-group">
                <label>Nome Completo</label>

                <input
                  type="text"
                  placeholder="Digite o nome do médico"
                />
              </div>

              <div className="input-group">
                <label>CRM</label>

                <input
                  type="text"
                  placeholder="Digite o CRM"
                />
              </div>
            </div>

            <div className="form-row">
              <div className="input-group">
                <label>Especialidade</label>

                <input
                  type="text"
                  placeholder="Digite a especialidade"
                />
              </div>

              <div className="input-group">
                <label>Telefone</label>

                <input
                  type="text"
                  placeholder="(83) 99999-9999"
                />
              </div>
            </div>

            <div className="form-row">
              <div className="input-group">
                <label>Email</label>

                <input
                  type="email"
                  placeholder="Digite o email"
                />
              </div>

              <div className="input-group">
                <label>Status</label>

                <select>
                  <option>Selecione</option>
                  <option>Ativo</option>
                  <option>Inativo</option>
                </select>
              </div>
            </div>

            <button
              type="button"
              className="save-button"
            >
              Cadastrar Médico
            </button>
          </form>
        </section>

        <section className="doctors-table-section">
          <div className="table-header">
            <h2>Médicos Cadastrados</h2>

            <input
              type="text"
              placeholder="Buscar médico"
              className="search-input"
            />
          </div>

          <table>
            <thead>
              <tr>
                <th>Nome</th>
                <th>CRM</th>
                <th>Especialidade</th>
                <th>Telefone</th>
                <th>Status</th>
              </tr>
            </thead>

            <tbody>
              <tr>
                <td>Dr. João Henrique</td>
                <td>CRM 12345</td>
                <td>Cardiologia</td>
                <td>(83) 99999-1111</td>
                <td>Ativo</td>
              </tr>

              <tr>
                <td>Dra. Fernanda Lima</td>
                <td>CRM 54321</td>
                <td>Pediatria</td>
                <td>(83) 98888-2222</td>
                <td>Ativo</td>
              </tr>

              <tr>
                <td>Dr. Carlos Eduardo</td>
                <td>CRM 98765</td>
                <td>Ortopedia</td>
                <td>(83) 97777-3333</td>
                <td>Inativo</td>
              </tr>
            </tbody>
          </table>
        </section>
      </main>
    </div>
  );
}

export default MedicosAdmin;