import { useState, useEffect } from "react";
import MenuLateral from "../Componentes/MenuLateral";
import "./horariobloqueado.css";

// Interface para tipar o Médico que vem do Backend
interface Medico {
  id: number;
  nome: string;
  sobrenome: string;
  especialidade: string;
}

function HorarioBloqueado() {
  const [menuAberto, setMenuAberto] = useState(false);
  const [medicos, setMedicos] = useState<Medico[]>([]); // Estado para guardar a lista de médicos
  const [carregandoMedicos, setCarregandoMedicos] = useState(true);

  const [formData, setFormData] = useState({
    idMedico: "",
    data: "", // Alterado de diaSemana para data
    horaInicio: "",
    horaFim: "",
    motivo: "",
  });

  // --- CARREGAR MÉDICOS DO BACKEND (GET) ---
  useEffect(() => {
    async function buscarMedicos() {
      const token = localStorage.getItem("token");
      try {
        const response = await fetch("http://localhost:8080/medico", {
          method: "GET",
          headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
          }
        });

        if (response.ok) {
          const dados = await response.json();
          setMedicos(dados);
        } else {
          console.error("Erro ao carregar médicos:", response.status);
        }
      } catch (error) {
        console.error("Erro de rede ao buscar médicos:", error);
      } finally {
        setCarregandoMedicos(false);
      }
    }

    buscarMedicos();
  }, []);

  // --- HANDLER DE ALTERAÇÃO DOS INPUTS ---
  function handleChange(event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) {
    const { name, value } = event.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value
    }));
  }

  // --- SUBMIT CONECTADO AO SPRING BOOT ---
  async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();

    const token = localStorage.getItem("token");

    const formatarHora = (hora: string) => {
      if (!hora) return "";
      return hora.length === 5 ? `${hora}:00` : hora;
    };

    // CORREÇÃO: Payload ajustado exatamente para o formato do JSON solicitado
    const payloadValido = {
      idMedico: Number(formData.idMedico),
      data: formData.data, // Formato "AAAA-MM-DD" nativo do <input type="date">
      horaInicio: formatarHora(formData.horaInicio),
      horaFim: formatarHora(formData.horaFim),
      motivo: formData.motivo
    };

    try {
      const response = await fetch("http://localhost:8080/bloqueios", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}` 
        },
        body: JSON.stringify(payloadValido), 
      });

      if (!response.ok) {
        if (response.status === 403) {
          alert("Erro 403: Seu usuário não tem permissão para cadastrar bloqueios.");
          return;
        }
        const textError = await response.text();
        alert(`Erro ao cadastrar o bloqueio: ${textError}`);
        return;
      }

      alert("Horário bloqueado cadastrado com sucesso! 🔒");
      
      setFormData({
        idMedico: "", 
        data: "", 
        horaInicio: "", 
        horaFim: "", 
        motivo: "",
      });
        
    } catch (error) {
      console.error("Erro na requisição:", error);
      alert("Não foi possível conectar ao servidor.");
    }
  }

  return (
    <div className="pacientes-container">
      <MenuLateral menuAberto={menuAberto} setMenuAberto={setMenuAberto} />

      <main className={`main-content ${menuAberto ? "expanded" : ""}`}>
        <section className="page-header">
          <h1>Cadastro de Horário Bloqueado</h1>
          <p>Impeça novos agendamentos definindo ausências, reuniões ou férias médicas.</p>
        </section>

        <section className="form-section">
          <form className="patient-form" onSubmit={handleSubmit}>
            
            <fieldset className="form-group-box">
              <legend>Dados de Configuração do Bloqueio</legend>
              
              <div className="form-row">
                {/* LISTA SUSPENSA DE MÉDICOS */}
                <div className="input-group">
                  <label>Médico</label>
                  <select 
                    name="idMedico" 
                    value={formData.idMedico} 
                    onChange={handleChange} 
                    required
                  >
                    <option value="">
                      {carregandoMedicos ? "Carregando médicos..." : "Selecione o Médico"}
                    </option>
                    {medicos.map((medico) => (
                      <option key={medico.id} value={medico.id}>
                        {medico.nome} {medico.sobrenome} ({medico.especialidade})
                      </option>
                    ))}
                  </select>
                </div>

                {/* CORREÇÃO: Input do tipo DATE para enviar o dia específico */}
                <div className="input-group">
                  <label>Data do Bloqueio</label>
                  <input 
                    type="date" 
                    name="data" 
                    value={formData.data} 
                    onChange={handleChange} 
                    required 
                  />
                </div>
              </div>

              <div className="form-row3">
                <div className="input-group">
                  <label>Horário de Início</label>
                  <input type="time" name="horaInicio" value={formData.horaInicio} onChange={handleChange} required />
                </div>

                <div className="input-group">
                  <label>Horário de Fim</label>
                  <input type="time" name="horaFim" value={formData.horaFim} onChange={handleChange} required />
                </div>

                <div className="input-group">
                  <label>Motivo do Bloqueio</label>
                  <input 
                    type="text" 
                    name="motivo" 
                    value={formData.motivo} 
                    onChange={handleChange} 
                    required 
                    placeholder="Ex: Férias, Congresso, Particular" 
                  />
                </div>
              </div>

            </fieldset>

            <button type="submit" className="save-button">
              Concluir Cadastro do Bloqueio
            </button>
          </form>
        </section>
      </main>
    </div>
  );
}

export default HorarioBloqueado;