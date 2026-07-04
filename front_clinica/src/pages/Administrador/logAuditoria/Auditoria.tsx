import { useEffect, useState } from "react";
import MenuLateral from "../Componentes/MenuLateral";
import "./Auditoria.css";

interface LogAuditoria {
  idLogAuditoria: number;
  dataHora: string;
  emailUsuario: string;
  perfilUsuario: string;
  acao: string;
  entidadeAfetada: string;
  idAfetado: number;
  descricao: string;
}

function Auditoria() {
  const [menuAberto, setMenuAberto] = useState(false);

  const [mensagem, setMensagem] = useState("");

  const [logs, setLogs] = useState<LogAuditoria[]>([]);

  const [paginaAtual, setPaginaAtual] = useState(0);

  const [totalPaginas, setTotalPaginas] = useState(0);

  const [loading, setLoading] = useState(false);

  const token = localStorage.getItem("token");

  async function carregarLogs(pagina: number) {
    try {
      setLoading(true);

      const response = await fetch(
        `http://localhost:8080/auditoria?page=${pagina}&size=20`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      );

      if (!response.ok) {
        if (response.status === 401) {
          setMensagem("Sessão expirada.");

          localStorage.removeItem("token");

          window.location.href = "/";

          return;
        } else if (response.status === 403) {
          setMensagem("Você não possui permissão.");

          return;
        }
        throw new Error("Erro ao carregar logs.");
      }

      const dados = await response.json();

      setLogs(dados.content);
      setPaginaAtual(dados.number);
      setTotalPaginas(dados.totalPages);
    } catch (erro) {
      console.error(erro);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    carregarLogs(0);
  }, []);

  function paginaAnterior() {
    if (paginaAtual > 0) {
      carregarLogs(paginaAtual - 1);
    }
  }

  function proximaPagina() {
    if (paginaAtual < totalPaginas - 1) {
      carregarLogs(paginaAtual + 1);
    }
  }

  return (
    <div className="pagina">
      <MenuLateral menuAberto={menuAberto} setMenuAberto={setMenuAberto} />

      <main className={`main-content ${menuAberto ? "expanded" : ""}`}>
        <h1>Logs de Auditoria</h1>

        {loading ? (
          <p>Carregando...</p>
        ) : (
          <table className="tabela-auditoria">
            <thead>
              <tr>
                <th>ID</th>
                <th>Data/Hora</th>
                <th>Email</th>
                <th>Perfil</th>
                <th>Ação</th>
                <th>Entidade</th>
                <th>ID Afetado</th>
                <th>Descrição</th>
              </tr>
            </thead>

            <tbody>
              {logs.map((log) => (
                <tr key={log.idLogAuditoria}>
                  <td>{log.idLogAuditoria}</td>

                  <td>{new Date(log.dataHora).toLocaleString("pt-BR")}</td>

                  <td>{log.emailUsuario}</td>

                  <td>{log.perfilUsuario}</td>

                  <td>{log.acao}</td>

                  <td>{log.entidadeAfetada}</td>

                  <td>{log.idAfetado}</td>

                  <td>{log.descricao}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}

        <div className="paginacao">
          <button onClick={paginaAnterior} disabled={paginaAtual === 0}>
            Anterior
          </button>

          <span>
            Página {paginaAtual + 1} de {totalPaginas}
          </span>

          <button
            onClick={proximaPagina}
            disabled={paginaAtual + 1 === totalPaginas}
          >
            Próxima
          </button>
          {mensagem && (
            <p style={{ marginTop: "10px", color: "blue" }}>{mensagem}</p>
          )}
        </div>
      </main>
    </div>
  );
}

export default Auditoria;
