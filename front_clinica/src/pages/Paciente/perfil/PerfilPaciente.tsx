import { useState } from "react";
import MenuLateral from "../componentes/MenuLateral";
import ChatFlutuante from "../componentes/ChatFlutuante";
import "./PerfilPaciente.css";

function PerfilPaciente() {
  const [menuAberto, setMenuAberto] = useState(false);

  return (
    <div className="perfil-container">
      <MenuLateral
        menuAberto={menuAberto}
        setMenuAberto={setMenuAberto}
      />

      <ChatFlutuante />

      <main
        className={`main-content ${
          menuAberto ? "expanded" : ""
        }`}
      >
        <section className="perfil-hero">
          <div className="perfil-info">
            <div className="foto-perfil">
              MS
            </div>

            <div>
              <h1>Maria Silva</h1>

              <div className="perfil-contatos">
                <span>📧 maria@email.com</span>
                <span>📱 (85) 99999-9999</span>
              </div>
            </div>
          </div>

          <button className="btn-editar">
            Editar Perfil
          </button>
        </section>

        <section className="metricas-grid">
          <div className="metrica-card">
            <h2>68 kg</h2>
            <span>Peso</span>
          </div>

          <div className="metrica-card">
            <h2>1,65 m</h2>
            <span>Altura</span>
          </div>

          <div className="metrica-card">
            <h2>O+</h2>
            <span>Tipo Sanguíneo</span>
          </div>

          <div className="metrica-card">
            <h2>2</h2>
            <span>Alergias</span>
          </div>
        </section>

        <section className="status-grid">
          <div className="status-card">
            <h3>Última Consulta</h3>
            <p>15/05/2026</p>
          </div>

          <div className="status-card">
            <h3>Próxima Consulta</h3>
            <p>10/06/2026</p>
          </div>

          <div className="status-card">
            <h3>Exames Pendentes</h3>
            <p>2</p>
          </div>

          <div className="status-card">
            <h3>Medicamentos</h3>
            <p>3 Ativos</p>
          </div>
        </section>

        <div className="info-grid">
          <section className="info-card">
            <h3>Dados Pessoais</h3>

            <p><strong>Nome:</strong> Maria Silva</p>
            <p><strong>Email:</strong> maria@email.com</p>
            <p><strong>Telefone:</strong> (85) 99999-9999</p>
            <p><strong>Sexo:</strong> Feminino</p>
            <p><strong>Idade:</strong> 27 anos</p>
            <p><strong>Nascimento:</strong> 10/03/1999</p>
            <p><strong>Profissão:</strong> Professora</p>
          </section>

          <section className="info-card">
            <h3>Dados Clínicos</h3>

            <p><strong>Peso:</strong> 68 kg</p>
            <p><strong>Altura:</strong> 1,65 m</p>
            <p><strong>Tipo Sanguíneo:</strong> O+</p>
            <p><strong>Alergias:</strong> Penicilina</p>
            <p><strong>Doenças Crônicas:</strong> Nenhuma</p>
          </section>

          <section className="info-card">
            <h3>Endereço</h3>

            <p><strong>Rua:</strong> Rua das Flores</p>
            <p><strong>Número:</strong> 123</p>
            <p><strong>Bairro:</strong> Centro</p>
            <p><strong>Cidade:</strong> Fortaleza</p>
            <p><strong>CEP:</strong> 60000-000</p>
          </section>

          <section className="info-card">
            <h3>Convênio</h3>

            <p><strong>Plano:</strong> Unimed</p>
            <p><strong>Número:</strong> 123456789</p>
            <p><strong>Validade:</strong> 12/2027</p>
          </section>
        </div>
      </main>
    </div>
  );
}

export default PerfilPaciente;