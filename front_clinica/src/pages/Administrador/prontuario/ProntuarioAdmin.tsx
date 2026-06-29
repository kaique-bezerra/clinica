import { useState, useEffect } from "react";
import "../../pages_medico/Prontuario/Prontuario.css";
import MenuLateral from "../Componentes/MenuLateral";

function ProntuarioAdmin() {

  const [mensagem, setMensagem]=useState("");

  const [menuAberto, setMenuAberto] = useState(false);

  const [formData, setFormData] = useState({
    idConsulta: "",
    queixas: "",
    diagnostico: "",
    prescricao: "",
    observacoes: ""
  });

  const [consultas, setConsultas] = useState<{
    idConsulta: number;
    nomePaciente: string;
    nomeMedico: string;
    dataConsulta: string;
    horaConsulta: string;
  }[]>([]);

  function handleChange(event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>){
    const {name, value}= event.target;

    setFormData((prev)=> ({
      ...prev, [name]:value
    }))
  }

  async function handleSubmit(event: React.FormEvent<HTMLFormElement>){
    event.preventDefault();

    if(!formData.idConsulta || !formData.queixas|| !formData.diagnostico 
      || !formData.prescricao){
        setMensagem("Preencha os campos obrigatórios!");
        return;
      }

      const token= localStorage.getItem("token");

      const payloadValido = {
        ...formData, 
        consulta: {
          idConsulta: Number(formData.idConsulta)
        }
      }; console.log(payloadValido);
      try{
        const response= await fetch("http://localhost:8080/prontuarios", {
          method: "POST",
          headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`
          
        }, body: JSON.stringify(payloadValido),
        });

        if (!response.ok) {
          if (response.status === 403) {
            throw new Error("Sem permissão ou token expirado.");
          }
        
          if (response.status === 409) {
            // Se a API envia o texto direto (ex: "CPF já cadastrado!")
            const msgServidor = await response.text(); 
            throw new Error(msgServidor || "Registro Duplicado");
          }

        // Captura o corpo do erro genérico enviado pelo Spring Boot
        const erroGenerico = await response.text();
        throw new Error(`Erro ao cadastrar prontuário: ${erroGenerico}`);
      }
      setMensagem("Prontuário cadastrado com sucesso!");
        setFormData({
        idConsulta: "",
        queixas: "",
        diagnostico: "",
        prescricao: "",
        observacoes: ""
      });
    } catch (error: any) {
      console.error(error);
      setMensagem(error.message || "Erro ao conectar ao servidor.");
    }
  }

  useEffect(() => {
  async function carregarConsultas() {
    const token = localStorage.getItem("token");
    try {
      const response = await fetch("http://localhost:8080/consultas/sem-prontuario", {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });

      if (!response.ok) {
        throw new Error("Erro ao carregar consultas.");
      }

      const dados = await response.json();
      setConsultas(dados);
    } catch (error) {
      console.error(error);
    }}
      carregarConsultas();}, []);

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
        <form className="prontuario-form" onSubmit={handleSubmit} >
        <section className="form-section">

          <label>Selecione a Consulta</label>

          <select
            value={formData.idConsulta}
            onChange={(e) => setFormData((prev) => ({
                ...prev,
                  idConsulta: e.target.value
              }))
            }
          >

  <option value="">Escolha uma consulta</option>

  {consultas.map((consulta) => (
    <option
      key={consulta.idConsulta}
      value={consulta.idConsulta}
    >
      {consulta.nomePaciente} - {consulta.nomeMedico} - {consulta.dataConsulta} {consulta.horaConsulta}
    </option>
    ))}

  </select>

        </section>

        <section className="form-section">

          <div className="input-group">
            
            <label>Queixa Principal *</label>
            <input name="queixas" value={formData.queixas} onChange={handleChange}
              placeholder="Ex: dor de cabeça, febre..." />
          </div>

          <div className="input-group">
            <label>Diagnóstico *</label>
            <input name="diagnostico" value={formData.diagnostico} onChange={handleChange}
              placeholder="Ex: virose, hipertensão..."/>
          </div>

          <div className="input-group">
            <label>Prescrição *</label>
            <input name="prescricao" value={formData.prescricao} onChange={handleChange}
              placeholder="Ex: dipirona 500mg..."/>
          </div>

          <div className="input-group">
            <label>Observações</label>
            <textarea name="observacoes" value={formData.observacoes} onChange={handleChange}
              placeholder="Anotações adicionais..."
            />
          </div>

          <button type="submit" className="save-button">
            Salvar Prontuário
          </button>

          {mensagem && (
            <p style={{ marginTop: "10px" , color: "blue"}}>
              {mensagem}
            </p>
          )}
        </section>
        </form>
      </main>

    </div>
  );
}

export default ProntuarioAdmin;