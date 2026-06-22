import { BrowserRouter, Route, Routes, Navigate } from "react-router-dom";
import { RotaProtegida } from "./pages/segurity/RotaProtegidaProps";
// IMPORT DA SUA TELA DE LOGIN UNIFICADA
import LoginRecepcionista from "./pages/Recepcionista/Login/LoginRecepcionista"; 

// IMPORTS DA RECEPCIONISTA
import Dashboard from "./pages/Recepcionista/Dashboard/Dashboard";
import Pacientes from "./pages/Recepcionista/cadastroDePacientes/pacientes";
import Agendamento from "./pages/Recepcionista/Agendamento/Agendamento";
import Calendario from "./pages/Recepcionista/Calendário/Calendario";

// IMPORTS DO MÉDICO
import DashboardM from "./pages/pages_medico/Dashboard/DashboardM";
import CalendarioM from "./pages/pages_medico/Calendário/CalendarioM";
import ProntuarioM from "./pages/pages_medico/Prontuario/ProntuarioM";

// IMPORTS DO ADMINISTRADOR
import DashboardAdmin from "./pages/Administrador/dashboard/DashboardAdmin";
import CalendarioAdmin from "./pages/Administrador/Calendario/CalendarioAdmin";
import ProntuarioAdmin from "./pages/Administrador/prontuario/ProntuarioAdmin";
import AgendamentoDeConsultaAdmin from "./pages/Administrador/AgendamentoConsultas/AgendamentoConsultasAdmin";
import CadastroDePacientesAdmin from "./pages/Administrador/cadastroDePacientes/CadastroDePacientesAdmin";
import CadastroDeUsuarioAdmin from "./pages/Administrador/cadastroDeUsuarios/CadastroDeMedico";
import MedicosAdmin from "./pages/Administrador/gerenciamentodeMedicos.tsx/Medicos-admin";

// IMPORTS DO PACIENTE
import DashboardPaciente from "./pages/Paciente/Dashboard/DashboardPaciente";
import CalendarioPaciente from "./pages/Paciente/CalendarioDeConsultas/CalendarioPaciente";
import ExamesPaciente from "./pages/Paciente/exames/ExamesPaciente";
import PrescricoesPaciente from "./pages/Paciente/Prescricoes/PrescricoesPaciente";
import PerfilPaciente from "./pages/Paciente/perfil/PerfilPaciente";
import ConversasPaciente from "./pages/Paciente/Chat/ConversasPaciente";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* 🔓 ROTAS PÚBLICAS */}
        <Route path="/" element={<LoginRecepcionista />} />
        
        {/* Mantive as rotas antigas redirecionando para a raiz caso alguém tente entrar nelas */}
        <Route path="/perfil-medico" element={<Navigate to="/" replace />} />
        <Route path="/perfil-admin" element={<Navigate to="/" replace />} />
        <Route path="/login-paciente" element={<Navigate to="/" replace />} />


        {/* 🔐 ROTAS PROTEGIDAS: RECEPCIONISTA */}
        <Route element={<RotaProtegida perfisPermitidos={["RECEPCIONISTA"]} />}>
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/pacientes" element={<Pacientes />} />
          <Route path="/agendamento" element={<Agendamento />} />
          <Route path="/calendario" element={<Calendario />} />
        </Route>


        {/* 🔐 ROTAS PROTEGIDAS: MÉDICO */}
        <Route element={<RotaProtegida perfisPermitidos={["MEDICO"]} />}>
          <Route path="/dashboard-medico" element={<DashboardM />} />
          <Route path="/calendario-medico" element={<CalendarioM />} />
          <Route path="/prontuario-medico" element={<ProntuarioM />} />
        </Route>


        {/* 🔐 ROTAS PROTEGIDAS: ADMINISTRADOR */}
        <Route element={<RotaProtegida perfisPermitidos={["ADMINISTRADOR"]} />}>
          <Route path="/dashboard-admin" element={<DashboardAdmin/>}/>
          <Route path="/calendario-admin" element={<CalendarioAdmin/>}/>
          <Route path="/prontuario-admin" element={<ProntuarioAdmin/>} />
          <Route path="/agendamento-admin" element={<AgendamentoDeConsultaAdmin/>} />
          <Route path="/cadastroDePacientes-admin" element={<CadastroDePacientesAdmin/>} />
          <Route path="/cadastroDeUsuarios-admin" element={<CadastroDeUsuarioAdmin/>} />
          <Route path="/cadastroDeMedicos-admin" element={<MedicosAdmin/>} />
        </Route>


        {/* 🔐 ROTAS PROTEGIDAS: PACIENTE */}
        <Route element={<RotaProtegida perfisPermitidos={["PACIENTE"]} />}>
          <Route path="/dashboard-paciente" element={<DashboardPaciente />} />
          <Route path="/calendario-paciente" element={<CalendarioPaciente />} />
          <Route path="/exames-paciente" element={<ExamesPaciente />} />
          <Route path="/prescricoes-paciente" element={<PrescricoesPaciente />} />
          <Route path="/perfil-paciente" element={<PerfilPaciente />} />
          <Route path="/conversas-paciente" element={<ConversasPaciente />} />
        </Route>


        {/* 🔄 REDIRECIONA QUALQUER ROTA INVÁLIDA PARA O LOGIN */}
        <Route path="*" element={<Navigate to="/" replace />} />

      </Routes>
    </BrowserRouter>
  );
}

export default App;