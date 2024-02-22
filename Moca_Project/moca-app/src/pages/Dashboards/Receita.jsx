import { useState } from "react";
import api from "../../api.js";
import Sidebar from "../../components/Sidebar";
import "../../assets/css/style2.css";
import PopUpCadastro from "../../components/PopupCadastro";
import { useEffect } from "react";
import LinhaTabela from "../../components/Tabela";
import { FaSpinner } from 'react-icons/fa';
import PaginacaoMesesInput from "../../components/PaginacaoMesesInput.jsx";
import { useNavigate } from "react-router";


function Receitas() {

    // Atributos
    const [loading, setLoading] = useState(false);
    // Constants para recuperar dados do localStorage
    const nomeUsuario = localStorage.getItem("nome");
    const idUsuario = localStorage.getItem("id");
    const [receita, setReceita] = useState();
    const [mesAtual, setMesAtual] = useState(new Date().getMonth() + 1);
    const [anoAtual, setAnoAtual] = useState(new Date().getFullYear());
    const [showModal, setShowModal] = useState(false);
    const [listaReceitas, setListaReceitas] = useState([]);
    const data = new Date();
    const ano = data.getFullYear();
    const navigate = useNavigate();


    // Funções
    // Validar se o usuario efetuou login antes de acessar a dashboard
    function verificarAutenticacao() {
        if (idUsuario === "") {
            navigate("/login");
        }
    }
    useEffect(() => {
        // verificarAutenticacao();
        requisicao(mesAtual, anoAtual);
        requisicaoListaReceita(mesAtual, anoAtual);
        setLoading(true);
        verificarAutenticacao();
    }, []);

    function requisicao(novoMes, novoAno) {
        // Constants para mes e ano que serão passadas na url
        // Requisição para buscar as receitas do usuario
        api.get(`home/${idUsuario}/${novoMes}/${novoAno}`).then((response) => {
            console.log(novoAno);
            console.log(novoMes);
            console.log(response);
            setReceita(response.data.receita);
            setLoading(true);
        });
    }

    function requisicaoListaReceita(novoMes, novoAno) {
        api.get(`receitas/${idUsuario}/${novoMes}/${novoAno}`).then((response) => {
            setListaReceitas([...response.data]);
            console.log(response.data)
        });
    }

    // Extrato por mes 
    const atualizarMesSelecionado = (novoMes, novoAno) => {
        setMesAtual(novoMes);
        setAnoAtual(novoAno);
        requisicaoListaReceita(novoMes, novoAno); // Passar ambos os valores aqui
        requisicao(novoMes, novoAno);
    };

    const atualizarCampos = () => {
        requisicao(mesAtual, anoAtual);
        requisicaoListaReceita(mesAtual, anoAtual);
        setLoading(true);
    };

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
                                    <span>Receita</span>
                                    <h2>R$ {receita === undefined ? <FaSpinner className="spinner" /> : receita.toFixed(2).replace('.', ',')}</h2>

                                </div>
                                <span id="up" className="material-symbols-outlined">arrow_upward</span>
                            </div>
                            <div className="card-receita" onClick={() => { setShowModal(true) }}>
                                <div>
                                    <span>Nova Receita</span> <br />
                                    <h2 className="material-symbols-outlined">add</h2>
                                </div>
                            </div>
                        </div>
                    </div>
                    <PaginacaoMesesInput setMesAno={atualizarMesSelecionado} />
                    <div className="table-container">
                        <h2 className="heading">
                        </h2>
                        <table className="table">
                            <thead>
                                <tr>
                                    <th>Situação</th>
                                    <th>Data</th>
                                    <th>Descrição</th>
                                    <th>Categoria</th>
                                    <th>Valor</th>
                                    <th>Ações</th>
                                </tr>
                            </thead>
                            {listaReceitas.map((receita) => {
                                return (
                                    <LinhaTabela receita={receita} key={receita.idReceita} atualizar={atualizarCampos}/>
                                )
                            })}
                        </table>
                    </div>
                </main>
            </div>
            <PopUpCadastro isOpen={showModal} setModalOpen={() => { setShowModal(!showModal) }}></PopUpCadastro>
        </div>
    );
}

export default Receitas;