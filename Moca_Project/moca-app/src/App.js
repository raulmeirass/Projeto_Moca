import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
// Páginas do site
import Home from "./pages/Home";
import Login from "./pages/Login";
import Cadastro from "./pages/Cadastro";
import HomeDashboard from "./pages/Dashboards/Dashboard";
import Extrato from "./pages/Dashboards/Extrato";
import Receitas from "./pages/Dashboards/Receita";
import Despesas from "./pages/Dashboards/Despesa";
// CSS
import "./assets/css/style.css";
import "./assets/css/header.css";
import Cartoes from "./pages/Dashboards/Cartoes";
import Porquinho from "./pages/Dashboards/Porquinho";
import Calculadora from "./pages/Dashboards/Calculadora";
import Configuracoes from "./pages/Dashboards/Configuracoes";
import PorquinhoExtrato from "./pages/Dashboards/PorquinhoExtrato";
import NotFound from "./pages/Dashboards/NotFound";


function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home/>}/>
        <Route path="/login" element={<Login/>}/>
        <Route path="/cadastro" element={<Cadastro/>}/>
        <Route path="/dashboard" element={<HomeDashboard/>}/>
        <Route path="/dashboard/extrato" element={<Extrato/>}/>
        <Route path="/dashboard/receita" element={<Receitas />}/>
        <Route path="/dashboard/despesa" element={<Despesas/>}/>
        <Route path="/dashboard/cartoes" element={<Cartoes />}/>
        <Route path="/dashboard/porquinho" element={<Porquinho />}/>
        <Route path="/dashboard/porquinho/extrato/:idPorquinho" element={<PorquinhoExtrato />}/>
        <Route path="/dashboard/calculadora" element={<Calculadora />}/>
        <Route path="/dashboard/configuracoes" element={<Configuracoes />}/>
        <Route path="*" element={<NotFound />}/>
      </Routes>
    </Router>
  );
}

export default App;