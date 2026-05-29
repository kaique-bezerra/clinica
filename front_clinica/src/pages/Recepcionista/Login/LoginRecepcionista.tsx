import { Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import logo from "../../../assets/heart-pulse-solid-full.svg";
import "./LoginRecepcionista.css";
import { AdminIcon, EmailIcon, LockIcon, SecurityIcon, UserDoctorIcon } from "../../../components/Icones";


function LoginRecepcionista() {
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  useEffect(() => {
    const savedEmail = localStorage.getItem("recepcionista_email");
    const savedPassword = localStorage.getItem("recepcionista_password");

    if (savedEmail) {
      setEmail(savedEmail);
    }

    if (savedPassword) {
      setPassword(savedPassword);
    }
  }, []);

  useEffect(() => {
    localStorage.setItem("recepcionista_email", email);
  }, [email]);

  useEffect(() => {
    localStorage.setItem("recepcionista_password", password);
  }, [password]);

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

        <p className="perfil"><UserDoctorIcon/> PERFIL RECEPCIONISTA</p>

        <form className="login-form">
          <div className="input-group">
            <label className="field-label" htmlFor="email-recepcionista">
              <EmailIcon />
              <span>E-mail</span>
            </label>

            <input
              id="email-recepcionista"
              type="email"
              name="email"
              autoComplete="email"
              placeholder="Digite seu e-mail"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>

          <div className="input-group">
            <label className="field-label" htmlFor="password-recepcionista">
              <LockIcon />
              <span>Senha</span>
            </label>

            <input
              id="password-recepcionista"
              type="password"
              name="password"
              autoComplete="current-password"
              placeholder="Digite sua senha"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>

          <button
            type="button"
            className="login-button"
            onClick={() => navigate("/dashboard")}
          >
            Entrar
          </button>

          <div className="login-divider" aria-hidden="true">
            <span>ou</span>
          </div>

          <div className="login-actions-panel">
            <Link className="secondary-login-button" to="/perfil-medico">
              <UserDoctorIcon />
              <span>Acessar Perfil Médico</span>
            </Link>

            <Link className="secondary-login-button" to="/perfil-paciente">
              <UserDoctorIcon />
              <span>Acessar Perfil Paciente</span>
            </Link>

            <button
              className="admin-login-button"
              type="button"
              onClick={() => navigate("/perfil-admin")}
            >
              <AdminIcon />
              <span>Acessar Perfil Admin</span>
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

export default LoginRecepcionista;