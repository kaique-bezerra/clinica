import { useState } from "react";
import MenuLateral from "../componentes/MenuLateral";
import "./CalendarioPaciente.css";
import ChatFlutuante from "../componentes/ChatFlutuante";

function CalendarioPaciente() {

  const [menuAberto, setMenuAberto] = useState(false);

  const consultasPassadas = [
    {
      data: "02/06/2026",
      horario: "09:00",
      medico: "Dr. João",
      especialidade: "Cardiologia",
      status: "Concluída",
    },

    {
      data: "28/05/2026",
      horario: "14:00",
      medico: "Dra. Ana",
      especialidade: "Dermatologia",
      status: "Concluída",
    },

    {
      data: "20/05/2026",
      horario: "11:00",
      medico: "Dr. Pedro",
      especialidade: "Clínico Geral",
      status: "Cancelada",
    },
  ];

  const consultasFuturas = [
    {
      data: "12/06/2026",
      horario: "15:00",
      medico: "Dra. Camila",
      especialidade: "Ortopedia",
      status: "Agendada",
    },

    {
      data: "13/06/2026",
      horario: "08:30",
      medico: "Dr. Lucas",
      especialidade: "Neurologia",
      status: "Hoje",
    },

    {
      data: "15/06/2026",
      horario: "10:00",
      medico: "Dra. Fernanda",
      especialidade: "Nutricionista",
      status: "Agendada",
    },
  ];

  return (
    <div className="calendario-container">

      <MenuLateral
        menuAberto={menuAberto}
        setMenuAberto={setMenuAberto}
      />

      <ChatFlutuante/>

      <main
        className={`main-content ${
          menuAberto ? "expanded" : ""
        }`}
      >

        <section className="page-header">

          <h1>Calendário de Consultas</h1>

          <p>
            Visualize suas consultas passadas,
            em andamento e futuras.
          </p>

        </section>

        <section className="section-consultas">

          <h2 className="titulo-section">
            CONSULTAS PASSADAS
          </h2>

          <div className="calendar-grid">

            {consultasPassadas.map((consulta, index) => (
              <div key={index} className="appointment-card">

                <h3>{consulta.data}</h3>

                <p>
                  <strong>Horário:</strong>{" "}
                  {consulta.horario}
                </p>

                <p>
                  <strong>Médico:</strong>{" "}
                  {consulta.medico}
                </p>

                <p>
                  <strong>Especialidade:</strong>{" "}
                  {consulta.especialidade}
                </p>

                <span
                  className={`status ${
                    consulta.status.toLowerCase()
                  }`}
                >
                  {consulta.status}
                </span>

              </div>
            ))}

          </div>

        </section>

        <section className="section-consultas">

          <h2 className="titulo-section">
            CONSULTAS ATUAIS E FUTURAS
          </h2>

          <div className="calendar-grid">

            {consultasFuturas.map((consulta, index) => (
              <div key={index} className="appointment-card">

                <h3>{consulta.data}</h3>

                <p>
                  <strong>Horário:</strong>{" "}
                  {consulta.horario}
                </p>

                <p>
                  <strong>Médico:</strong>{" "}
                  {consulta.medico}
                </p>

                <p>
                  <strong>Especialidade:</strong>{" "}
                  {consulta.especialidade}
                </p>

                <span
                  className={`status ${
                    consulta.status.toLowerCase()
                  }`}
                >
                  {consulta.status}
                </span>

              </div>
            ))}

          </div>

        </section>

      </main>

    </div>
  );
}

export default CalendarioPaciente;