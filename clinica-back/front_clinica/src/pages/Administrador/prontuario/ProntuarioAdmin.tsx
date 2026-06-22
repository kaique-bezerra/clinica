import "../../pages_medico/Prontuario/Prontuario.css";
import MenuLateral from "../Componentes/MenuLateral";

import { useState } from "react";

function ProntuarioAdmin() {

  const [menuAberto, setMenuAberto] = useState(false);

  const [consultaSelecionada, setConsultaSelecionada] = useState("");

  const [queixa, setQueixa] = useState("");
  const [diagnostico, setDiagnostico] = useState("");
  const [prescricao, setPrescricao] = useState("");
  const [observacoes, setObservacoes] = useState("");

  const [mensagem, setMensagem] = useState("");

  function salvarProntuario() {

    if (!consultaSelecionada || !queixa || !diagnostico) {
      setMensagem("Preencha os campos obrigatórios!");
      return;
    }

    setMensagem(" Prontuário salvo com sucesso!");

    setQueixa("");
    setDiagnostico("");
    setPrescricao("");
    setObservacoes("");
    setConsultaSelecionada("");
  }

  return (
    <div className="prontuario-container">

      <MenuLateral menuAberto={menuAberto}
      setMenuAberto={setMenuAberto}/>

      <main className={`main-content ${menuAberto ? "expanded" : ""}`}>

        <section className="page-header">

          <h1>Prontuário Eletrônico</h1>

          <p>
            Registre informações clínicas da consulta e acompanhe o histórico do paciente.
          </p>

        </section>

        <section className="form-section">

          <label>Selecione a Consulta</label>

          <select
            value={consultaSelecionada}
            onChange={(e) => setConsultaSelecionada(e.target.value)}
          >
            <option value="">Escolha uma consulta</option>
            <option>Maria Silva - 20/05 14:00</option>
            <option>Carlos Henrique - 20/05 15:00</option>
            <option>Fernanda Lima - 20/05 16:30</option>
          </select>

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
            <label>Prescrição</label>
            <input
              value={prescricao}
              onChange={(e) => setPrescricao(e.target.value)}
              placeholder="Ex: dipirona 500mg..."
            />
          </div>

          <div className="input-group">
            <label>Observações</label>
            <textarea
              value={observacoes}
              onChange={(e) => setObservacoes(e.target.value)}
              placeholder="Anotações adicionais..."
            />
          </div>

          <button onClick={salvarProntuario}>
            Salvar Prontuário
          </button>

          {mensagem && (
            <p style={{ marginTop: "10px" }}>
              {mensagem}
            </p>
          )}

        </section>

        <section className="history-section">

          <h2>Histórico do Paciente</h2>

          <div className="history-card">

            <h3>Maria Silva - 10/05</h3>

            <p><strong>Queixa:</strong> Dor de cabeça</p>
            <p><strong>Diagnóstico:</strong> Enxaqueca</p>
            <p><strong>Prescrição:</strong> Dipirona 500mg</p>
            <p><strong>Observações:</strong> Evitar luz forte</p>

          </div>

          <div className="history-card">

            <h3>Maria Silva - 02/04</h3>

            <p><strong>Queixa:</strong> Febre</p>
            <p><strong>Diagnóstico:</strong> Virose</p>
            <p><strong>Prescrição:</strong> Paracetamol</p>
            <p><strong>Observações:</strong> Repouso</p>

          </div>

        </section>

      </main>

    </div>
  );
}

export default ProntuarioAdmin;