import "./Dashboard.css";

import { useState } from "react";

function Dashboard() {

  /* CONTROLE MENU */
  const [menuAberto, setMenuAberto] = useState(false);

  return (
    <div className="dashboard-container">

      {/* BOTÃO MENU */}
      <button
        className="menu-button"
        onClick={() => setMenuAberto(!menuAberto)}
      >
        ☰
      </button>

      {/* SIDEBAR */}
      <aside
        className={`sidebar ${
          menuAberto ? "open" : "closed"
        }`}
      >

        {/* LOGO */}
        <div className="logo">
          <h2>MedSync</h2>
        </div>

        {/* MENU */}
        <nav className="menu">

          <ul>

            <li className="active">
              Dashboard
            </li>

            <li>
              Pacientes
            </li>

            <li>
              Consultas
            </li>

            <li>
              Calendário
            </li>

            <li>
              Prontuários
            </li>

            <li>
              Usuários
            </li>

          </ul>

        </nav>

      </aside>

      {/* CONTEÚDO */}
      <main
        className={`main-content ${
          menuAberto ? "expanded" : ""
        }`}
      >

        {/* CARDS */}
        <section className="cards-container">

          {/* CARD 1 */}
          <div className="card">
            <h2>12</h2>
            <p>Consultas Hoje</p>
          </div>

          {/* CARD 2 */}
          <div className="card">
            <h2>245</h2>
            <p>Pacientes Cadastrados</p>
          </div>

          {/* CARD 3 */}
          <div className="card">
            <h2>87</h2>
            <p>Consultas no Mês</p>
          </div>

        </section>

        {/* TABELA CONSULTAS */}
        <section className="appointments-section">

          <h2>Próximos Agendamentos</h2>

          <table>

            <thead>
              <tr>
                <th>Paciente</th>
                <th>Horário</th>
                <th>Médico</th>
              </tr>
            </thead>

            <tbody>

              <tr>
                <td>Maria Silva</td>
                <td>14:00</td>
                <td>Dr. João</td>
              </tr>

              <tr>
                <td>Carlos Henrique</td>
                <td>15:00</td>
                <td>Dra. Ana</td>
              </tr>

              <tr>
                <td>Fernanda Lima</td>
                <td>16:30</td>
                <td>Dr. Pedro</td>
              </tr>

            </tbody>

          </table>

        </section>

      </main>

    </div>
  );
}

export default Dashboard;