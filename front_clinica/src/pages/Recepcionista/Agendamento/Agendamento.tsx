import { useState, useEffect } from "react";
import "../../Recepcionista/Agendamento/Agendamento.css";
import MenuLateral from "../componentes/MenuLateral";

interface Medico {
  id: number;
  nome: string;
  sobrenome: string;
  especialidade: string;
}

interface Paciente {
  id: number;
  nome: string;
  cpf: string;
  sobrenome: string;
  email: string;
}

interface HorarioDisponivel {
  hora: string;
}

function Agendamento() {
  const [mensagem, setMensagem] = useState("");

  const [menuAberto, setMenuAberto] = useState(false);

  const [medicos, setMedicos] = useState<Medico[]>([]);
  const [carregandoMedicos, setCarregandoMedicos] = useState(true);

  const [pacientes, setPacientes] = useState<Paciente[]>([]);
  const [termoBuscaPaciente, setTermoBuscaPaciente] = useState("");
  const [sugestoesFiltradas, setSugestoesFiltradas] = useState<Paciente[]>([]);
  const [mostrarSugestoes, setMostrarSugestoes] = useState(false);

  const [horariosDisponiveis, setHorariosDisponiveis] = useState<
    HorarioDisponivel[]
  >([]);
  const [carregandoHorarios, setCarregandoHorarios] = useState(false);

  const [formData, setFormData] = useState({
    idPaciente: "",
    idMedico: "",
    dataConsulta: "",
    horaConsulta: "",
  });

  useEffect(() => {
    async function carregarDados() {
      const token = localStorage.getItem("token");
      const headers = {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      };

      try {
        const resMedicos = await fetch("http://localhost:8080/medico", {
          method: "GET",
          headers,
        });
        if (resMedicos.ok) {
          const dadosMedicos = await resMedicos.json();
          setMedicos(dadosMedicos);
        }
      } catch (err) {
        console.error("Erro ao buscar médicos:", err);
      } finally {
        setCarregandoMedicos(false);
      }

      try {
        const resPacientes = await fetch("http://localhost:8080/paciente", {
          method: "GET",
          headers,
        });
        if (resPacientes.ok) {
          const dadosPacientes = await resPacientes.json();
          setPacientes(dadosPacientes);
        }
      } catch (err) {
        console.error("Erro ao buscar pacientes:", err);
      }
    }

    carregarDados();
  }, []);

  useEffect(() => {
    async function buscarHorarios() {
      if (!formData.idMedico || !formData.dataConsulta) {
        setHorariosDisponiveis([]);
        return;
      }

      setCarregandoHorarios(true);
      const token = localStorage.getItem("token");

      try {
        const res = await fetch(
          `http://localhost:8080/disponibilidade/medico/${formData.idMedico}/dia/${formData.dataConsulta}`,
          {
            method: "GET",
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "application/json",
            },
          },
        );

        if (res.ok) {
          const horarios = await res.json();
          setHorariosDisponiveis(horarios);
        } else {
          setHorariosDisponiveis([]);
        }
      } catch (err) {
        console.error("Erro ao buscar horários disponíveis:", err);
        setHorariosDisponiveis([]);
      } finally {
        setCarregandoHorarios(false);
      }
    }

    buscarHorarios();
  }, [formData.idMedico, formData.dataConsulta]);

  function handleBuscaPacienteChange(
    event: React.ChangeEvent<HTMLInputElement>,
  ) {
    const valor = event.target.value;
    setTermoBuscaPaciente(valor);

    if (valor.trim() === "") {
      setSugestoesFiltradas([]);
      setMostrarSugestoes(false);
      setFormData((prev) => ({ ...prev, idPaciente: "" }));
      return;
    }

    const valorMinusculo = valor.toLowerCase();
    const termoApenasNumeros = valor.replace(/\D/g, "");

    const filtrados = pacientes.filter((paciente) => {
      const nomeCompleto =
        `${paciente.nome} ${paciente.sobrenome || ""}`.toLowerCase();
      const cpfPacienteNumeros = paciente.cpf
        ? paciente.cpf.replace(/\D/g, "")
        : "";

      const combinaNome = nomeCompleto.includes(valorMinusculo);
      const combinaCpf =
        termoApenasNumeros !== "" &&
        cpfPacienteNumeros.includes(termoApenasNumeros);

      return combinaNome || combinaCpf;
    });

    setSugestoesFiltradas(filtrados);
    setMostrarSugestoes(true);
  }

  function selecionarPaciente(paciente: Paciente) {
    setTermoBuscaPaciente(
      `${paciente.nome} ${paciente.sobrenome || ""} - CPF: ${paciente.cpf || "Não informado"}`,
    );
    setFormData((prev) => ({ ...prev, idPaciente: String(paciente.id) }));
    setMostrarSugestoes(false);
  }

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

    if (!formData.idPaciente) {
      setMensagem(
        "Por favor, selecione um paciente válido na lista de sugestões.",
      );
      return;
    }

    if (!formData.horaConsulta) {
      setMensagem("Por favor, selecione um horário válido.");
      return;
    }

    const token = localStorage.getItem("token");

    const formatarHora = (hora: string) => {
      if (!hora) return "";
      return hora.length === 5 ? `${hora}:00` : hora;
    };

    const payloadValido = {
      idPaciente: Number(formData.idPaciente),
      idMedico: Number(formData.idMedico),
      dataConsulta: formData.dataConsulta,
      horaConsulta: formatarHora(formData.horaConsulta),
    };

    try {
      const response = await fetch("http://localhost:8080/consultas", {
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
            "Erro 403: Seu usuário não tem permissão para agendar consultas.",
          );
          return;
        }
        const textError = await response.text();
        setMensagem(`Erro ao agendar consulta: ${textError}`);
        return;
      }

      setMensagem("Consulta agendada com sucesso! 🎉🗓️");
      setFormData({
        idPaciente: "",
        idMedico: "",
        dataConsulta: "",
        horaConsulta: "",
      });
      setTermoBuscaPaciente("");
      setHorariosDisponiveis([]);
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
          <h1>Agendamento de Consultas</h1>
          <p>Busque o paciente por nome ou CPF de forma rápida e organizada.</p>
        </section>

        <section className="form-section">
          <form
            className="patient-form"
            onSubmit={handleSubmit}
            autoComplete="off"
          >
            <fieldset className="form-group-box">
              <legend>Dados do Agendamento</legend>

              <div className="form-row">
                <div className="input-group" style={{ position: "relative" }}>
                  <label>Paciente (Nome ou CPF)</label>
                  <input
                    type="text"
                    value={termoBuscaPaciente}
                    onChange={handleBuscaPacienteChange}
                    onFocus={() =>
                      termoBuscaPaciente && setMostrarSugestoes(true)
                    }
                    placeholder="Digite o nome ou CPF..."
                    required
                  />

                  {mostrarSugestoes && sugestoesFiltradas.length > 0 && (
                    <div className="autocomplete-table-container">
                      <table className="autocomplete-table">
                        <thead>
                          <tr>
                            <th>Nome Completo</th>
                            <th>CPF</th>
                            <th>E-mail</th>
                          </tr>
                        </thead>
                        <tbody>
                          {sugestoesFiltradas.map((paciente) => (
                            <tr
                              key={paciente.id}
                              onClick={() => selecionarPaciente(paciente)}
                              className="autocomplete-row"
                            >
                              <td>
                                {paciente.nome} {paciente.sobrenome}
                              </td>
                              <td className="cpf-cell">{paciente.cpf}</td>
                              <td className="email-cell">{paciente.email}</td>
                            </tr>
                          ))}
                        </tbody>
                      </table>
                    </div>
                  )}

                  {mostrarSugestoes &&
                    termoBuscaPaciente &&
                    sugestoesFiltradas.length === 0 && (
                      <div className="autocomplete-table-container empty-msg">
                        Nenhum paciente encontrado com esses dados.
                      </div>
                    )}
                </div>

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
              </div>

              <div className="form-row">
                <div className="input-group">
                  <label>Data da Consulta</label>
                  <input
                    type="date"
                    name="dataConsulta"
                    value={formData.dataConsulta}
                    onChange={handleChange}
                    required
                  />
                </div>

                <div className="input-group">
                  <label>Horário da Consulta</label>
                  <select
                    name="horaConsulta"
                    value={formData.horaConsulta}
                    onChange={handleChange}
                    disabled={
                      !formData.idMedico ||
                      !formData.dataConsulta ||
                      carregandoHorarios
                    }
                    required
                  >
                    {carregandoHorarios ? (
                      <option value="">Buscando horários...</option>
                    ) : !formData.idMedico || !formData.dataConsulta ? (
                      <option value="">Selecione médico e data primeiro</option>
                    ) : horariosDisponiveis.length === 0 ? (
                      <option value="">
                        Nenhum horário livre para este dia
                      </option>
                    ) : (
                      <>
                        <option value="">Selecione um horário vago</option>
                        {horariosDisponiveis.map((item) => (
                          <option key={item.hora} value={item.hora}>
                            {item.hora.substring(0, 5)}
                          </option>
                        ))}
                      </>
                    )}
                  </select>
                </div>
              </div>
            </fieldset>

            <button type="submit" className="save-button">
              Concluir Agendamento
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

export default Agendamento;
