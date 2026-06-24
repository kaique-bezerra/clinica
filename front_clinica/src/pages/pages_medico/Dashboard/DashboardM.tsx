import { useState, useEffect } from "react";
import imagem from "../../../assets/logo2.jpeg";
import MenuLateral from "../componentes/MenuLateral";
import "./Dashboard.css";

// 1. Definição das Interfaces atualizadas de acordo com o padrão do seu Backend
interface Paciente {
  idUsuario: number; 
  nome: string;
  sobrenome?: string;
  cpf?: string;
}

interface Medico {
  idUsuario: number; 
  nome: string;
  sobrenome?: string;
  crm?: string;
}

interface Consulta {
  id: number;
  paciente: Paciente;
  medico: Medico;
  dataConsulta: string; // "YYYY-MM-DD"
  horaConsulta: string; // "HH:mm:ss"
  statusConsulta: "AGENDADO" | "REALIZADO" | "CANCELADO";
}

function DashboardM() {
  const [menuAberto, setMenuAberto] = useState(false);
  const [consultas, setConsultas] = useState<Consulta[]>([]);
  const [carregando, setCarregando] = useState(true);
  const [erroMensagem, setErroMensagem] = useState<string | null>(null);

  // 2. RECUPERAÇÃO DINÂMICA DOS DADOS DO MÉDICO LOGADO
  const idMedicoLogado = Number(localStorage.getItem("idUsuario")) || 0; 
  const nomeMedicoLogado = localStorage.getItem("nomeUsuario") || "Médico";

  // 3. Efeito para buscar os dados na API passando o ID dinâmico e o Token
  useEffect(() => {
    // Se o ID for 0, significa que os dados não estão no localStorage (usuário deslogado)
    if (idMedicoLogado === 0) {
      setErroMensagem("Usuário não identificado. Por favor, faça login novamente.");
      setCarregando(false);
      return;
    }

    async function carregarConsultas() {
      try {
        setCarregando(true);
        setErroMensagem(null);

        const token = localStorage.getItem("token");

        if (!token) {
          throw new Error("Token de autenticação não encontrado. Faça login novamente.");
        }

        // Requisição usando o ID dinâmico recuperado do login
        const response = await fetch(`http://localhost:8080/consultas/medico/${idMedicoLogado}`, {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
          }
        });

        if (!response.ok) {
          if (response.status === 403) {
            throw new Error("Acesso negado. Seu usuário não tem permissão para ver estes dados.");
          }
          throw new Error(`Erro no servidor: Status ${response.status}`);
        }

        const dados: Consulta[] = await response.json();
        setConsultas(dados);
      } catch (error: any) {
        console.error("Falha na requisição:", error);
        setErroMensagem(error.message || "Erro desconhecido ao carregar consultas.");
      } finally {
        setCarregando(false);
      }
    }

    carregarConsultas();
  }, [idMedicoLogado]);

  // 4. Métricas dinâmicas baseadas nos dados retornados
  const hojeFormatado = new Date().toISOString().split("T")[0]; 
  
  const consultasHoje = consultas.filter(
    (c) => c.dataConsulta === hojeFormatado && c.statusConsulta === "AGENDADO"
  );
  
  const consultasCanceladas = consultas.filter(
    (c) => c.statusConsulta === "CANCELADO"
  );

  const formatarHora = (hora: string) => (hora ? hora.slice(0, 5) : "");

  return (
    <div className="dashboard-container">
      <MenuLateral menuAberto={menuAberto} setMenuAberto={setMenuAberto} />

      <main className={`main-content ${menuAberto ? "expanded" : ""}`}>
        <section className="welcome-card">
          <div className="welcome-text">
            <div className="Imagemlogo">
              <img src={imagem} alt="Logo da Clínica" />
            </div>
            <h1>Bem-vindo, {nomeMedicoLogado}!</h1>

            {carregando ? (
              <p>Carregando sua agenda...</p>
            ) : erroMensagem ? (
              <p style={{ color: "#ff4d4d" }}>{erroMensagem}</p>
            ) : (
              <p>
                Você possui {consultasHoje.length} consultas agendadas para hoje.
                Gerencie os pacientes, acompanhe os horários e mantenha a agenda da clínica organizada.
              </p>
            )}
          </div>
        </section>

        <section className="cards-container">
          <div className="card">
            <h2>{carregando ? "..." : erroMensagem ? "0" : consultasHoje.length}</h2>
            <p>Consultas Hoje</p>
          </div>

          <div className="card">
            <h2>{carregando ? "..." : erroMensagem ? "0" : consultas.length}</h2>
            <p>Total de Consultas</p>
          </div>

          <div className="card">
            <h2>{carregando ? "..." : erroMensagem ? "0" : consultasCanceladas.length}</h2>
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
                  <td colSpan={5} style={{ textAlign: "center" }}>Carregando agendamentos...</td>
                </tr>
              ) : erroMensagem ? (
                <tr>
                  <td colSpan={5} style={{ textAlign: "center", color: "#ff4d4d" }}>{erroMensagem}</td>
                </tr>
              ) : consultas.length === 0 ? (
                <tr>
                  <td colSpan={5} style={{ textAlign: "center" }}>Nenhuma consulta encontrada.</td>
                </tr>
              ) : (
                consultas.map((consulta) => (
                  <tr key={consulta.id}>
                    <td>{consulta.paciente?.nome || "Não informado"}</td>
                    <td>{formatarHora(consulta.horaConsulta)}</td>
                    <td>{consulta.dataConsulta ? consulta.dataConsulta.split("-").reverse().join("/") : ""}</td>
                    <td>{consulta.medico?.nome || "Não informado"}</td>
                    <td>
                      <span className={`status-badge ${consulta.statusConsulta?.toLowerCase() || ""}`}>
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