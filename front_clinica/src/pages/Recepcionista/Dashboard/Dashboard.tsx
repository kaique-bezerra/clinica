import { useEffect, useState } from "react";
import imagem from "../../../assets/logo2.jpeg";
import "./Dashboard.css";
import MenuLateral from "../componentes/MenuLateral";

interface Consulta {
  idConsulta: number;
  nomePaciente: string;
  nomeMedico: string;
  dataConsulta: string;
  horaConsulta: string;
  statusConsulta: "AGENDADO" | "CANCELADO" | "REALIZADO";
}

function DashboardRecepcionista() {
  const [menuAberto, setMenuAberto] = useState(false);

  const [consultasHoje, setConsultasHoje] = useState<Consulta[]>([]);
  const [consultasSemana, setConsultasSemana] = useState<Consulta[]>([]);
  const [pacientes, setPacientes] = useState<any[]>([]);

  const [carregando, setCarregando] = useState(true);
  const [erro, setErro] = useState("");

  const nomeRecepcionista =
    localStorage.getItem("nomeUsuario") || "Recepcionista";

  useEffect(() => {
    async function carregarDashboard() {
      try {
        setCarregando(true);

        const token = localStorage.getItem("token");

        const headers = {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        };

        const [resConsultasHoje, resConsultasSemana, resPacientes] =
          await Promise.all([
            fetch("http://localhost:8080/consultas/dia", { headers }),
            fetch("http://localhost:8080/consultas/semana", { headers }),
            fetch("http://localhost:8080/paciente", { headers }),
          ]);

        if (
          !resConsultasHoje.ok ||
          !resConsultasSemana.ok ||
          !resPacientes.ok
        ) {
          throw new Error("Erro ao carregar dados do dashboard.");
        }

        const dadosHoje = await resConsultasHoje.json();
        const dadosSemana = await resConsultasSemana.json();
        const dadosPacientes = await resPacientes.json();

        setConsultasHoje(dadosHoje);
        setConsultasSemana(dadosSemana);
        setPacientes(dadosPacientes);
      } catch (error: any) {
        console.error(error);
        setErro(error.message);
      } finally {
        setCarregando(false);
      }
    }

    carregarDashboard();
  }, []);

  const consultasCanceladasSemana = consultasSemana.filter(
    (consulta) => consulta.statusConsulta === "CANCELADO"
  );


  const proximosAgendamentos = [...consultasHoje]
    .filter((consulta) => consulta.statusConsulta === "AGENDADO")
    .sort((a, b) => {
      const horaA = new Date(`${a.dataConsulta}T${a.horaConsulta}`).getTime();
      const horaB = new Date(`${b.dataConsulta}T${b.horaConsulta}`).getTime();
      return horaA - horaB;
    });

  const formatarHora = (hora: string) => hora?.slice(0, 5);

  const formatarData = (data: string) =>
    data.split("-").reverse().join("/");

  return (
    <div className="dashboard-container">
      <MenuLateral
        menuAberto={menuAberto}
        setMenuAberto={setMenuAberto}
      />

      <main className={`main-content ${menuAberto ? "expanded" : ""}`}>
        <section className="welcome-card">
          <div className="welcome-text">
            <div className="Imagemlogo">
              <img src={imagem} alt="Logo da Clínica" />
            </div>

            <h1>Bem-vinda, {nomeRecepcionista}!</h1>

            {carregando ? (
              <p>Carregando dashboard...</p>
            ) : erro ? (
              <p style={{ color: "red" }}>{erro}</p>
            ) : (
              <p>
                A MedSync possui {consultasHoje.length} consultas agendadas para hoje.
              </p>
            )}
          </div>
        </section>

        <section className="cards-container">
          <div className="card">
            <h2>{carregando ? "..." : consultasHoje.length}</h2>
            <p>Consultas Hoje</p>
          </div>

          <div className="card">
            <h2>{carregando ? "..." : pacientes.length}</h2>
            <p>Pacientes Cadastrados</p>
          </div>

          <div className="card">
            <h2>
              {carregando ? "..." : consultasCanceladasSemana.length}
            </h2>
            <p>Consultas Canceladas (Semana)</p>
          </div>
        </section>

        <section className="appointments-section">
          <h2>Próximos Agendamentos</h2>

          <table>
            <thead>
              <tr>
                <th>Paciente</th>
                <th>Horário</th>
                <th>Data</th>
                <th>Médico</th>
                <th>Status</th>
              </tr>
            </thead>

            <tbody>
              {carregando ? (
                <tr>
                  <td colSpan={5} style={{ textAlign: "center" }}>
                    Carregando agendamentos...
                  </td>
                </tr>
              ) : erro ? (
                <tr>
                  <td colSpan={5} style={{ textAlign: "center", color: "red" }}>
                    {erro}
                  </td>
                </tr>
              ) : proximosAgendamentos.length === 0 ? (
                <tr>
                  <td colSpan={5} style={{ textAlign: "center" }}>
                    Nenhuma consulta encontrada para hoje.
                  </td>
                </tr>
              ) : (
                proximosAgendamentos.map((consulta) => (
                  <tr key={consulta.idConsulta}>
                    <td>{consulta.nomePaciente}</td>
                    <td>{formatarHora(consulta.horaConsulta)}</td>
                    <td>{formatarData(consulta.dataConsulta)}</td>
                    <td>{consulta.nomeMedico}</td>
                    <td>
                      <span
                        className={`status-badge ${consulta.statusConsulta.toLowerCase()}`}
                      >
                        {consulta.statusConsulta}
                      </span>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </section>
      </main>
    </div>
  );
}

export default DashboardRecepcionista;