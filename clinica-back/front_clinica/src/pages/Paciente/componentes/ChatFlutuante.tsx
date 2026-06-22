import { useState } from "react";
import "./ChatFlutuante.css";
import { Bot } from "lucide-react";

const ChatFlutuante=()=>{

  const [aberto, setAberto] = useState(false);

  return (
    <>
     <button className="syncia-button" onClick={()=>setAberto(!aberto)}>
      <div className="syncia-icon">
        <Bot size={33} />
        <span className="sparkle">✨</span>
      </div>

      <div className="syncia-content">
        <span className="syncia-title">SyncIA</span>
        <span className="syncia-subtitle">
          Sua assistente virtual
        </span>
      </div>
    </button>

      {aberto && (
        <div className="chat-popup">

          <div className="chat-header">
            Assistente Virtual
          </div>

          <div className="chat-body">

            <div className="mensagem ia">
              Olá! Como posso ajudar?
            </div>

            <div className="mensagem usuario">
              Quero marcar uma consulta.
            </div>

          </div>

          <div className="chat-footer">
            <input
              type="text"
              placeholder="Digite sua mensagem..."
            />

            <button>Enviar</button>
          </div>

        </div>
      )}
    </>
  );
}

export default ChatFlutuante;