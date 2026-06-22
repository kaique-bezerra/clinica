import { useState } from "react";
import imagem from "../../../assets/logo2.jpeg";
import "../../Recepcionista/Dashboard/Dashboard.css";
import MenuLateral from "../Componentes/MenuLateral";

function DashboardAdmin() {

  const [menuAberto, setMenuAberto] = useState(false);

  return (
   <div className="dashboard-container">

      <MenuLateral menuAberto={menuAberto}
      setMenuAberto={setMenuAberto}/>

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
            <h1>
              Bem-vindo, Administrador!
            </h1>

            <p>
              A clínica possui 12 consultas agendadas para hoje.
              Gerencie os pacientes, acompanhe os horários
              e mantenha a agenda da clínica organizada.
            </p>

          </div>

        </section>

        <section className="cards-container">

          <div className="card">
            <h2>12</h2>
            <p>Consultas Hoje</p>
          </div>

          <div className="card">
            <h2>245</h2>
            <p>Pacientes Cadastrados</p>
          </div>

          <div className="card">
            <h2>5</h2>
            <p>Consultas Canceladas</p>
          </div>

        </section>

        <section className="appointments-section">

          <h2>Próximos Agendamentos</h2>

          <table>

            <thead>
              <tr>
                <th>Paciente</th>
                <th>Horário</th>
                <th>Médico</th>
                <th>Status</th>
              </tr>
            </thead>

            <tbody>

              <tr>
                <td>Maria Silva</td>
                <td>14:00</td>
                <td>Dr. João</td>
                <td>Agendada</td>
              </tr>

              <tr>
                <td>Carlos Henrique</td>
                <td>15:00</td>
                <td>Dra. Ana</td>
                <td>Agendada</td>
              </tr>

              <tr>
                <td>Fernanda Lima</td>
                <td>16:30</td>
                <td>Dr. Pedro</td>
                <td>Cancelada</td>
              </tr>

            </tbody>

          </table>

        </section>

      </main>

    </div>
  );
}

export default DashboardAdmin;