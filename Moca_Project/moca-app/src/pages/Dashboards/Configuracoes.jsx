import { useEffect } from "react";
import api from "../../api";
import Sidebar from "../../components/Sidebar";
import { useState } from "react";

function Configuracoes() {

    // Atributos
    const nomeUsuario = localStorage.getItem("nome");
    const idUsuario = localStorage.getItem("id");
    const [configUsuario, setConfigUsuario] = useState({
        enviaSms: false,
        enviaEmail: false,
        telefone: "",
        email: "",
    });
    const [editar, setEditar] = useState(false);

    function requisicao() {
        api.get(`usuarios/config/${idUsuario}`).then((response) => {
            console.log(response.data);
            setConfigUsuario(response.data);
        });
    }

    function salvarEdicao() {
        const { enviaEmail, enviaSms, telefone, email } = configUsuario;
        const data = {
            enviarEmail: enviaEmail,
            enviarSms: enviaSms,
            numeroCelular: telefone,
            email: email,
        };

        api.patch(`usuarios/config/${idUsuario}`, data)
            .then((response) => {
                console.log(response);
                requisicao();
            })
            .catch((error) => {
                console.log(error);
            });
    }


    useEffect(() => {
        requisicao();
    }, []);

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
                    <div className="container-config">
                        <h1>Configurações</h1>
                        <div className="config">
                            <div className="section-config">
                                <span>Ativar lembretes (Notificações do APP)</span>
                                <label className="toggle-switch">
                                    <input
                                        type="checkbox"
                                        defaultChecked={configUsuario.enviaSms}
                                        disabled={!editar}
                                        onChange={(event) =>
                                            setConfigUsuario((prevState) => ({
                                                ...prevState,
                                                enviaSms: event.target.checked,
                                            }))
                                        }
                                    />
                                    <div className="toggle-switch-background">
                                        <div className="toggle-switch-handle"></div>
                                    </div>
                                </label>
                            </div>
                            <div className="section-config">
                                <span>Ativar notificações SMS</span>
                                <label className="toggle-switch">
                                    <input
                                        type="checkbox"
                                        defaultChecked={configUsuario.enviaEmail}
                                        disabled={!editar}
                                        onChange={(event) =>
                                            setConfigUsuario((prevState) => ({
                                                ...prevState,
                                                enviaEmail: event.target.checked,
                                            }))
                                        }
                                    />
                                    <div className="toggle-switch-background">
                                        <div className="toggle-switch-handle"></div>
                                    </div>
                                </label>
                            </div>
                            <div className="section-config">
                                <span>Número celular</span>
                                <input
                                    type="text"
                                    className="input-text-config"
                                    value={configUsuario.telefone}
                                    disabled={!editar}
                                    onChange={(event) =>
                                        setConfigUsuario({
                                            ...configUsuario,
                                            telefone: event.target.value,
                                        })
                                    }
                                />
                            </div>
                            <div className="section-config">
                                <span>E-mail</span>
                                <input
                                    type="text"
                                    className="input-text-config"
                                    value={configUsuario.email}
                                    disabled={!editar}
                                    onChange={(event) =>
                                        setConfigUsuario({
                                            ...configUsuario,
                                            email: event.target.value,
                                        })
                                    }
                                />
                            </div>
                            {editar ? (
                                <button
                                    className="config-btn"
                                    onClick={() => {
                                        setEditar(false);
                                        salvarEdicao();
                                    }}
                                >
                                    <span className="material-symbols-outlined">save</span>{" "}
                                    Salvar
                                </button>
                            ) : (
                                <button
                                    className="config-btn-editar"
                                    onClick={() => setEditar(true)}
                                >
                                    <span className="material-symbols-outlined">edit</span>{" "}
                                    Editar
                                </button>
                            )}
                        </div>
                    </div>
                </main>
            </div>
        </div>
    );
}

export default Configuracoes;