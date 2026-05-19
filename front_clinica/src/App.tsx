import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import Pacientes from "./pages/pacientes";
import Agendamento from "./pages/Agendamento";
import Prontuario from "./pages/Prontuario";
import Calendario from "./pages/Calendario";
import LoginM from "./pages/pages_medico/LoginM";
import PacientesM from "./pages/pages_medico/pacientesM";
import CalendarioM from "./pages/pages_medico/CalendarioM";
import ProntuarioM from "./pages/pages_medico/ProntuarioM";
import DashboardM from "./pages/pages_medico/DashboardM";
function App() {
  return (
    <BrowserRouter>

      <Routes>

        <Route path="/" element={<Login />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/pacientes" element={<Pacientes />} />
        <Route path="/agendamento" element={<Agendamento />} />
        <Route path="/prontuario" element={<Prontuario />} />
        <Route path="/calendario" element={<Calendario />} />
        <Route path="/perfil-medico" element={<LoginM />} />
        <Route path="/dashboard-medico" element={<DashboardM />} />
        <Route path="/pacientes-medico" element={<PacientesM />} />
        <Route path="/calendario-medico" element={<CalendarioM />} />
        <Route path="/prontuario-medico" element={<ProntuarioM />} />
      </Routes>

    </BrowserRouter>
  );
}

export default App;