import { useEffect, useState } from "react";
import ChatFlutuante from "../componentes/ChatFlutuante";
import MenuLateral from "../componentes/MenuLateral";
import "./PerfilPaciente.css";

function PerfilPaciente() {
  interface Paciente {
    id: number;
    nome: string;
    sobrenome: string;
    email: string;
    telefone: string;
    sexo: string;
    profissao: string;
    dataNascimento: string;
    idade: number;

    rua: string;
    numero: number;
    bairro: string;
    cidade: string;
    estado: string;
    cep: string;

    plano: string;
    numeroConvenio: string;
    data: string;

    dadosClinicos: {
      peso: number;
      altura: number;
      tipoSanguineo: string;
      alergias: string[];
      doencasCronicas: string[];
    };
  }

  const idPaciente = Number(localStorage.getItem("idUsuario"));

  const [menuAberto, setMenuAberto] = useState(false);
  const [paciente, setPaciente] = useState<Paciente | null>(null);

  useEffect(() => {
    async function carregarPerfil() {
      try {
        const token = localStorage.getItem("token");


        const response = await fetch(
          `http://localhost:8080/paciente/${idPaciente}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          },
        );


        const dados = await response.json();


        setPaciente(dados);

      } catch (erro) {
        console.error("ERRO:", erro);
      }
    }

    carregarPerfil();
  }, [idPaciente]);

  return (
    <div className="perfil-container">
      <MenuLateral menuAberto={menuAberto} setMenuAberto={setMenuAberto} />

      <ChatFlutuante />

      <main className={`main-content ${menuAberto ? "expanded" : ""}`}>
        <section className="perfil-hero">
          <div className="perfil-info">
            <div className="foto-perfil">MS</div>

            <div>
              <h1>{paciente?.nome + " " + paciente?.sobrenome}</h1>

              <div className="perfil-contatos">
                <span>📧 {paciente?.email}</span>
                <span>📱 {paciente?.telefone}</span>
              </div>
            </div>
          </div>

          <button className="btn-editar">Editar Perfil</button>
        </section>

        <section className="metricas-grid">
          <div className="metrica-card">
            <h2>{paciente?.dadosClinicos?.peso + "kg"}</h2>
            <span>Peso</span>
          </div>

          <div className="metrica-card">
            <h2>{paciente?.dadosClinicos.altura + " m"}</h2>
            <span>Altura</span>
          </div>

          <div className="metrica-card">
            <h2>{paciente?.dadosClinicos.tipoSanguineo}</h2>
            <span>Tipo Sanguíneo</span>
          </div>

          <div className="metrica-card">
            <h2>{paciente?.dadosClinicos.alergias.length}</h2>
            <span>Alergias</span>
          </div>
        </section>

        <div className="info-grid">
          <section className="info-card">
            <h3>Dados Pessoais</h3>

            <p>
              <strong>Nome:</strong>{" "}
              {paciente?.nome + " " + paciente?.sobrenome}
            </p>
            <p>
              <strong>Email:</strong> {paciente?.email}
            </p>
            <p>
              <strong>Telefone:</strong> {paciente?.telefone}
            </p>
            <p>
              <strong>Sexo:</strong>
              {paciente?.sexo === "F"
                ? "Feminino"
                : paciente?.sexo === "M"
                  ? "Masculino"
                  : "Não informado"}
            </p>
            <p>
              <strong>Idade:</strong> {paciente?.idade + " anos"}
            </p>
            <p>
              <strong>Nascimento:</strong> {paciente?.dataNascimento}
            </p>
            <p>
              <strong>Profissão:</strong> {paciente?.profissao}
            </p>
          </section>

          <section className="info-card">
            <h3>Dados Clínicos</h3>

            <p>
              <strong>Peso:</strong> {paciente?.dadosClinicos.peso + " kg"}
            </p>
            <p>
              <strong>Altura:</strong> {paciente?.dadosClinicos.altura + " m"}
            </p>
            <p>
              <strong>Tipo Sanguíneo:</strong>
              {paciente?.dadosClinicos.tipoSanguineo}
            </p>
            <p>
              <strong>Alergias:</strong>{" "}
              {paciente?.dadosClinicos.alergias.length === 0
                ? "Sem registro"
                : paciente?.dadosClinicos.alergias}
            </p>
            <p>
              <strong>Doenças Crônicas:</strong>
              {paciente?.dadosClinicos.doencasCronicas.length === 0
                ? "Sem registro"
                : paciente?.dadosClinicos.doencasCronicas}{" "}
            </p>
          </section>

          <section className="info-card">
            <h3>Endereço</h3>

            <p>
              <strong>Rua:</strong> {paciente?.rua}
            </p>
            <p>
              <strong>Número:</strong> {paciente?.numero}
            </p>
            <p>
              <strong>Bairro:</strong> {paciente?.bairro}
            </p>
            <p>
              <strong>Cidade:</strong> {paciente?.cidade}
            </p>
            <p>
              <strong>CEP:</strong> {paciente?.cep}
            </p>
          </section>

          <section className="info-card">
            <h3>Convênio</h3>

            <p>
              <strong>Plano:</strong> {paciente?.plano}
            </p>
            <p>
              <strong>Número:</strong> {paciente?.numeroConvenio}
            </p>
            <p>
              <strong>Validade:</strong> {paciente?.data}
            </p>
          </section>
        </div>
      </main>
    </div>
  );
}

export default PerfilPaciente;
