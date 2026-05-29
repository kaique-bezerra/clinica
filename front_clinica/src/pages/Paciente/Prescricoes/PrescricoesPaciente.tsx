import { useState } from "react";
import MenuLateral from "../componentes/MenuLateral";
import "./PrescricoesPaciente.css";

function PrescricoesPaciente() {

  const [menuAberto, setMenuAberto] = useState(false);

  const prescricoes = [
    {
      titulo: "Receita Médica",
      descricao: "Uso de antibiótico por 7 dias",
      medico: "Dr. João",
      data: "10/06/2026",
      status: "Disponível",
    },

    {
      titulo: "Pedido de Exame",
      descricao: "Solicitação de hemograma completo",
      medico: "Dra. Ana",
      data: "08/06/2026",
      status: "Disponível",
    },

    {
      titulo: "Atestado Médico",
      descricao: "Atestado de 3 dias",
      medico: "Dr. Pedro",
      data: "05/06/2026",
      status: "Disponível",
    },

    {
      titulo: "Recomendação Médica",
      descricao: "Prática de atividade física leve",
      medico: "Dra. Camila",
      data: "01/06/2026",
      status: "Disponível",
    },
  ];

  return (
    <div className="prescricoes-container">

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

          <h1>Prescrições e Documentos</h1>

          <p>
            Acesse receitas, pedidos de exames,
            atestados e recomendações médicas.
          </p>

        </section>

        <section className="prescricoes-grid">

          {prescricoes.map((item, index) => (
            <div key={index} className="prescricao-card">

              <div className="card-top">

                <h2>{item.titulo}</h2>

                <span className="status">
                  {item.status}
                </span>

              </div>

              <p className="descricao">
                {item.descricao}
              </p>

              <div className="info">
                <p>
                  <strong>Médico:</strong>{" "}
                  {item.medico}
                </p>

                <p>
                  <strong>Data:</strong>{" "}
                  {item.data}
                </p>
              </div>

              <button className="download-btn">
                Baixar Documento
              </button>

            </div>
          ))}

        </section>

      </main>

    </div>
  );
}

export default PrescricoesPaciente;