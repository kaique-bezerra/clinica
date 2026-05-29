import { useState } from "react";
import "../../Recepcionista/cadastroDePacientes/Pacientes.css";
import MenuLateral from "../Componentes/MenuLateral";

function CadastroDePacientesAdmin() {

  const [menuAberto, setMenuAberto] = useState(false);

  const [paciente, setPaciente] = useState("");
  const [cpf, setCpf] = useState("");
  const [telefone,setTelefone] = useState("");
  const [data, setData] = useState("");
  const [convenio,setConvenio] = useState("");


  function handleSubmit(
    event: React.SubmitEvent<HTMLFormElement>
  ) {

    event.preventDefault();

    console.log("Paciente cadastrado com sucesso!");
  }

    const [mensagem, setMensagem] = useState("");

  function agendarConsulta() {

    if (!paciente || !cpf || !telefone || !data || !convenio) {
      setMensagem(" Preencha todos os campos!");
      return;
    }

    setMensagem(" Consulta agendada com sucesso!");

    setPaciente("");
    setCpf("");
    setTelefone("");
    setData("");
    setConvenio("");
  }

    let mensagemTela = null;

  if (mensagem !== "") {
    mensagemTela = (
      <p style={{ marginTop: "10px" }}>
        {mensagem}
      </p>
    );
  }

  return (
    <div className="pacientes-container">

    <MenuLateral menuAberto={menuAberto}
      setMenuAberto={setMenuAberto}/>

      <main
        className={`main-content ${
          menuAberto ? "expanded" : ""
        }`}
      >

        <section className="page-header">

          <h1>Cadastro de Pacientes</h1>

          <p>
            Cadastre novos pacientes e visualize os
            registros já existentes no sistema.
          </p>

        </section>

        <section className="form-section">

          <form className="patient-form" onSubmit={handleSubmit} >

            <div className="form-row">

              <div className="input-group">
                <label>Nome Completo</label>

                <input
                  type="text"
                  placeholder="Digite o nome do paciente"
                  value={paciente} onChange={(e)=> setPaciente(e.target.value)}
                  />
              </div>

              <div className="input-group">
                <label>CPF</label>

                <input
                  type="text"
                  placeholder="000.000.000-00"
                  value={cpf} onChange={(e)=> setCpf(e.target.value)}

                />
              </div>

            </div>

            <div className="form-row">

              <div className="input-group">
                <label>Telefone</label>

                <input
                  type="text"
                  placeholder="(83) 99999-9999"
                  value={telefone} onChange={(e)=> setTelefone(e.target.value)}
                />
              </div>

              <div className="input-group">
                <label>Data de Nascimento</label>

                <input type="date" 
                value={data} onChange={(e)=> setData(e.target.value)}/>
              </div>

            </div>

            <div className="form-row">

              <div className="input-group full-width">
                <label>Convênio</label>

                <input
                  type="text"
                  placeholder="Digite o convênio"
                  value={convenio} onChange={(e)=> setConvenio(e.target.value)}
                />
              </div>

            </div>

            <button type="submit" className="save-button" onClick={agendarConsulta}>
              Cadastrar Paciente
            </button>

          {mensagemTela}
          </form>

        </section>

        <section className="patients-table-section">

          <div className="table-header">

            <h2>Pacientes Cadastrados</h2>

            <input
              type="text"
              placeholder="Buscar por nome ou CPF"
              className="search-input"
            />

          </div>

          <table>

            <thead>

              <tr>
                <th>Nome</th>
                <th>CPF</th>
                <th>Telefone</th>
                <th>Convênio</th>
              </tr>

            </thead>

            <tbody>

              <tr>
                <td>Maria Silva</td>
                <td>123.456.789-00</td>
                <td>(83) 99999-1111</td>
                <td>Unimed</td>
              </tr>

              <tr>
                <td>Carlos Henrique</td>
                <td>987.654.321-00</td>
                <td>(83) 98888-2222</td>
                <td>Hapvida</td>
              </tr>

              <tr>
                <td>Fernanda Lima</td>
                <td>741.852.963-00</td>
                <td>(83) 97777-3333</td>
                <td>Particular</td>
              </tr>

            </tbody>

          </table>

        </section>

      </main>

    </div>
  );
}  

export default CadastroDePacientesAdmin;