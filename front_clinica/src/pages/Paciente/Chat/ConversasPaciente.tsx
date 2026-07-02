import { useState } from "react";
import MenuLateral from "../componentes/MenuLateral";
import "./ConversasPaciente.css";

interface Mensagem {
  texto: string;
  tipo: "ia" | "usuario";
}

function ConversasPaciente() {
  const [menuAberto, setMenuAberto] =
    useState<boolean>(false);

  const [mensagens, setMensagens] =
    useState<Mensagem[]>([
      {
        texto: "Olá! Como posso ajudar?",
        tipo: "ia",
      },
    ]);

  const [mensagemInput, setMensagemInput] =
    useState<string>("");

  async function enviarMensagem() {
    if (!mensagemInput.trim()) return;

    const token = localStorage.getItem("token");

    const novaMensagemUsuario: Mensagem = {
      texto: mensagemInput,
      tipo: "usuario",
    };

    setMensagens((prev) => [
      ...prev,
      novaMensagemUsuario,
    ]);

    const pergunta = mensagemInput;

    setMensagemInput("");

    try {
      const response = await fetch(
        "http://localhost:8080/chat",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify({
            pergunta: pergunta,
          }),
        }
      );

      if (!response.ok) {
        throw new Error(
          "Erro ao buscar resposta da IA."
        );
      }

      const respostaIa = await response.text();

      setMensagens((prev) => [
        ...prev,
        {
          texto: respostaIa,
          tipo: "ia",
        },
      ]);
    } catch (error) {
      setMensagens((prev) => [
        ...prev,
        {
          texto:
            "Erro ao conectar com o assistente.",
          tipo: "ia",
        },
      ]);

      console.error(error);
    }
  }

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
              {mensagens.map((msg, index) => (
                <div
                  key={index}
                  className={`msg ${msg.tipo}`}
                >
                  {msg.texto}
                </div>
              ))}
            </div>

            <div className="chat-input">
              <input
                type="text"
                placeholder="Digite uma mensagem..."
                value={mensagemInput}
                onChange={(e) =>
                  setMensagemInput(e.target.value)
                }
                onKeyDown={(e) => {
                  if (e.key === "Enter") {
                    enviarMensagem();
                  }
                }}
              />

              <button onClick={enviarMensagem}>
                Enviar
              </button>
            </div>
          </section>
        </section>
      </main>
    </div>
  );
}

export default ConversasPaciente;