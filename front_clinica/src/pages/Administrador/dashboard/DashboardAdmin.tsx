import { useEffect, useState } from "react";
import imagem from "../../../assets/logo2.jpeg";
import "../../Recepcionista/Dashboard/Dashboard.css";
import MenuLateral from "../Componentes/MenuLateral";

function DashboardAdmin() {
  const [menuAberto, setMenuAberto] = useState(false);

  const [recepcionistas, setRecepcionistas] = useState<any[]>([]);
  const [medicos, setMedicos] = useState<any[]>([]);
  const [pacientes, setPacientes] = useState<any[]>([]);

  const [tipoSelecionado, setTipoSelecionado] = useState("recepcionistas");
  const [pesquisa, setPesquisa] = useState("");

  const [carregando, setCarregando] = useState(true);

  const nomeAdmin = localStorage.getItem("nomeUsuario") || "Administrador";

  useEffect(() => {
    async function carregarDados() {
      try {
        const token = localStorage.getItem("token");

        const headers = {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        };

        const [resRecep, resMedicos, resPacientes] = await Promise.all([
          fetch("http://localhost:8080/recepcionista", { headers }),
          fetch("http://localhost:8080/medico", { headers }),
          fetch("http://localhost:8080/paciente", { headers }),
        ]);

        const dadosRecep = await resRecep.json();
        const dadosMedicos = await resMedicos.json();
        const dadosPacientes = await resPacientes.json();

        setRecepcionistas(dadosRecep);
        setMedicos(dadosMedicos);
        setPacientes(dadosPacientes);

      } catch (error) {
        console.error(error);
      } finally {
        setCarregando(false);
      }
    }

    carregarDados();
  }, []);

  const listaAtual =
    tipoSelecionado === "recepcionistas"
      ? recepcionistas
      : tipoSelecionado === "medicos"
      ? medicos
      : pacientes;

  const listaFiltrada = listaAtual.filter((usuario) =>
    usuario.nome?.toLowerCase().includes(pesquisa.toLowerCase()) ||
    usuario.cpf?.includes(pesquisa)
  );

  return (
    <div className="dashboard-container">

      <MenuLateral
        menuAberto={menuAberto}
        setMenuAberto={setMenuAberto}
      />

      <main className={`main-content ${menuAberto ? "expanded" : ""}`}>

        <section className="welcome-card">
          <div className="welcome-text">

            <div className="Imagemlogo">
              <img src={imagem} alt="Logo da Clínica" />
            </div>

            <h1>Bem-vindo, DEV {nomeAdmin}!</h1>

            <p>
              Gerencie os usuários cadastrados no sistema.
            </p>

          </div>
        </section>

        <section className="cards-container">

          <div className="card">
            <h2>{carregando ? "..." : recepcionistas.length}</h2>
            <p>Recepcionistas</p>
          </div>

          <div className="card">
            <h2>{carregando ? "..." : medicos.length}</h2>
            <p>Médicos</p>
          </div>

          <div className="card">
            <h2>{carregando ? "..." : pacientes.length}</h2>
            <p>Pacientes</p>
          </div>

        </section>

        {/* BOTÕES */}
        <section className="buttons-section">
          <div className="botoes">
          <button
            className={`view-button ${
              tipoSelecionado === "recepcionistas" ? "active-btn" : ""
            }`}
            onClick={() => {
              setTipoSelecionado("recepcionistas");
              setPesquisa("");
            }}
          >
            Ver Recepcionistas
          </button>

          <button
            className={`view-button ${
              tipoSelecionado === "medicos" ? "active-btn" : ""
            }`}
            onClick={() => {
              setTipoSelecionado("medicos");
              setPesquisa("");
            }}
          >
            Ver Médicos
          </button>

          <button
            className={`view-button ${
              tipoSelecionado === "pacientes" ? "active-btn" : ""
            }`}
            onClick={() => {
              setTipoSelecionado("pacientes");
              setPesquisa("");
            }}
          >
            Ver Pacientes
          </button>
        </div>
        <div>
                    <section className="search-section">
          <input
            type="text"
            className="search-input"
            placeholder={`Buscar ${
              tipoSelecionado === "recepcionistas"
                ? "recepcionista"
                : tipoSelecionado === "medicos"
                ? "médico"
                : "paciente"
            } por nome ou CPF...`}
            value={pesquisa}
            onChange={(e) => setPesquisa(e.target.value)}
          />
        </section>

        </div>
        </section>

        {/* BARRA DE PESQUISA */}

        <section className="appointments-section">
          <h2>Perfis Cadastrados</h2>

          <table>
            <thead>
              <tr>
                <th>Nome</th>
                <th>Sobrenome</th>
                <th>CPF</th>
                <th>Email</th>
                <th>Telefone</th>
              </tr>
            </thead>

            <tbody>
              {listaFiltrada.length > 0 ? (
                listaFiltrada.map((usuario) => (
                  <tr key={usuario.id}>
                    <td>{usuario.nome}</td>
                    <td>{usuario.sobrenome}</td>
                    <td>{usuario.cpf}</td>
                    <td>{usuario.email}</td>
                    <td>{usuario.telefone}</td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan={5} style={{ textAlign: "center" }}>
                    Nenhum resultado encontrado.
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </section>

      </main>
    </div>
  );
}

export default DashboardAdmin;