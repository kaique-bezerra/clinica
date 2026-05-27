import "./Pacientes.css";
import { Link } from "react-router-dom";


import { useState } from "react";

function Pacientes() {

  const [menuAberto, setMenuAberto] = useState(false);

  return (
    <div className="pacientes-container">

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
            <Link className="link" to="/dashboard">
            <li>
              Dashboard
            </li>
                </Link>
            <li className="active">
              Cadastro de Pacientes
            </li>
                <Link className="link" to="/agendamento">
            <li>
              Agendamento
            </li>
                </Link>
                <Link className="link" to="/calendario">
            <li>
              Calendário
            </li>
                </Link>
                <Link className="link" to="/">
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

        <div>
        <section className="page-header">
          <h1>Cadastro de Pacientes</h1>

          <p>
            Cadastre novos pacientes e visualize os
            registros já existentes no sistema.
          </p>

        </section>
        </div>
        <div>
        <section className="form-section">

          <form className="patient-form">

            <div className="form-row">

              <div className="input-group">
                <label>Nome Completo</label>

                <input
                  type="text"
                  placeholder="Digite o nome do paciente"
                />
              </div>

              <div className="input-group">
                <label>CPF</label>

                <input
                  type="text"
                  placeholder="000.000.000-00"
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
                <label>Data de Nascimento</label>

                <input type="date" />
              </div>

            </div>

            <div className="form-row">

              <div className="input-group full-width">
                <label>Convênio</label>

                <input
                  type="text"
                  placeholder="Digite o convênio"
                />
              </div>

            </div>

            <button
              type="button"
              className="save-button"
            >
              Cadastrar Paciente
            </button>

          </form>

        </section>
        </div>

        <section className="patients-table-section">

          <div className="table-header">

            <h2>Pacientes Cadastrados</h2>

            <input
              type="text"
              placeholder="Buscar por nome ou CPF"
              className="search-input"
            />

          </div>

          <table>

            <thead>

              <tr>
                <th>Nome</th>
                <th>CPF</th>
                <th>Telefone</th>
                <th>Convênio</th>
              </tr>

            </thead>

            <tbody>

              <tr>
                <td>Maria Silva</td>
                <td>123.456.789-00</td>
                <td>(83) 99999-1111</td>
                <td>Unimed</td>
              </tr>

              <tr>
                <td>Carlos Henrique</td>
                <td>987.654.321-00</td>
                <td>(83) 98888-2222</td>
                <td>Hapvida</td>
              </tr>

              <tr>
                <td>Fernanda Lima</td>
                <td>741.852.963-00</td>
                <td>(83) 97777-3333</td>
                <td>Particular</td>
              </tr>

            </tbody>

          </table>

        </section>

      </main>

    </div>
  );
}  

export default Pacientes;