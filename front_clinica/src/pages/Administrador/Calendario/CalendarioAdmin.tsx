import "../../Recepcionista/Calendário/Calendario.css";
import { Link } from "react-router-dom";
import { useState } from "react";

function CalendarioAdmin() {

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
            <Link className="link" to="/dashboard-admin"><li>
              Dashboard
            </li></Link>
            
            <Link className="link" to="/calendario-admin"><li className="active">
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

      <main className={`main-content ${menuAberto ? "expanded" : ""}`}>

        <section className="page-header">

          <h1>Calendário de Consultas</h1>

          <p>
            Visualização semanal dos agendamentos da clínica.
          </p>

        </section>

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

export default CalendarioAdmin;