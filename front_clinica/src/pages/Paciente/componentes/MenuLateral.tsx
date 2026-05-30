import { Link } from "react-router-dom";

const MenuLateral= ({menuAberto, setMenuAberto}:{menuAberto: boolean, setMenuAberto:any})=>{

    return(
    <div>
        <button className="menu-button" onClick={() => setMenuAberto(!menuAberto)}>
        ☰ Menu
      </button>

      <aside
        className={`sidebar ${
          menuAberto ? "open" : "closed"
        }`}>

        <div className="logo">
          <h2>MedSync</h2>
        </div>

        <nav className="menu">

          <ul>

            <Link className="link" to="/perfil-paciente"><li>
              Perfil
            </li></Link>

            <Link className="link" to="/dashboard-paciente"><li>
              Dashboard
            </li></Link>
            
            <Link className="link" to="/calendario-paciente"><li>
              Calendário de Consultas
            </li></Link>

            <Link className="link" to="/exames-paciente"><li>
              Exames
            </li></Link>

            <Link className="link" to="/prescricoes-paciente"><li>
                Prescrição
            </li></Link>

            <Link className="link" to="/conversas-paciente"><li>
                Chat
            </li></Link>

            <Link className="link" to="/login-paciente"><li>
              Sair
            </li></Link>

          </ul>

        </nav>
      </aside>
    </div>
    );
}

export default MenuLateral;