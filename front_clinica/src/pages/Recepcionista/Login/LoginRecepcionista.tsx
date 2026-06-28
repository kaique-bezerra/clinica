import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react"; // Removido o FormEvent daqui
import logo from "../../../assets/heart-pulse-solid-full.svg";
import "./LoginRecepcionista.css"; 
import { EmailIcon, LockIcon, SecurityIcon } from "../../../components/Icones";

function Login() {
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [erro, setErro] = useState(""); 
  const [carregando, setCarregando] = useState(false);

  useEffect(() => {
    const savedEmail = localStorage.getItem("medsync_email");
    if (savedEmail) {
      setEmail(savedEmail);
    }
  }, []);

  // Mudamos o 'e' para 'e: any' se for TypeScript, ou apenas 'e' se for JavaScript.
  // Para garantir que funcione em ambos, deixamos como 'e: any'
  const handleLogin = async (e: any) => {
    e.preventDefault();
    setErro("");
    setCarregando(true);

    try {
      const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          email: email,
          senha: password, 
        }),
      });

      if (!response.ok) {
        throw new Error("E-mail ou senha inválidos.");
      }

      const data = await response.json(); 

      localStorage.setItem("token", data.token);
      localStorage.setItem("perfil", data.perfil);
      localStorage.setItem("idUsuario", data.idUsuario); 
      localStorage.setItem("nomeUsuario", data.nome);   
      localStorage.setItem("medsync_email", email); 

      switch (data.perfil) {
        case "RECEPCIONISTA":
          navigate("/dashboard");
          break;
        case "MEDICO":
          navigate("/dashboard-medico");
          break;
        case "ADMINISTRADOR":
          navigate("/dashboard-admin");
          break;
        case "PACIENTE":
          navigate("/dashboard-paciente");
          break;
        default:
          setErro("Perfil desconhecido. Entre em contato com o suporte.");
      }

    } catch (error) {
      console.error("Erro no login:", error);

      if(TypeError("Failed to fetch")){
        setErro("E-mail ou senha inválidos.");
      }

      if (error instanceof Error) {
        setErro(error.message);
      } else {
        setErro("Erro ao conectar com o servidor.");
      }
    } finally {
      setCarregando(false);
    }
  };

  return (
    <div className="login-container">
      <div className="login-card">
        <div className="login-header">
          <div className="logo">
            <img src={logo} alt="Logo MedSync" />
            <h1>MedSync</h1>
          </div>
          <p>Sistema Integrado da Clínica</p>
        </div>

        {erro && <p style={{ color: "red", textAlign: "center", marginBottom: "15px" }}>{erro}</p>}

        <form className="login-form" onSubmit={handleLogin}>
          <div className="input-group">
            <label className="field-label" htmlFor="email-login">
              <EmailIcon />
              <span>E-mail</span>
            </label>
            <input
              id="email-login"
              type="email"
              autoComplete="email"
              placeholder="Digite seu e-mail"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>

          <div className="input-group">
            <label className="field-label" htmlFor="password-login">
              <LockIcon />
              <span>Senha</span>
            </label>
            <input
              id="password-login"
              type="password"
              autoComplete="current-password"
              placeholder="Digite sua senha"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>

          <button
            type="submit"
            className="login-button"
            disabled={carregando}
          >
            {carregando ? "Autenticando..." : "Entrar"}
          </button>

          <div className="login-actions-panel">
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

export default Login;