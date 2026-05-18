import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import Pacientes from "./pages/pacientes";
import Agendamento from "./pages/Agendamento";
import Prontuario from "./pages/Prontuario";
import Calendario from "./pages/Calendario";

function App() {
  return (
    <BrowserRouter>

      <Routes>

        {/* Página Login */}
        <Route path="/" element={<Login />} />

        {/* Página Dashboard */}
        <Route path="/dashboard" element={<Dashboard />} />

        {/* Página Cadastro de Pacientes */}
        <Route path="/pacientes" element={<Pacientes />} />

        {/* Página Agendamento */}
        <Route path="/agendamento" element={<Agendamento />} />

        {/* Página Prontuário */}
        <Route path="/prontuario" element={<Prontuario />} />

        {/* Página Calendário */}
        <Route path="/calendario" element={<Calendario />} />
        

      </Routes>

    </BrowserRouter>
  );
}

export default App;