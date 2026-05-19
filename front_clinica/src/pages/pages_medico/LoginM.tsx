import "./Login.css";
import { useNavigate } from "react-router-dom";
import {Link} from "react-router-dom";

function LoginM() {

  const navigate = useNavigate();

  return (
    <div className="login-container">

      <div className="login-card">

        <div className="login-header">
          <h1>MedSync</h1>
          <p>Sistema Administrativo da Clínica</p>

        </div>
          <p className="perfil">PERFIL MÉDICO</p>

        <form className="login-form">

          <div className="input-group">
            <label>E-mail</label>

            <input
              type="email"
              placeholder="Digite seu e-mail"
            />
          </div>

          <div className="input-group">
            <label>Senha</label>

            <input
              type="password"
              placeholder="Digite sua senha"
            />
          </div>

          <button
            type="button"
            className="login-button"
            onClick={() => navigate("/dashboard-medico")}
          >
            Entrar
          </button>
          <Link to="/">
            <p className="linkp">Acessar Perfil Atendente</p>
          </Link>

        </form>

      </div>

    </div>
  );
}

export default LoginM;