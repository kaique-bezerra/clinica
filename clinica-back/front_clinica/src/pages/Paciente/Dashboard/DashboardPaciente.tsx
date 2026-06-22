import { useState } from "react";
import imagem from "../../../assets/logo2.jpeg";
import MenuLateral from "../componentes/MenuLateral";
import "./DashboardPaciente.css";
import ChatFlutuante from "../componentes/ChatFlutuante";

function DashboardPaciente() {
  const [menuAberto, setMenuAberto] = useState(false);

  return (
    <div className="dashboard-container">

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

        <section className="welcome-card">

          <div className="welcome-text">

            <div className="Imagemlogo">
              <img src={imagem} alt="Logo da Clínica" />
            </div>

            <h1>Olá, Maria 👋</h1>

            <p>
              Seja bem-vinda ao seu painel do paciente.
              Aqui você pode acompanhar suas consultas,
              medicações e histórico recente.
            </p>

          </div>

        </section>

        <section className="cards-container">

          <div className="card">
            <h2>12/06</h2>
            <p>Próxima Consulta</p>
          </div>

          <div className="card">
            <h2>18</h2>
            <p>Consultas Realizadas</p>
          </div>

          <div className="card">
            <h2>3</h2>
            <p>Lembretes de Medicação</p>
          </div>

        </section>

        <section className="medicacao-section">

          <h2>Lembretes de Medicação</h2>

          <div className="medicacao-lista">

            <div className="medicacao-card">
              <h3>Paracetamol</h3>
              <p>08:00 - Após café da manhã</p>
            </div>

            <div className="medicacao-card">
              <h3>Vitamina D</h3>
              <p>13:00 - Após almoço</p>
            </div>

            <div className="medicacao-card">
              <h3>Antialérgico</h3>
              <p>21:00 - Antes de dormir</p>
            </div>

          </div>

        </section>

        <section className="history-section">

          <h2>Histórico Recente</h2>

          <table>

            <thead>
              <tr>
                <th>Data</th>
                <th>Médico</th>
                <th>Especialidade</th>
                <th>Status</th>
              </tr>
            </thead>

            <tbody>

              <tr>
                <td>02/06/2026</td>
                <td>Dr. João</td>
                <td>Cardiologia</td>
                <td>Concluída</td>
              </tr>

              <tr>
                <td>15/05/2026</td>
                <td>Dra. Ana</td>
                <td>Dermatologia</td>
                <td>Concluída</td>
              </tr>

              <tr>
                <td>01/05/2026</td>
                <td>Dr. Pedro</td>
                <td>Clínico Geral</td>
                <td>Cancelada</td>
              </tr>

            </tbody>

          </table>

        </section>

      </main>

    </div>
  );
}

export default DashboardPaciente;