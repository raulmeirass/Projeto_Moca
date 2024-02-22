import Sidebar from "../../components/Sidebar";

function NotFound() {

    // Atributos
    const nomeUsuario = localStorage.getItem("nome");

    // Return do HTML
    return (
        <div>
            <Sidebar />
            <div className="main-content">
                <header className="header">
                    <h2>
                        <label style={{ cursor: "pointer" }} htmlFor="nav-toggle">
                            {/* <span className="material-symbols-outlined">menu</span> */}
                        </label>
                    </h2>
                    <div className="user-wrapper">
                        <div>
                            <small>Bem vindo,</small>
                            <h4>{nomeUsuario}</h4>
                        </div>
                    </div>
                </header>
                <main className="main">
                    <div className="container-not-found">
                        <h1>404</h1>
                        <h2>Ops! Página não encontrada</h2>
                    </div>
                </main>
            </div>
        </div>
    );
}

export default NotFound;