import "./Calendario.css";
import { Link } from "react-router-dom";

import { useState } from "react";

function Calendario() {

  const [menuAberto, setMenuAberto] = useState(false);

  const dias = [
    "Segunda",
    "Terça",
    "Quarta",
    "Quinta",
    "Sexta",
  ];

  return (
    <div className="calendario-container">

      {/* BOTÃO MENU */}
      <button
        className="menu-button"
        onClick={() => setMenuAberto(!menuAberto)}
      >
        ☰
      </button>

      {/* SIDEBAR */}
      <aside className={`sidebar ${menuAberto ? "open" : "closed"}`}>

        <div className="logo">
          <h2>MedSync</h2>
        </div>

        <nav className="menu">

          <ul>
                <Link className="link" to="/dashboard">
            <li>Dashboard</li>
                </Link>
            <Link className="link" to="/pacientes">
            <li>Pacientes</li>
                </Link>
                <Link className="link" to="/agendamento">
            <li>Agendamento</li>
                </Link>
                <Link className="link" to="/calendario">
            <li className="active">Calendário</li>
                </Link>
                <Link className="link" to="/prontuario">
            <li>Prontuário</li>
                </Link>


          </ul>

        </nav>

      </aside>

      {/* CONTEÚDO */}
      <main className={`main-content ${menuAberto ? "expanded" : ""}`}>

        {/* HEADER */}
        <section className="page-header">

          <h1>Calendário de Consultas</h1>

          <p>
            Visualização semanal dos agendamentos da clínica.
          </p>

        </section>

        {/* CALENDÁRIO */}
        <section className="calendar-grid">

          {dias.map((dia, index) => (
            <div key={index} className="day-card">

              <h3>{dia}</h3>

              <div className="appointment">

                <p>14:00 - Maria Silva</p>
                <p>Dr. João</p>

              </div>

              <div className="appointment">

                <p>15:00 - Carlos Henrique</p>
                <p>Dra. Ana</p>

              </div>

            </div>
          ))}

        </section>

      </main>

    </div>
  );
}

export default Calendario;