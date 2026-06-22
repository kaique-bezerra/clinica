import { Navigate, Outlet } from "react-router-dom";

interface RotaProtegidaProps {
  perfisPermitidos: string[];
}

export function RotaProtegida({ perfisPermitidos }: RotaProtegidaProps) {
  const token = localStorage.getItem("token");
  const perfilUsuario = localStorage.getItem("perfil"); // Pega o perfil salvo no login (ex: "MEDICO")

  // 1. Se não estiver logado (não tem token), manda de volta para a tela de login
  if (!token) {
    return <Navigate to="/login" replace />;
  }

  // 2. Se o perfil do usuário não estiver na lista de perfis autorizados para essa rota
  if (perfilUsuario && !perfisPermitidos.includes(perfilUsuario)) {
    // Redireciona ele para o dashboard correto dele para ele não ficar preso
    switch (perfilUsuario) {
      case "RECEPCIONISTA": return <Navigate to="/dashboard" replace />;
      case "MEDICO": return <Navigate to="/dashboard-medico" replace />;
      case "ADMINISTRADOR": return <Navigate to="/dashboard-admin" replace />;
      case "PACIENTE": return <Navigate to="/dashboard-paciente" replace />;
      default: return <Navigate to="/login" replace />;
    }
  }

  // 3. Se estiver tudo certo, renderiza a página que ele tentou acessar
  return <Outlet />;
}