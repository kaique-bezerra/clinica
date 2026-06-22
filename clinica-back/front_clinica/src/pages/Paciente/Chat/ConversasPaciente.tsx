import { useState } from "react";
import MenuLateral from "../componentes/MenuLateral";
import "./ConversasPaciente.css";

function ConversasPaciente() {

  const [menuAberto, setMenuAberto] =
    useState(false);

  return (
    <div className="conversas-container">

      <MenuLateral
        menuAberto={menuAberto}
        setMenuAberto={setMenuAberto}
      />

      <main
        className={`main-content ${
          menuAberto ? "expanded" : ""
        }`}
      >

        <section className="chat-layout">

          <aside className="lista-conversas">

            <h2>Conversas</h2>

            <div className="contato ativo">
              🤖 Assistente Virtual
            </div>

            <div className="contato">
              👨‍⚕️ Dr. João
            </div>

            <div className="contato">
              👩‍⚕️ Dra. Ana
            </div>

            <div className="contato">
              🏥 Recepção
            </div>

          </aside>

          <section className="chat-area">

            <div className="chat-header">
              🤖 Assistente Virtual
            </div>

            <div className="mensagens">

              <div className="msg ia">
                Olá! Como posso ajudar?
              </div>

              <div className="msg usuario">
                Quero ver minhas consultas.
              </div>

            </div>

            <div className="chat-input">

              <input
                type="text"
                placeholder="Digite uma mensagem..."
              />

              <button>Enviar</button>

            </div>

          </section>

        </section>

      </main>

    </div>
  );
}

export default ConversasPaciente;