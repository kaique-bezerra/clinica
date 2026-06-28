import { useEffect, useState } from "react";
import MenuLateral from "../componentes/MenuLateral";
import "./CalendarioPaciente.css";
import ChatFlutuante from "../componentes/ChatFlutuante";

interface Consulta {
  idConsulta: number;
  nomePaciente: string;
  nomeMedico: string;
  dataConsulta: string;
  horaConsulta: string;
  statusConsulta: "AGENDADO" | "REALIZADO" | "CANCELADO";
}

function CalendarioPaciente() {
  const [menuAberto, setMenuAberto] = useState(false);
  const [consultas, setConsultas] = useState<Consulta[]>([]);
  const [carregando, setCarregando] = useState(true);
  const [erro, setErro] = useState("");

  const idPaciente = Number(localStorage.getItem("idUsuario"));

  useEffect(() => {
    async function carregarConsultas() {
      try {
        const token = localStorage.getItem("token");

        const response = await fetch(
          `http://localhost:8080/consultas/paciente/${idPaciente}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        if (!response.ok) {
          throw new Error("Erro ao carregar consultas.");
        }

        const dados: Consulta[] = await response.json();

        const ordenadas = dados.sort((a, b) => {
          const dataA = new Date(
            `${a.dataConsulta}T${a.horaConsulta}`
          );
          const dataB = new Date(
            `${b.dataConsulta}T${b.horaConsulta}`
          );

          return dataA.getTime() - dataB.getTime();
        });

        setConsultas(ordenadas);
      } catch (error: any) {
        setErro(error.message);
      } finally {
        setCarregando(false);
      }
    }

    if (idPaciente) {
      carregarConsultas();
    }
  }, [idPaciente]);

  const consultasPassadas = consultas.filter(
    (consulta) =>
      consulta.statusConsulta === "REALIZADO" ||
      consulta.statusConsulta === "CANCELADO"
  );

  const consultasFuturas = consultas.filter(
    (consulta) => consulta.statusConsulta === "AGENDADO"
  );

  const proximaConsulta = consultasFuturas[0];

  function formatarData(data: string) {
    return new Date(data).toLocaleDateString("pt-BR");
  }

  return (
    <div className="calendario-container">
      <MenuLateral
        menuAberto={menuAberto}
        setMenuAberto={setMenuAberto}
      />

      <ChatFlutuante />

      <main
        className={`main-content ${
          menuAberto ? "expanded" : ""
        }`}
      >
        <section className="page-header">
          <h1>Calendário de Consultas</h1>

          <p>
            Visualize suas consultas passadas,
            atuais e futuras.
          </p>

          {proximaConsulta && (
            <div className="proxima-consulta">
              <strong>Próxima consulta:</strong>{" "}
              {formatarData(proximaConsulta.dataConsulta)}
              {" às "}
              {proximaConsulta.horaConsulta.slice(0, 5)}
              {" com "}
              {proximaConsulta.nomeMedico}
            </div>
          )}
        </section>

        {carregando ? (
          <p>Carregando consultas...</p>
        ) : erro ? (
          <p>{erro}</p>
        ) : (
          <>
            <section className="section-consultas">
              <h2 className="titulo-section">
                CONSULTAS PASSADAS
              </h2>

              <div className="calendar-grid">
                {consultasPassadas.length === 0 ? (
                  <p>Nenhuma consulta passada.</p>
                ) : (
                  consultasPassadas.map((consulta) => (
                    <div
                      key={consulta.idConsulta}
                      className="appointment-card"
                    >
                      <h3>
                        {formatarData(consulta.dataConsulta)}
                      </h3>

                      <p>
                        <strong>Horário:</strong>{" "}
                        {consulta.horaConsulta.slice(0, 5)}
                      </p>

                      <p>
                        <strong>Médico:</strong>{" "}
                        {consulta.nomeMedico}
                      </p>

                      <span
                        className={`status ${consulta.statusConsulta.toLowerCase()}`}
                      >
                        {consulta.statusConsulta}
                      </span>
                    </div>
                  ))
                )}
              </div>
            </section>

            <section className="section-consultas">
              <h2 className="titulo-section">
                CONSULTAS ATUAIS E FUTURAS
              </h2>

              <div className="calendar-grid">
                {consultasFuturas.length === 0 ? (
                  <p>Nenhuma consulta futura.</p>
                ) : (
                  consultasFuturas.map((consulta) => (
                    <div
                      key={consulta.idConsulta}
                      className="appointment-card"
                    >
                      <h3>
                        {formatarData(consulta.dataConsulta)}
                      </h3>

                      <p>
                        <strong>Horário:</strong>{" "}
                        {consulta.horaConsulta.slice(0, 5)}
                      </p>

                      <p>
                        <strong>Médico:</strong>{" "}
                        {consulta.nomeMedico}
                      </p>

                      <span
                        className={`status ${consulta.statusConsulta.toLowerCase()}`}
                      >
                        {consulta.statusConsulta}
                      </span>
                    </div>
                  ))
                )}
              </div>
            </section>
          </>
        )}
      </main>
    </div>
  );
}

export default CalendarioPaciente;