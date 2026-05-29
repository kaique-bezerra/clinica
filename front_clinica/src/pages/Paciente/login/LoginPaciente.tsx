import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import logo from "../../../assets/heart-pulse-solid-full.svg";
import { AdminIcon, EmailIcon, LockIcon, SecurityIcon, UserDoctorIcon } from "../../../components/Icones";
import "../../Recepcionista/Login/LoginRecepcionista.css";


function LoginPaciente(){

  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  useEffect(() => {
    const savedEmail = localStorage.getItem("medico_email");
    const savedPassword = localStorage.getItem("medico_password");

    if (savedEmail) {
      setEmail(savedEmail);
    }

    if (savedPassword) {
      setPassword(savedPassword);
    }
  }, []);

  useEffect(() => {
    localStorage.setItem("medico_email", email);
  }, [email]);

  useEffect(() => {
    localStorage.setItem("medico_password", password);
  }, [password]);
    return(
        
    <div className="login-container">
      <div className="login-card">
        <div className="login-header">
          <div className="logo">
            <img src={logo} alt="" />
            <h1>MedSync</h1>
          </div>

          <p>Sistema Administrativo da Clínica</p>
        </div>

        <p className="perfil"><UserDoctorIcon/> PERFIL PACIENTE</p>

        <form className="login-form">
          <div className="input-group">
            <label className="field-label" htmlFor="email-medico">
              <EmailIcon />
              <span>E-mail</span>
            </label>

            <input
              id="email-medico"
              type="email"
              name="email"
              autoComplete="email"
              placeholder="Digite seu e-mail"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>

          <div className="input-group">
            <label className="field-label" htmlFor="password-medico">
              <LockIcon />
              <span>Senha</span>
            </label>

            <input
              id="password-medico"
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
            onClick={() => navigate("/dashboard-paciente")}
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

            <Link className="secondary-login-button" to="/perfil-paciente">
              <UserDoctorIcon/>
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

export default LoginPaciente;