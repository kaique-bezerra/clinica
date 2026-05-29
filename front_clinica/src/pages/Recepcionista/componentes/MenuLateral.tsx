import { Link } from "react-router-dom";

const MenuLateral=({menuAberto, setMenuAberto}:{menuAberto:boolean, setMenuAberto:any})=>{

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

                    <Link className="link" to="/dashboard"><li>
                    Dashboard
                    </li></Link>

                    <Link className="link" to="/pacientes"> <li>
                        cadastro de pacientes
                        </li></Link>

                    <Link className="link" to="/agendamento"><li>
                        Agendamento
                    </li></Link>

                    <Link className="link" to="/calendario"><li>
                        Calendário
                    </li></Link>

                    <Link className="link" to="/"><li>
                    Sair
                    </li></Link>
                </ul>
                </nav>
            </aside>
        </div>
    );
}

export default MenuLateral;