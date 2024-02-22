import { useState } from "react";
import Sidebar from "../../components/Sidebar";
import { FaSpinner } from "react-icons/fa";
import PopUpPorquinho from "../../components/PopUpPorquinho"
import { useEffect } from "react";
import CardPorquinho from "../../components/CardPorquinho";
import api from "../../api.js";
import { useNavigate } from "react-router";

function Porquinho() {

    // Atributos
    const nomeUsuario = localStorage.getItem("nome");
    const idUsuario = localStorage.getItem("id");
    const [porquinhosUsuario, setPorquinhosUsuario] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [porcentagem, setPorcentagem] = useState();
    const navigate = useNavigate();

    
    // Funções
    function requisicao() {
        api.get(`porquinhos/${idUsuario}`).then((response) => {
            // console.log(response.data);
            setPorquinhosUsuario(response.data);
        });
    }
    useEffect(() => {
        requisicao();
    }, []);

    function handleClick(idPorquinho) {
        navigate(`/dashboard/porquinho/extrato/${idPorquinho}`);
        api.get(`porquinhos/mostrarPorcentagem/${idUsuario}/${idPorquinho}`).then((response) => {
            setPorcentagem(response.data);
        });
    }


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
                    <div className="container-cards">
                        <div className="cards-receitas">
                            <div className="card-single-receita">
                                <div>
                                    <span>Seus Porquinhos</span>
                                    <h2>{porquinhosUsuario.length === undefined ? <FaSpinner className="spinner" /> : porquinhosUsuario.length}</h2>

                                </div>
                                <span id="cartao-card" className="material-symbols-outlined">savings</span>
                            </div>
                            <div className="card-cartao" onClick={() => { setShowModal(true) }}>
                                <div>
                                    <span>Novo Porquinho</span> <br />
                                    <h2 className="material-symbols-outlined">add</h2>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="cartoes-usuario">
                        {porquinhosUsuario.map((opcao) => {
                            return (
                            <CardPorquinho key={opcao.idPorquinho} porcentagem={porcentagem} opcao={opcao} onClick={() => handleClick(opcao.idPorquinho)} />
                        )})}
                    </div>

                    <div className="frase">Guardando dinheiro consistentemente, você está investindo em seu futuro e transformando seus sonhos em metas alcançáveis</div>

                </main>
            </div>
            <PopUpPorquinho isOpen={showModal} setModalOpen={() => { setShowModal(!showModal) }} />
        </div>
    );
}

export default Porquinho;