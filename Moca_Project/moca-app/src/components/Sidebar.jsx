import logo from "../assets/img/logoMoca.png";
import "../assets/css/style2.css";
import { useState } from "react";

function Sidebar() {

    const [sidebarClosed, setSidebarClosed] = useState(true);

    const toggleSidebar = () => {
        setSidebarClosed(!sidebarClosed);
    };

    const handleSearchClick = () => {
        setSidebarClosed(false);
    };

    return (
        <nav className={`sidebar ${sidebarClosed ? 'close' : ''}`}>
            <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'></link>
            <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,700,0,0" />
            <header className="headerside">
                <div className="text">
                    <h2><img src={logo} alt="" /></h2>
                    <span className="name">
                        MOCA
                    </span>
                </div>
                <div className="navteste" onClick={toggleSidebar}><i className="bx bx-chevron-right toggle"></i></div>
            </header>
            <div className="menu-bar">
                <div className="menu">
                    <ul className="menu-links">
                        <li className={`nav-link ${window.location.pathname === '/dashboard' ? 'active' : ''}`}>
                            <a href="/dashboard">
                                <span className="material-symbols-outlined">dashboard</span>
                                <span className="text nav-text">Dashboard</span>
                            </a>
                        </li>
                        <li className={`nav-link ${window.location.pathname === '/dashboard/extrato' ? 'active' : ''}`}>
                            <a href="/dashboard/extrato">
                                <span className="material-symbols-outlined">description</span>
                                <span className="text nav-text">Extrato</span>
                            </a>
                        </li>
                        <li className={`nav-link ${window.location.pathname === '/dashboard/receita' ? 'active' : ''}`}>
                            <a href="/dashboard/receita">
                                <span className="material-symbols-outlined">arrow_upward</span>
                                <span className="text nav-text">Receita</span>
                            </a>
                        </li>
                        <li className={`nav-link ${window.location.pathname === '/dashboard/despesa' ? 'active' : ''}`}>
                            <a href="/dashboard/despesa">
                                <span className="material-symbols-outlined">arrow_downward</span>
                                <span className="text nav-text">Despesa</span>
                            </a>
                        </li>
                        <li className={`nav-link ${window.location.pathname === '/dashboard/cartoes' ? 'active' : ''}`}>
                            <a href="/dashboard/cartoes">
                                <span className="material-symbols-outlined">credit_card</span>
                                <span className="text nav-text">Cartões</span>
                            </a>
                        </li>
                        <li className={`nav-link ${window.location.pathname === '/dashboard/porquinho' ? 'active' : ''}`}>
                            <a href="/dashboard/porquinho">
                                <span className="material-symbols-outlined">savings</span>
                                <span className="text nav-text">Porquinho</span>
                            </a>
                        </li>
                        <li className={`nav-link ${window.location.pathname === '/dashboard/configuracoes' ? 'active' : ''}`}>
                            <a href="/dashboard/configuracoes">
                                <span className="material-symbols-outlined">settings</span>
                                <span className="text nav-text">Configurações</span>
                            </a>
                        </li>
                        <li className="nav-link ">
                            <a href="/login" onClick={() => {
                                localStorage.setItem("id", "");
                                localStorage.setItem("nome", "");
                                localStorage.setItem("token", "");
                            }}>
                                <span className="material-symbols-outlined">logout</span>
                                <span className="text nav-text">Sair</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    );
}

export default Sidebar;