import { useState } from "react";
import MenuLateral from "../componentes/MenuLateral";
import "./PerfilPaciente.css";

function PerfilPaciente() {

  const [menuAberto, setMenuAberto] = useState(false);

  return (
    <div className="perfil-container">

      <MenuLateral
        menuAberto={menuAberto}
        setMenuAberto={setMenuAberto}
      />

      <main
        className={`main-content ${
          menuAberto ? "expanded" : ""
        }`}
      >

        <section className="page-header">

          <h1>Meu Perfil</h1>

          <p>
            Visualize e mantenha seus dados atualizados.
          </p>

        </section>

        <section className="perfil-card">

          <div className="perfil-topo">

            <div className="foto-perfil">
              MS
            </div>

            <div>
              <h2>Maria Silva</h2>
              <p>Paciente</p>
            </div>

          </div>

        </section>

        <div className="perfil-grid">

          <section className="info-card">

            <h3>Dados Pessoais</h3>

            <p><strong>Nome:</strong> Maria Silva</p>
            <p><strong>Email:</strong> maria@email.com</p>
            <p><strong>Telefone:</strong> (85) 99999-9999</p>
            <p><strong>Sexo:</strong> Feminino</p>
            <p><strong>Idade:</strong> 27 anos</p>
            <p><strong>Data de Nascimento:</strong> 10/03/1999</p>
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

        <section className="acoes-card">

          <button className="btn-editar">
            Editar Perfil
          </button>

        </section>

      </main>

    </div>
  );
}

export default PerfilPaciente;