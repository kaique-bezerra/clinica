import "./Agendamento.css";
import { Link } from "react-router-dom";

import { useState } from "react";

function Agendamento() {

  const [menuAberto, setMenuAberto] = useState(false);

  const [paciente, setPaciente] = useState("");
  const [medico, setMedico] = useState("");
  const [data, setData] = useState("");
  const [hora, setHora] = useState("");

  const [mensagem, setMensagem] = useState("");

  function agendarConsulta() {

    if (!paciente || !medico || !data || !hora) {
      setMensagem("❌ Preencha todos os campos!");
      return;
    }

    setMensagem("✅ Consulta agendada com sucesso!");

    setPaciente("");
    setMedico("");
    setData("");
    setHora("");
  }

  return (
    <div className="agendamento-container">

      <button
        className="menu-button"
        onClick={() => setMenuAberto(!menuAberto)}
      >
        ☰ Menu
      </button>

      <aside className={`sidebar ${menuAberto ? "open" : "closed"}`}>

        <div className="logo">
          <h2>MedSync</h2>
        </div>

        <nav className="menu">
          <ul>

            <li>
              <Link className="link" to="/dashboard">
                Dashboard
              </Link>
            </li>
                          <Link className="link" to="/pacientes">

            <li>
                cadastro de pacientes
            </li>
              </Link>
              <Link className="link" to="/agendamento">
            <li className="active">
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

      <main className={`main-content ${menuAberto ? "expanded" : ""}`}>

        <section className="page-header">

          <h1>Agendamento de Consultas</h1>

          <p>Selecione paciente, médico, data e horário.</p>

        </section>

        <section className="form-section">

          <select
            value={paciente}
            onChange={(e) => setPaciente(e.target.value)}
          >
            <option value="">Selecione o paciente</option>
            <option>Maria Silva</option>
            <option>Carlos Henrique</option>
            <option>Fernanda Lima</option>
          </select>

          <select
            value={medico}
            onChange={(e) => setMedico(e.target.value)}
          >
            <option value="">Selecione o médico</option>
            <option>Dr. João</option>
            <option>Dra. Ana</option>
            <option>Dr. Pedro</option>
          </select>

          <input
            type="date"
            value={data}
            onChange={(e) => setData(e.target.value)}
          />

          <input
            type="time"
            value={hora}
            onChange={(e) => setHora(e.target.value)}
          />

          <button onClick={agendarConsulta}>
            Agendar Consulta
          </button>

          {mensagem && (
            <p style={{ marginTop: "10px" }}>
              {mensagem}
            </p>
          )}

        </section>

        <section className="table-section">

          <h2>Consultas Agendadas</h2>

          <table>

            <thead>
              <tr>
                <th>Paciente</th>
                <th>Médico</th>
                <th>Data</th>
                <th>Hora</th>
                <th>Status</th>
              </tr>
            </thead>

            <tbody>

              <tr>
                <td>Maria Silva</td>
                <td>Dr. João</td>
                <td>20/05</td>
                <td>14:00</td>
                <td>Agendada</td>
              </tr>

              <tr>
                <td>Carlos Henrique</td>
                <td>Dra. Ana</td>
                <td>20/05</td>
                <td>15:00</td>
                <td>Agendada</td>
              </tr>

            </tbody>

          </table>

        </section>

      </main>

    </div>
  );
}

export default Agendamento;