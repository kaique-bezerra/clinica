import { useState, useEffect } from "react";
import MenuLateral from "../componentes/MenuLateral";
import "./Prontuario.css";

interface Consulta {
  idConsulta: number;
  nomePaciente: string;
  nomeMedico: string;
  dataConsulta: string;
  horaConsulta: string;
  statusConsulta: "AGENDADO" | "REALIZADO" | "CANCELADO";
}

function Prontuario() {
  const [menuAberto, setMenuAberto] = useState(false);

  const [consultas, setConsultas] = useState<Consulta[]>([]);
  const [consultaSelecionada, setConsultaSelecionada] = useState("");

  const [queixa, setQueixa] = useState("");
  const [diagnostico, setDiagnostico] = useState("");
  const [prescricao, setPrescricao] = useState("");
  const [observacoes, setObservacoes] = useState("");

  const [mensagem, setMensagem] = useState("");
  const [erro, setErro] = useState("");
  const [carregando, setCarregando] = useState(false);

  const idMedicoLogado = Number(localStorage.getItem("idUsuario")) || 0;

  useEffect(() => {
    carregarConsultas();
  }, [idMedicoLogado]);

  async function carregarConsultas() {
    try {
      const token = localStorage.getItem("token");

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
        throw new Error("Erro ao carregar consultas.");
      }

      const dados: Consulta[] = await response.json();

      const consultasAgendadas = dados.filter(
        (consulta) => consulta.statusConsulta === "AGENDADO"
      );

      setConsultas(consultasAgendadas);
    } catch (error) {
      console.error(error);
      setErro("Erro ao carregar consultas.");
    }
  }

  async function salvarProntuario() {
    if (
      !consultaSelecionada ||
      !queixa ||
      !diagnostico ||
      !prescricao ||
      !observacoes
    ) {
      setMensagem("Preencha todos os campos!");
      return;
    }

    try {
      setCarregando(true);

      const token = localStorage.getItem("token");

      // SALVA O PRONTUÁRIO
      const response = await fetch("http://localhost:8080/prontuarios", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
          idConsulta: Number(consultaSelecionada),
          queixas: queixa,
          diagnostico: diagnostico,
          observacoes: observacoes,
          prescricao: prescricao,
        }),
      });

      if (!response.ok) {
        const erro = await response.text();
        throw new Error(erro);
      }

      // ALTERA STATUS PARA REALIZADO
      const atualizarStatus = await fetch(
        `http://localhost:8080/consultas/${consultaSelecionada}/status`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify({
            statusConsulta: "REALIZADO",
          }),
        }
      );

      if (!atualizarStatus.ok) {
        throw new Error("Erro ao atualizar status da consulta.");
      }

      setMensagem("Prontuário salvo com sucesso!");

      // REMOVE DA LISTA DE PRÓXIMOS AGENDAMENTOS
      setConsultas((prev) =>
        prev.filter(
          (consulta) => consulta.idConsulta !== Number(consultaSelecionada)
        )
      );

      // LIMPA FORMULÁRIO
      setQueixa("");
      setDiagnostico("");
      setPrescricao("");
      setObservacoes("");
      setConsultaSelecionada("");

    } catch (error: any) {
      setMensagem(error.message || "Erro ao salvar prontuário.");
    } finally {
      setCarregando(false);
    }
  }

  return (
    <div className="prontuario-container">
      <MenuLateral
        menuAberto={menuAberto}
        setMenuAberto={setMenuAberto}
      />

      <main className={`main-content ${menuAberto ? "expanded" : ""}`}>
        <section className="page-header">
          <h1>Prontuário Eletrônico</h1>
          <p>
            Registre informações clínicas da consulta e acompanhe o histórico do
            paciente.
          </p>
        </section>

        <section className="form-section">
          <label>Selecione a Consulta</label>

          <select
            value={consultaSelecionada}
            onChange={(e) => setConsultaSelecionada(e.target.value)}
          >
            <option value="">Escolha uma consulta</option>

            {consultas.map((consulta) => (
              <option key={consulta.idConsulta} value={consulta.idConsulta}>
                {consulta.nomePaciente} -{" "}
                {consulta.dataConsulta.split("-").reverse().join("/")} -{" "}
                {consulta.horaConsulta.slice(0, 5)}
              </option>
            ))}
          </select>

          {erro && <p>{erro}</p>}
        </section>

        <section className="form-section">
          <div className="input-group">
            <label>Queixa Principal *</label>
            <input
              value={queixa}
              onChange={(e) => setQueixa(e.target.value)}
              placeholder="Ex: dor de cabeça, febre..."
            />
          </div>

          <div className="input-group">
            <label>Diagnóstico *</label>
            <input
              value={diagnostico}
              onChange={(e) => setDiagnostico(e.target.value)}
              placeholder="Ex: virose, hipertensão..."
            />
          </div>

          <div className="input-group">
            <label>Prescrição *</label>
            <input
              value={prescricao}
              onChange={(e) => setPrescricao(e.target.value)}
              placeholder="Ex: dipirona 500mg..."
            />
          </div>

          <div className="input-group">
            <label>Observações *</label>
            <textarea
              value={observacoes}
              onChange={(e) => setObservacoes(e.target.value)}
              placeholder="Anotações adicionais..."
            />
          </div>

          <button onClick={salvarProntuario} disabled={carregando}>
            {carregando ? "Salvando..." : "Salvar Prontuário"}
          </button>

          {mensagem && (
            <p style={{ marginTop: "10px" }}>
              {mensagem}
            </p>
          )}
        </section>
      </main>
    </div>
  );
}

export default Prontuario;