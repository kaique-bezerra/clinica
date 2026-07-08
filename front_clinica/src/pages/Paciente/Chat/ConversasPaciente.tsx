import { useEffect, useRef, useState } from "react";
import MenuLateral from "../componentes/MenuLateral";
import "./ConversasPaciente.css";

interface Mensagem {
  id: number;
  conteudo: string;
  papel: "USER" | "ASSISTANT";
  dataHora: string;
}

function ConversasPaciente() {
  const [menuAberto, setMenuAberto] = useState<boolean>(false);
  const [mensagemInput, setMensagemInput] = useState<string>("");
  const [mensagens, setMensagens] = useState<Mensagem[]>([]);

  const mensagensRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    carregarMensagens();
  }, []);

  useEffect(() => {
    mensagensRef.current?.scrollTo({
      top: mensagensRef.current.scrollHeight,
      behavior: "smooth",
    });
  }, [mensagens]);

  async function carregarMensagens() {
    const token = localStorage.getItem("token");

    const response = await fetch("http://localhost:8080/chat", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (!response.ok) return;

    const dados = await response.json();

    setMensagens(dados);
  }

  async function enviarMensagem() {
    if (!mensagemInput.trim()) return;

    const token = localStorage.getItem("token");
    const pergunta = mensagemInput;

    setMensagemInput("");

    try {
      const response = await fetch("http://localhost:8080/chat", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
          pergunta,
        }),
      });

      if (!response.ok) throw new Error();

      await carregarMensagens();
    } catch (error) {
      console.error(error);
    }
  }

  return (
    <div className="conversas-container">
      <MenuLateral
        menuAberto={menuAberto}
        setMenuAberto={setMenuAberto}
      />

      <main className={`main-content ${menuAberto ? "expanded" : ""}`}>
        <section className="chat-layout">
          <aside className="lista-conversas">
            <h2>Conversas</h2>

            <div className="contato ativo">
              🤖 Assistente Virtual
            </div>
          </aside>

          <section className="chat-area">
            <div className="chat-header">
              🤖 Assistente Virtual
            </div>

            <div className="mensagens" ref={mensagensRef}>
              {mensagens.map((mensagem) => (
                <div
                  key={mensagem.id}
                  className={
                    mensagem.papel === "USER"
                      ? "mensagem usuario"
                      : "mensagem ia"
                  }
                >
                  {mensagem.conteudo}
                </div>
              ))}
            </div>

            <div className="chat-input">
              <input
                type="text"
                placeholder="Digite uma mensagem..."
                value={mensagemInput}
                onChange={(e) => setMensagemInput(e.target.value)}
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