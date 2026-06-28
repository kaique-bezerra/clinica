import { useEffect, useState } from "react";
import imagem from "../../../assets/logo2.jpeg";
import MenuLateral from "../componentes/MenuLateral";
import "./DashboardPaciente.css";
import ChatFlutuante from "../componentes/ChatFlutuante";

interface Consulta {
  idConsulta: number;
  nomePaciente: string;
  nomeMedico: string;
  especialidade: string;
  dataConsulta: string;
  horaConsulta: string;
  statusConsulta: "AGENDADO" | "REALIZADO" | "CANCELADO";
}

interface Prontuario {
  idProntuario: number;
  idConsulta: number;
  queixas: string;
  diagnostico: string;
  observacoes: string;
  prescricao: string;
}

function DashboardPaciente() {
  const [menuAberto, setMenuAberto] = useState(false);
  const [consultas, setConsultas] = useState<Consulta[]>([]);
  const [prontuarios, setProntuarios] = useState<Prontuario[]>([]);

  const idPaciente = Number(localStorage.getItem("idUsuario"));
  const token = localStorage.getItem("token");

  useEffect(() => {
    async function carregarDados() {
      try {
        const response = await fetch(
          `http://localhost:8080/consultas/paciente/${idPaciente}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        const consultasData: Consulta[] = await response.json();
        setConsultas(consultasData);

        const consultasRealizadas = consultasData.filter(
          (consulta) => consulta.statusConsulta === "REALIZADO"
        );

        const prontuariosPromises = consultasRealizadas.map(
          async (consulta) => {
            const response = await fetch(
              `http://localhost:8080/prontuarios/consulta/${consulta.idConsulta}`,
              {
                headers: {
                  Authorization: `Bearer ${token}`,
                },
              }
            );

            if (!response.ok) return null;

            return await response.json();
          }
        );

        const prontuariosData = await Promise.all(prontuariosPromises);

        setProntuarios(
          prontuariosData.filter(
            (prontuario): prontuario is Prontuario => prontuario !== null
          )
        );
      } catch (error) {
        console.log(error);
      }
    }

    carregarDados();
  }, [idPaciente, token]);

  const proximasConsultas = consultas
    .filter((consulta) => consulta.statusConsulta === "AGENDADO")
    .sort((a, b) => {
      const dataA = new Date(`${a.dataConsulta}T${a.horaConsulta}`);
      const dataB = new Date(`${b.dataConsulta}T${b.horaConsulta}`);
      return dataA.getTime() - dataB.getTime();
    });

  const proximaConsulta = proximasConsultas[0];

  const consultasRealizadas = consultas.filter(
    (consulta) => consulta.statusConsulta === "REALIZADO"
  );

  return (
    <div className="dashboard-container">
      <MenuLateral
        menuAberto={menuAberto}
        setMenuAberto={setMenuAberto}
      />

      <ChatFlutuante />

      <main
        className={`main-content ${menuAberto ? "expanded" : ""}`}
      >
        <section className="welcome-card">
          <div className="welcome-text">
            <div className="Imagemlogo">
              <img src={imagem} alt="Logo da Clínica" />
            </div>

            <h1>Olá 👋</h1>

            <p>
              Aqui você pode acompanhar suas consultas,
              medicações e prontuários.
            </p>
          </div>
        </section>

        <section className="cards-container">
          <div className="card">
            <h2>
              {proximaConsulta
                ? `${proximaConsulta.dataConsulta}`
                : "Sem consulta"}
            </h2>
            <p>Próxima Consulta</p>
          </div>

          <div className="card">
            <h2>{consultasRealizadas.length}</h2>
            <p>Consultas Realizadas</p>
          </div>

          <div className="card">
            <h2>{prontuarios.length}</h2>
            <p>Prontuários</p>
          </div>
        </section>

        <section className="medicacao-section">
          <h2>Lembretes de Medicação</h2>

          <div className="medicacao-lista">
            {prontuarios.length === 0 ? (
              <p>Nenhuma medicação registrada.</p>
            ) : (
              prontuarios.map((prontuario) => (
                <div
                  key={prontuario.idProntuario}
                  className="medicacao-card"
                >
                  <h3>Prescrição</h3>
                  <p>{prontuario.prescricao}</p>
                </div>
              ))
            )}
          </div>
        </section>

        <section className="history-section">
          <h2>Prontuários</h2>

          <table>
            <thead>
              <tr>
                <th>Diagnóstico</th>
                <th>Queixas</th>
                <th>Observações</th>
              </tr>
            </thead>

            <tbody>
              {prontuarios.map((prontuario) => (
                <tr key={prontuario.idProntuario}>
                  <td>{prontuario.diagnostico}</td>
                  <td>{prontuario.queixas}</td>
                  <td>{prontuario.observacoes}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </section>

        <section className="history-section">
          <h2>Próximas Consultas</h2>

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
              {proximasConsultas.map((consulta) => (
                <tr key={consulta.idConsulta}>
                  <td>{consulta.dataConsulta}</td>
                  <td>{consulta.nomeMedico}</td>
                  <td>{consulta.especialidade}</td>
                  <td>{consulta.statusConsulta}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </section>
      </main>
    </div>
  );
}

export default DashboardPaciente;