import { useState, useEffect } from "react";
import imagem from "../../../assets/logo2.jpeg";
import MenuLateral from "../componentes/MenuLateral";
import "./Dashboard.css";

interface Consulta {
  idConsulta: number;
  nomePaciente: string;
  nomeMedico: string;
  dataConsulta: string;
  horaConsulta: string;
  statusConsulta: "AGENDADO" | "REALIZADO" | "CANCELADO";
}

function DashboardM() {
  const [menuAberto, setMenuAberto] = useState(false);
  const [consultas, setConsultas] = useState<Consulta[]>([]);
  const [carregando, setCarregando] = useState(true);
  const [erroMensagem, setErroMensagem] = useState<string | null>(null);

  const idMedicoLogado = Number(localStorage.getItem("idUsuario")) || 0;
  const nomeMedicoLogado = localStorage.getItem("nomeUsuario") || "Médico";

  useEffect(() => {
    if (idMedicoLogado === 0) {
      setErroMensagem("Usuário não identificado. Faça login novamente.");
      setCarregando(false);
      return;
    }

    async function carregarConsultas() {
      try {
        setCarregando(true);
        setErroMensagem(null);

        const token = localStorage.getItem("token");

        if (!token) {
          throw new Error("Token não encontrado.");
        }

        const response = await fetch(
          `http://localhost:8080/consultas/medico/${idMedicoLogado}`,
          {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${token}`,
            },
          }
        );

        if (!response.ok) {
          if (response.status === 403) {
            throw new Error("Acesso negado.");
          }
          throw new Error(`Erro no servidor: ${response.status}`);
        }

        const dados: Consulta[] = await response.json();
        setConsultas(dados);

      } catch (error: any) {
        console.error(error);
        setErroMensagem(error.message || "Erro ao carregar consultas.");
      } finally {
        setCarregando(false);
      }
    }

    carregarConsultas();
  }, [idMedicoLogado]);

  const hojeFormatado = new Date().toISOString().split("T")[0];

  const consultasHoje = consultas.filter(
    (c) =>
      c.dataConsulta === hojeFormatado &&
      c.statusConsulta === "AGENDADO"
  );

  const consultasCanceladas = consultas.filter(
    (c) => c.statusConsulta === "CANCELADO"
  );


  const proximosAgendamentos = consultas.filter(
    (c) => c.statusConsulta === "AGENDADO"
  );

  const formatarHora = (hora: string) =>
    hora ? hora.slice(0, 5) : "";

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

            <h1>Bem-vindo, Dr {nomeMedicoLogado}!</h1>

            {carregando ? (
              <p>Carregando sua agenda...</p>
            ) : erroMensagem ? (
              <p style={{ color: "#ff4d4d" }}>{erroMensagem}</p>
            ) : (
              <p>
                Você possui {consultasHoje.length} consultas agendadas para hoje.
              </p>
            )}
          </div>
        </section>

        <section className="cards-container">
          <div className="card">
            <h2>
              {carregando ? "..." : erroMensagem ? "0" : consultasHoje.length}
            </h2>
            <p>Consultas Hoje</p>
          </div>

          <div className="card">
            <h2>
              {carregando ? "..." : erroMensagem ? "0" : consultas.length}
            </h2>
            <p>Total de Consultas</p>
          </div>

          <div className="card">
            <h2>
              {carregando ? "..." : erroMensagem ? "0" : consultasCanceladas.length}
            </h2>
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
              ) : erroMensagem ? (
                <tr>
                  <td colSpan={5} style={{ textAlign: "center", color: "#ff4d4d" }}>
                    {erroMensagem}
                  </td>
                </tr>
              ) : proximosAgendamentos.length === 0 ? (
                <tr>
                  <td colSpan={5} style={{ textAlign: "center" }}>
                    Nenhum próximo agendamento.
                  </td>
                </tr>
              ) : (
                proximosAgendamentos.map((consulta) => (
                  <tr key={consulta.idConsulta}>
                    <td>{consulta.nomePaciente}</td>
                    <td>{formatarHora(consulta.horaConsulta)}</td>
                    <td>
                      {consulta.dataConsulta
                        .split("-")
                        .reverse()
                        .join("/")}
                    </td>
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

export default DashboardM;