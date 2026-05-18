import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import Pacientes from "./pages/pacientes";

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

      </Routes>

    </BrowserRouter>
  );
}

export default App;