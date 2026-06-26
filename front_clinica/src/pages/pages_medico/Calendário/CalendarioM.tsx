import "./Calendario.css";
import MenuLateral from "../componentes/MenuLateral";
import { useEffect, useState } from "react";

interface Consulta {
  idConsulta: number;
  nomePaciente: string;
  nomeMedico: string;
  dataConsulta: string;
  horaConsulta: string;
  statusConsulta: "AGENDADO" | "REALIZADO" | "CANCELADO";
}

function CalendarioM() {
  const [menuAberto, setMenuAberto] = useState(false);
  const [consultas, setConsultas] = useState<Consulta[]>([]);
  const [carregando, setCarregando] = useState(true);
  const [erro, setErro] = useState("");

  const idMedicoLogado = Number(localStorage.getItem("idUsuario")) || 0;

  const diasSemana = [
    "Domingo",
    "Segunda",
    "Terça",
    "Quarta",
    "Quinta",
    "Sexta",
    "Sábado",
  ];

  useEffect(() => {
    async function carregarConsultas() {
      try {
        const token = localStorage.getItem("token");

        const response = await fetch(
          `http://localhost:8080/consultas/medico/${idMedicoLogado}`,
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
          const dataA = new Date(`${a.dataConsulta}T${a.horaConsulta}`);
          const dataB = new Date(`${b.dataConsulta}T${b.horaConsulta}`);
          return dataA.getTime() - dataB.getTime();
        });

        setConsultas(ordenadas);
      } catch (error: any) {
        setErro(error.message);
      } finally {
        setCarregando(false);
      }
    }

    if (idMedicoLogado) {
      carregarConsultas();
    }
  }, [idMedicoLogado]);

  function consultasPorDia(indiceDia: number) {
    return consultas.filter((consulta) => {
      const data = new Date(`${consulta.dataConsulta}T00:00:00`);
      return data.getDay() === indiceDia;
    });
  }

  const hoje = new Date().getDay();

  return (
    <div className="calendario-container">
      <MenuLateral
        menuAberto={menuAberto}
        setMenuAberto={setMenuAberto}
      />

      <main className={`main-content ${menuAberto ? "expanded" : ""}`}>
        <section className="page-header">
          <h1>Calendário de Consultas</h1>

          <p>
            Visualização semanal dos seus agendamentos.
          </p>
        </section>

        {carregando ? (
          <p>Carregando agenda...</p>
        ) : erro ? (
          <p>{erro}</p>
        ) : (
          <section className="calendar-grid">
            {diasSemana.map((dia, index) => {
              const consultasDoDia = consultasPorDia(index);

              return (
                <div
                  key={index}
                  className={`day-card ${hoje === index ? "today" : ""}`}
                >
                  <h3>{dia}</h3>

                  {consultasDoDia.length === 0 ? (
                    <p className="sem-consulta">
                      Nenhuma consulta
                    </p>
                  ) : (
                    consultasDoDia.map((consulta) => (
                      <div
                        key={consulta.idConsulta}
                        className={`appointment ${consulta.statusConsulta.toLowerCase()}`}
                      >
                        <p>
                          <strong>
                            {consulta.horaConsulta.slice(0, 5)}
                          </strong>
                        </p>

                        <p>{consulta.nomePaciente}</p>

                        <span>
                          {consulta.statusConsulta}
                        </span>
                      </div>
                    ))
                  )}
                </div>
              );
            })}
          </section>
        )}
      </main>
    </div>
  );
}

export default CalendarioM;