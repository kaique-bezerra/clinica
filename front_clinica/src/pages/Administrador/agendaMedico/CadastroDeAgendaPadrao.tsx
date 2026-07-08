import { useState, useEffect } from "react";
import MenuLateral from "../Componentes/MenuLateral";
import "./CadastroDeAgendaPadrao.css";

interface Medico {
  id: number;
  nome: string;
  sobrenome: string;
  especialidade: string;
}

function CadastroDeAgendaPadrao() {

  const [mensagem, setMensagem] = useState("");
  const [menuAberto, setMenuAberto] = useState(false);
  const [medicos, setMedicos] = useState<Medico[]>([]);
  const [carregandoMedicos, setCarregandoMedicos] = useState(true);

  const [formData, setFormData] = useState({
    idMedico: "",
    diaSemana: "",
    horaInicio: "",
    horaFim: "",
    intervaloMinutos: "",
  });

  useEffect(() => {
    async function buscarMedicos() {
      const token = localStorage.getItem("token");
      try {
        const response = await fetch("http://localhost:8080/medico", {
          method: "GET",
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
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

  function handleChange(
    event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>,
  ) {
    const { name, value } = event.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  }

  async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();

    const token = localStorage.getItem("token");

    const formatarHora = (hora: string) => {
      if (!hora) return "";
      return hora.length === 5 ? `${hora}:00` : hora;
    };

    const payloadValido = {
      idMedico: Number(formData.idMedico),
      diaSemana: formData.diaSemana,
      horaInicio: formatarHora(formData.horaInicio),
      horaFim: formatarHora(formData.horaFim),
      intervaloMinutos: Number(formData.intervaloMinutos),
    };

    try {
      const response = await fetch("http://localhost:8080/agendas", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(payloadValido),
      });

      if (!response.ok) {
        if (response.status === 403) {
          setMensagem(
            "Erro 403: Seu usuário não tem permissão para cadastrar agendas.",
          );
          return;
        }
        const textError = await response.text();
        setMensagem(`Erro ao cadastrar a agenda: ${textError}`);
        return;
      }

      setMensagem("Agenda cadastrada com sucesso! 🎉");

      setFormData({
        idMedico: "",
        diaSemana: "",
        horaInicio: "",
        horaFim: "",
        intervaloMinutos: "",
      });
    } catch (error) {
      console.error("Erro na requisição:", error);
      setMensagem("Não foi possível conectar ao servidor.");
    }
  }

  return (
    <div className="pacientes-container">
      <MenuLateral menuAberto={menuAberto} setMenuAberto={setMenuAberto} />

      <main className={`main-content ${menuAberto ? "expanded" : ""}`}>
        <section className="page-header">
          <h1>Cadastro de Agenda Padrão</h1>
          <p>
            Configure os dias e horários de atendimento dos médicos disponíveis.
          </p>
        </section>

        <section className="form-section">
          <form className="patient-form" onSubmit={handleSubmit}>
            <fieldset className="form-group-box">
              <legend>Dados de Configuração da Agenda</legend>

              <div className="form-row">
                <div className="input-group">
                  <label>Médico</label>
                  <select
                    name="idMedico"
                    value={formData.idMedico}
                    onChange={handleChange}
                    required
                  >
                    <option value="">
                      {carregandoMedicos
                        ? "Carregando médicos..."
                        : "Selecione o Médico"}
                    </option>
                    {medicos.map((medico) => (
                      <option key={medico.id} value={medico.id}>
                        {medico.nome} {medico.sobrenome} ({medico.especialidade}
                        )
                      </option>
                    ))}
                  </select>
                </div>

                <div className="input-group">
                  <label>Dia da Semana</label>
                  <select
                    name="diaSemana"
                    value={formData.diaSemana}
                    onChange={handleChange}
                    required
                  >
                    <option value="">Selecione o Dia</option>
                    <option value="MONDAY">Segunda-feira</option>
                    <option value="TUESDAY">Terça-feira</option>
                    <option value="WEDNESDAY">Quarta-feira</option>
                    <option value="THURSDAY">Quinta-feira</option>
                    <option value="FRIDAY">Sexta-feira</option>
                    <option value="SATURDAY">Sábado</option>
                    <option value="SUNDAY">Domingo</option>
                  </select>
                </div>
              </div>

              <div className="form-row3">
                <div className="input-group">
                  <label>Horário de Início</label>
                  <input
                    type="time"
                    name="horaInicio"
                    value={formData.horaInicio}
                    onChange={handleChange}
                    required
                  />
                </div>

                <div className="input-group">
                  <label>Horário de Fim</label>
                  <input
                    type="time"
                    name="horaFim"
                    value={formData.horaFim}
                    onChange={handleChange}
                    required
                  />
                </div>

                <div className="input-group">
                  <label>Intervalo de Consulta (Minutos)</label>
                  <input
                    type="number"
                    name="intervaloMinutos"
                    value={formData.intervaloMinutos}
                    onChange={handleChange}
                    required
                    placeholder="Ex: 30"
                  />
                </div>
              </div>
            </fieldset>

            <button type="submit" className="save-button">
              Concluir Cadastro da Agenda
            </button>
            {mensagem && (
              <p style={{ marginTop: "10px", color: "blue" }}>{mensagem}</p>
            )}
          </form>
        </section>
      </main>
    </div>
  );
}

export default CadastroDeAgendaPadrao;
