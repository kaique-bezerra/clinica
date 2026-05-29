
import { Link } from "react-router-dom";

const MenuLateral=({ menuAberto, setMenuAberto }: {menuAberto: boolean, setMenuAberto: any})=>{

    return(
        <div>
            <button className="menu-button" onClick={() => setMenuAberto(!menuAberto)}>
                ☰ Menu
            </button>

        <aside className={`sidebar ${menuAberto ? "open" : "closed"}`}>

        <div className="logo">
          <h2>MedSync</h2>
        </div>

        <nav className="menu">

          <ul>
            <Link className="link" to="/dashboard-medico">
            <li>Dashboard</li>
            </Link>
                
            <Link className="link" to="/calendario-medico">
            <li>Calendário</li>
            </Link>

            <Link className="link" to="/prontuario-medico">
             <li>Prontuário</li>
            </Link>

            <Link className="link" to="/perfil-medico">
             <li>Sair</li>    
            </Link>
          </ul>

        </nav>
      </aside>
      </div>
    );
}

export default MenuLateral;