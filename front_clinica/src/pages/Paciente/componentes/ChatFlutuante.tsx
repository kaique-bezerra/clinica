import { Bot, X } from "lucide-react";
import { useEffect, useRef, useState } from "react";
import "./ChatFlutuante.css";

interface Mensagem {
  id: number;
  conteudo: string;
  papel: "USER" | "ASSISTANT";
  dataHora: string;
}

const ChatFlutuante = () => {
  const [aberto, setAberto] = useState(false);
  const [mensagemInput, setMensagemInput] = useState("");
  const [mensagens, setMensagens] = useState<Mensagem[]>([]);

  useEffect(() => {
    carregarMensagens();
  }, []);

  const chatBodyRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (chatBodyRef.current) {
      setTimeout(() => {
        chatBodyRef.current!.scrollTop = chatBodyRef.current!.scrollHeight;
      }, 0);
    }
  }, [mensagens, aberto]);

  async function carregarMensagens() {
    const token = localStorage.getItem("token");

    const response = await fetch("http://localhost:8080/chat", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (!response.ok) return;

    const dados = await response.json();

    console.log(dados);

    setMensagens(dados);
  }

  async function enviarMensagem() {
    if (!mensagemInput.trim()) return;

    const token = localStorage.getItem("token");

    try {
      const response = await fetch("http://localhost:8080/chat", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
          pergunta: mensagemInput,
        }),
      });

      if (!response.ok) {
        throw new Error();
      }

      setMensagemInput("");

      await carregarMensagens();
    } catch (error) {
      console.error(error);
    }
  }

  const popupRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    function handleClick(event: MouseEvent) {
      if (
        aberto &&
        popupRef.current &&
        !popupRef.current.contains(event.target as Node)
      ) {
        setAberto(false);
      }
    }

    document.addEventListener("mousedown", handleClick);

    return () => document.removeEventListener("mousedown", handleClick);
  }, [aberto]);

  return (
    <>
      <button className="syncia-button" onClick={() => setAberto(!aberto)}>
        <div className="syncia-icon">
          <Bot size={33} />
          <span className="sparkle">✨</span>
        </div>

        <div className="syncia-content">
          <span className="syncia-title">SyncIA</span>
          <span className="syncia-subtitle">Sua assistente virtual</span>
        </div>
      </button>

      {aberto && (
        <div className="chat-popup" ref={popupRef}>
          <div className="chat-header">
            <div>Assistente Virtual</div>
            <button className="fechar-chat" onClick={() => setAberto(false)}>
              <X size={20} />
            </button>
          </div>
          <div className="chat-body" ref={chatBodyRef}>
            {mensagens.length === 0 && (
              <div className="mensagem ia">Olá! Como posso ajudar?</div>
            )}

            {mensagens.map((mensagem, index) => (
              <div
                key={mensagem.id ?? index}
                className={
                  mensagem.papel === "USER" ? "mensagem usuario" : "mensagem ia"
                }
              >
                {mensagem.conteudo}
              </div>
            ))}
          </div>

          <div className="chat-footer">
            <input
              type="text"
              placeholder="Digite sua mensagem..."
              value={mensagemInput}
              onChange={(e) => setMensagemInput(e.target.value)}
              onKeyDown={(e) => {
                if (e.key === "Enter") {
                  enviarMensagem();
                }
              }}
            />

            <button onClick={enviarMensagem}>Enviar</button>
          </div>
        </div>
      )}
    </>
  );
};

export default ChatFlutuante;
