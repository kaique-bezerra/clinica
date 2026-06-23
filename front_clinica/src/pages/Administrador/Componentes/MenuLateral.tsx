import { Link } from "react-router-dom";

const MenuLateral=({ menuAberto, setMenuAberto}:{menuAberto: boolean, setMenuAberto: any})=>{

    return(
        <div>
        <button className="menu-button" onClick={() => setMenuAberto(!menuAberto)}>
        ☰ Menu
      </button>

      <aside
        className={`sidebar ${
          menuAberto ? "open" : "closed"
        }`}
      >

        <div className="logo">
          <h2>MedSync</h2>
        </div>

        <nav className="menu">

          <ul>

            <Link className="link" to="/dashboard-admin"><li>
              Dashboard
            </li></Link>
            
            <Link className="link" to="/calendario-admin"><li>
              Calendário
            </li></Link>

            <Link className="link" to="/prontuario-admin"><li>
              prontuário
            </li></Link>

            <Link className="link" to="/agendamento-admin"><li>
                Agendamento de Consultas
            </li></Link>

            <Link className="link" to="/cadastroDePacientes-admin"><li>
                Cadastro de pacientes
            </li></Link>

            <Link className="link" to="/cadastroDeUsuarios-admin"><li>
                Cadastro de medico
            </li></Link>
            <Link className="link" to="/cadastroDeRecepcionista-admin"><li>
                Cadastro de Recepcionista
            </li></Link>
            
            <Link className="link" to="/cadastroDeMedicos-admin"><li>
                Gerenciamento de Médicos
            </li></Link>

            <Link className="link" to="/cadastroDeAgendaPadrao-admin"><li>
                Cadastro de Agenda Padrão
            </li></Link>

            <Link className="link" to="/horario-bloqueado"><li>
                Cadastro de Horário Bloqueado
            </li></Link>


            <Link className="link" to="/">
            <li>
              Sair
              </li>    
            </Link>

          </ul>

        </nav>

      </aside>
        </div>
);
}

export default MenuLateral;

