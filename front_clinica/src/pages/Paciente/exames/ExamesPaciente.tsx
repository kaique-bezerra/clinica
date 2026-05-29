import { useState } from "react";
import MenuLateral from "../componentes/MenuLateral";
import "./ExamesPaciente.css";

function ExamesPaciente() {

  const [menuAberto, setMenuAberto] = useState(false);

  const exames = [
    {
      nome: "Hemograma Completo",
      tipo: "PDF",
      data: "02/06/2026",
      status: "Enviado",
    },

    {
      nome: "Raio-X Torácico",
      tipo: "Imagem",
      data: "20/05/2026",
      status: "Analisado",
    },

    {
      nome: "Ressonância Magnética",
      tipo: "Documento",
      data: "11/05/2026",
      status: "Enviado",
    },
  ];

  return (
    <div className="exames-container">

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

          <h1>Exames</h1>

          <p>
            Faça upload dos seus exames e acompanhe
            o histórico de arquivos enviados.
          </p>

        </section>

        <section className="upload-section">

          <h2>Adicionar Novo Exame</h2>

          <div className="upload-box">

            <p>
              Arraste arquivos aqui ou selecione do
              dispositivo
            </p>

            <input
              type="file"
              multiple
              accept=".pdf,.jpg,.jpeg,.png,.doc,.docx"
            />

            <small>
              Formatos permitidos: PDF, imagem e
              documentos
            </small>

          </div>

        </section>

        <section className="historico-section">

          <h2>Histórico de Uploads</h2>

          <div className="exames-grid">

            {exames.map((exame, index) => (
              <div key={index} className="exame-card">

                <h3>{exame.nome}</h3>

                <p>
                  <strong>Tipo:</strong>{" "}
                  {exame.tipo}
                </p>

                <p>
                  <strong>Data:</strong>{" "}
                  {exame.data}
                </p>

                <span
                  className={`status ${
                    exame.status.toLowerCase()
                  }`}
                >
                  {exame.status}
                </span>

              </div>
            ))}

          </div>

        </section>

      </main>

    </div>
  );
}

export default ExamesPaciente;