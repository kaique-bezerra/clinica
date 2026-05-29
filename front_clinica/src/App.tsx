import { BrowserRouter, Route, Routes } from "react-router-dom";
import LoginPaciente from "./pages/Paciente/login/LoginPaciente";
import Agendamento from "./pages/Recepcionista/Agendamento/Agendamento";
import Calendario from "./pages/Recepcionista/Calendário/Calendario";
import Dashboard from "./pages/Recepcionista/Dashboard/Dashboard";
import LoginRecepcionista from "./pages/Recepcionista/Login/LoginRecepcionista";
import Pacientes from "./pages/Recepcionista/cadastroDePacientes/pacientes";
import CalendarioM from "./pages/pages_medico/Calendário/CalendarioM";
import DashboardM from "./pages/pages_medico/Dashboard/DashboardM";
import LoginMedico from "./pages/pages_medico/Login/LoginMedico";
import ProntuarioM from "./pages/pages_medico/Prontuario/ProntuarioM";
import LoginAdmin from "./pages/Administrador/login/LoginAdmin";
import DashboardAdmin from "./pages/Administrador/dashboard/DashboardAdmin";
import CalendarioAdmin from "./pages/Administrador/Calendario/CalendarioAdmin";
import ProntuarioAdmin from "./pages/Administrador/prontuario/ProntuarioAdmin";
import AgendamentoDeConsultaAdmin from "./pages/Administrador/AgendamentoConsultas/AgendamentoConsultasAdmin";
import CadastroDePacientesAdmin from "./pages/Administrador/cadastroDePacientes/CadastroDePacientesAdmin";
import CadastroDeUsuarioAdmin from "./pages/Administrador/cadastroDeUsuarios/CadastroDeUsuarioAdmin";
import MedicosAdmin from "./pages/Administrador/gerenciamentodeMedicos.tsx/Medicos-admin";
import DashboardPaciente from "./pages/Paciente/Dashboard/DashboardPaciente";
import CalendarioPaciente from "./pages/Paciente/CalendarioDeConsultas/CalendarioPaciente";
import ExamesPaciente from "./pages/Paciente/exames/ExamesPaciente";
import PrescricoesPaciente from "./pages/Paciente/Prescricoes/PrescricoesPaciente";

function App() {
  return (
    <BrowserRouter>

      <Routes>

        <Route path="/" element={<LoginRecepcionista />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/pacientes" element={<Pacientes />} />
        <Route path="/agendamento" element={<Agendamento />} />
        <Route path="/calendario" element={<Calendario />} />
        <Route path="/perfil-medico" element={<LoginMedico />} />
        <Route path="/dashboard-medico" element={<DashboardM />} />
        <Route path="/calendario-medico" element={<CalendarioM />} />
        <Route path="/prontuario-medico" element={<ProntuarioM />} />
        <Route path="/perfil-admin" element={<LoginAdmin />} />
        <Route path="/dashboard-admin" element={<DashboardAdmin/>}/>
        <Route path="/calendario-admin" element={<CalendarioAdmin/>}/>
        <Route path="/prontuario-admin" element={<ProntuarioAdmin/>} />
        <Route path="/agendamento-admin" element={<AgendamentoDeConsultaAdmin/>} />
        <Route path="/cadastroDePacientes-admin" element={<CadastroDePacientesAdmin/>} />
        <Route path="/cadastroDeUsuarios-admin" element={<CadastroDeUsuarioAdmin/>} />
        <Route path="/cadastroDeMedicos-admin" element={<MedicosAdmin/>} />
        <Route path="/perfil-paciente" element={<LoginPaciente />} />
        <Route path="/dashboard-paciente" element={<DashboardPaciente />} />
        <Route path="/calendario-paciente" element={<CalendarioPaciente />} />
        <Route path="/exames-paciente" element={<ExamesPaciente />} />
        <Route path="/prescricoes-paciente" element={<PrescricoesPaciente />} />

      </Routes>

    </BrowserRouter>
  );
}

export default App;
