import "./Login.css";
import { useNavigate } from "react-router-dom";

function Login() {

  const navigate = useNavigate();

  return (
    <div className="login-container">

      {/* CARD LOGIN */}
      <div className="login-card">

        {/* TÍTULO */}
        <div className="login-header">
          <h1>MedSync</h1>
          <p>Sistema Administrativo da Clínica</p>
        </div>

        {/* FORMULÁRIO */}
        <form className="login-form">

          {/* CAMPO EMAIL */}
          <div className="input-group">
            <label>E-mail</label>

            <input
              type="email"
              placeholder="Digite seu e-mail"
            />
          </div>

          {/* CAMPO SENHA */}
          <div className="input-group">
            <label>Senha</label>

            <input
              type="password"
              placeholder="Digite sua senha"
            />
          </div>

          {/* BOTÃO */}
          <button
            type="button"
            className="login-button"
            onClick={() => navigate("/dashboard")}
          >
            Entrar
          </button>

        </form>

      </div>

    </div>
  );
}

export default Login;