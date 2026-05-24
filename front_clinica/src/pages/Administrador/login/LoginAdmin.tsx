import { Link, useNavigate } from "react-router-dom";
import logo from "../../../assets/heart-pulse-solid-full.svg";
import "../../Recepcionista/Login/LoginRecepcionista.css";

function EmailIcon() {
  return (
    <svg
      className="field-label-icon"
      aria-hidden="true"
      focusable="false"
      viewBox="0 0 24 24">
      <path d="M4.5 6.75A2.25 2.25 0 0 1 6.75 4.5h10.5a2.25 2.25 0 0 1 2.25 2.25v10.5a2.25 2.25 0 0 1-2.25 2.25H6.75a2.25 2.25 0 0 1-2.25-2.25V6.75Zm2.25-.75a.75.75 0 0 0-.75.75v.43l6 4.2 6-4.2v-.43a.75.75 0 0 0-.75-.75H6.75Zm11.25 2.98-5.57 3.9a.75.75 0 0 1-.86 0L6 8.98v8.27c0 .41.34.75.75.75h10.5c.41 0 .75-.34.75-.75V8.98Z" />
    </svg>
  );
}

function LockIcon() {
  return (
    <svg
      className="field-label-icon"
      aria-hidden="true"
      focusable="false"
      viewBox="0 0 24 24">
      <path d="M7.5 10.5V8.25a4.5 4.5 0 0 1 9 0v2.25h.75A2.25 2.25 0 0 1 19.5 12.75v5.5a2.25 2.25 0 0 1-2.25 2.25H6.75a2.25 2.25 0 0 1-2.25-2.25v-5.5a2.25 2.25 0 0 1 2.25-2.25h.75Zm1.5 0h6V8.25a3 3 0 0 0-6 0v2.25Zm-2.25 1.5a.75.75 0 0 0-.75.75v5.5c0 .41.34.75.75.75h10.5c.41 0 .75-.34.75-.75v-5.5a.75.75 0 0 0-.75-.75H6.75Z" />
    </svg>
  );
}

function UserDoctorIcon() {
  return (
    <svg
      className="secondary-button-icon"
      aria-hidden="true"
      focusable="false"
      viewBox="0 0 24 24">
      <path d="M12 12.75a4.5 4.5 0 1 1 0-9 4.5 4.5 0 0 1 0 9Zm0-1.5a3 3 0 1 0 0-6 3 3 0 0 0 0 6Zm-7.5 8.5A5.25 5.25 0 0 1 9.75 14.5h4.5a5.25 5.25 0 0 1 5.25 5.25.75.75 0 0 1-1.5 0A3.75 3.75 0 0 0 14.25 16h-4.5A3.75 3.75 0 0 0 6 19.75a.75.75 0 0 1-1.5 0Zm7.5-2.5c.41 0 .75.34.75.75v.75h.75a.75.75 0 0 1 0 1.5h-.75V21a.75.75 0 0 1-1.5 0v-.75h-.75a.75.75 0 0 1 0-1.5h.75V18c0-.41.34-.75.75-.75Z" />
    </svg>
  );
}

function AdminIcon() {
  return (
    <svg
      className="admin-button-icon"
      aria-hidden="true"
      focusable="false"
      viewBox="0 0 24 24">
      <path d="M12 21.5a.76.76 0 0 1-.29-.06C7.07 19.51 4.5 15.61 4.5 10.46V6.75c0-.32.2-.61.51-.71l6.75-2.25c.16-.05.32-.05.48 0l6.75 2.25c.31.1.51.39.51.71v3.71c0 5.15-2.57 9.05-7.21 10.98a.76.76 0 0 1-.29.06ZM6 7.29v3.17c0 4.41 2.13 7.69 6 9.47 3.87-1.78 6-5.06 6-9.47V7.29l-6-2-6 2Zm6 8.21a.75.75 0 0 1-.75-.75v-1.5h-1.5a.75.75 0 0 1 0-1.5h1.5v-1.5a.75.75 0 0 1 1.5 0v1.5h1.5a.75.75 0 0 1 0 1.5h-1.5v1.5a.75.75 0 0 1-.75.75Z" />
    </svg>
  );
}

function SecurityIcon() {
  return (
    <svg
      className="security-icon"
      aria-hidden="true"
      focusable="false"
      viewBox="0 0 24 24">
      <path d="M12 21.5a.76.76 0 0 1-.29-.06C7.07 19.51 4.5 15.61 4.5 10.46V6.75c0-.32.2-.61.51-.71l6.75-2.25c.16-.05.32-.05.48 0l6.75 2.25c.31.1.51.39.51.71v3.71c0 5.15-2.57 9.05-7.21 10.98a.76.76 0 0 1-.29.06ZM6 7.29v3.17c0 4.41 2.13 7.69 6 9.47 3.87-1.78 6-5.06 6-9.47V7.29l-6-2-6 2Zm3.97 4.15a.75.75 0 0 1 1.06 0l.97.97 2.97-2.97a.75.75 0 1 1 1.06 1.06l-3.5 3.5a.75.75 0 0 1-1.06 0l-1.5-1.5a.75.75 0 0 1 0-1.06Z" />
    </svg>
  );
}

function LoginAdmin() {
  const navigate = useNavigate();

  return (
    <div className="login-container">
      <div className="login-card">
        <div className="login-header">
          <div className="logo">
            <img src={logo} alt="" />
            <h1>MedSync</h1>
          </div>
          <p>Sistema Administrativo da Clínica</p>
        </div>

        <p className="perfil">PERFIL ADMINISTRADOR</p>

        <form className="login-form">
          <div className="input-group">
            <label className="field-label" htmlFor="email-admin">
              <EmailIcon />
              <span>E-mail</span>
            </label>

            <input
              id="email-admin"
              type="email"
              name="email"
              autoComplete="email"
              placeholder="Digite seu e-mail"
            />
          </div>

          <div className="input-group">
            <label className="field-label" htmlFor="password-admin">
              <LockIcon />
              <span>Senha</span>
            </label>

            <input
              id="password-admin"
              type="password"
              name="password"
              autoComplete="current-password"
              placeholder="Digite sua senha"
            />
          </div>

          <button
            type="button"
            className="login-button"
            onClick={() => navigate("/dashboard-admin")}
          >
            Entrar
          </button>

          <div className="login-divider" aria-hidden="true">
            <span>ou</span>
          </div>

          <div className="login-actions-panel">
            <Link className="secondary-login-button" to="/">
              <UserDoctorIcon />
              <span>Acessar Perfil Recepcionista</span>
            </Link>

            <button className="secondary-login-button" type="button" onClick={()=>navigate("/perfil-medico")}>
              <UserDoctorIcon />
              <span>Acessar Perfil Médico</span>
            </button>

            <p className="security-message">
              <SecurityIcon />
              <span>Seus dados estão protegidos e seguros</span>
            </p>
          </div>
        </form>
      </div>
    </div>
  );
}

export default LoginAdmin;
