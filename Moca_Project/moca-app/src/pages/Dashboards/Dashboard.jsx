import api from "../../api.js";
import Sidebar from "../../components/Sidebar";
import { useEffect, useState } from "react";
import "../../assets/css/style2.css";
import { FaSpinner } from 'react-icons/fa';
import ChartBarra from "../../components/ChartBarra";
import ChartPizza from "../../components/ChartPizza";
import Mastercard from "../../assets/img/Mastercard-Logo.png";
import Visa from "../../assets/img/logo-visa.png";
import Elo from "../../assets/img/logo-elo.png";
import Hipercard from "../../assets/img/hipercard.png";
import { useNavigate } from "react-router";

function HomeDashboard() {

    // Atributos
    // Constants para recuperar dados do localStorage
    const nomeUsuario = localStorage.getItem("nome");
    const idUsuario = localStorage.getItem("id");
    const navigate = useNavigate();
    // useStates para salvar os dados e exibir na tela
    const [saldo, setSaldo] = useState();
    const [receita, setReceita] = useState();
    const [despesa, setDespesa] = useState();
    const [saldoCartao, setSaldoCartao] = useState();
    const [graficoDespesa, setGraficoDespesa] = useState([]);
    const [graficoReceitas, setGraficoReceitas] = useState([]);
    const [opcoes, setOpcoes] = useState([]);
    const [cartoesUsuario, setCartoesUsuario] = useState([]);
    const limitarCartoes = cartoesUsuario.slice(0, 2);
    const [porquinhosUsuario, setPorquinhoUsuario] = useState([]);
    const limitarPorquinho = porquinhosUsuario.slice(0, 4);
    const corCartao = [
        { id: 1, opcao: "Azul Royal", codigo: "#0071C5" },
        { id: 2, opcao: "Verde Esmeralda", codigo: "#50C878" },
        { id: 3, opcao: "Amarelo Sol", codigo: "#FFD700" },
        { id: 4, opcao: "Vermelho Cereja", codigo: "#DC143C" },
        { id: 5, opcao: "Roxo Violeta", codigo: "#8A2BE2" },
        { id: 6, opcao: "Laranja Coral", codigo: "#FF7F50" },
        { id: 7, opcao: "Cinza Prata", codigo: "#C0C0C0" },
    ];
    const bandeiraCartao = [
        { id: 1, opcao: "Mastercard", codigo: Mastercard },
        { id: 2, opcao: "Visa", codigo: Visa },
        { id: 3, opcao: "Elo", codigo: Elo },
        { id: 4, opcao: "Hipercard", codigo: Hipercard },
    ];
    const dateAno = new Date();




    // Funções
    // Validar se o usuario efetuou login antes de acessar a dashboard
    function verificarAutenticacao() {
        if (idUsuario === "") {
            navigate("/login");
        }
    }

    //Quando carregar a página executara este useEffect
    useEffect(() => {
        verificarAutenticacao();
        requisicao();
    }, []);

    useEffect(() => {
        const dataAtual = new Date();
        const meses = [];
        for (let i = 0; i < 12; i++) {
            const data = new Date(dataAtual.getFullYear(), dataAtual.getMonth() + i, 1);
            const mes = data.toLocaleString('pt-br', { month: 'long' }).toUpperCase();
            meses.push({ value: `${1 + data.getMonth()}`, label: `${mes}` });
        }
        setOpcoes(meses);
    }, []);



    // Requisição do endpoint para mostrar as informações do usuário
    function requisicao(props) {
        const data = new Date();
        const ano = data.getFullYear();
        api.get(`home/${idUsuario}/${props ? props : data.getMonth() + 1}/${ano}`).then((response) => {
            console.log(response);
            setSaldo(response.data.saldo);
            setGraficoReceitas(response.data.graficoReceitas.indices);
            setGraficoDespesa(response.data.graficoDespesas.indices);
            setReceita(response.data.receita);
            setDespesa(response.data.despesas);
            setSaldoCartao(response.data.despesaCartao);
            setCartoesUsuario(response.data.cartoes);
            setPorquinhoUsuario(response.data.porquinhos);
        });
    }


    // Return do HTML
    return (
        <div>
            <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" />
            <Sidebar />
            <div className="main-content">
                <header className="header">
                    <h2>
                        <label style={{ cursor: 'pointer' }} htmlFor="nav-toggle">
                            {/* <span className="material-symbols-outlined">menu</span> */}
                        </label>
                    </h2>

                    <div className="search-wrapper">
                        <span className="material-symbols-outlined">calendar_month</span>
                        <select onChange={(event) => { requisicao(event.target.value) }}>
                            {opcoes.map((opcao) => (
                                <option key={opcao.value} value={opcao.value}>
                                    {opcao.label} / {dateAno.getFullYear()}
                                </option>
                            ))}
                        </select>
                    </div>

                    <div className="user-wrapper">
                        <div>
                            <small>Bem vindo,</small>
                            <h4>{nomeUsuario}</h4>
                        </div>
                    </div>
                </header>

                <main className="main">
                    <div className="cards">
                        <div className="card-single">
                            <div>
                                <span>Saldo</span>
                                <h2>R$ {saldo === undefined ? <FaSpinner className="spinner" /> : saldo.toFixed(2).replace('.', ',')}</h2>
                            </div>
                            <div>
                                <span id="money" className="material-symbols-outlined">attach_money</span>
                            </div>
                        </div>
                        <div className="card-single">
                            <div>
                                <span>Receita</span>
                                <h2>R$ {receita === undefined ? <FaSpinner className="spinner" /> : receita.toFixed(2).replace('.', ',')}</h2>
                            </div>
                            <span id="up" className="material-symbols-outlined">arrow_upward</span>
                        </div>
                        <div className="card-single">
                            <div>
                                <span>Despesa</span>
                                <h2>R$ {despesa === undefined ? <FaSpinner className="spinner" /> : despesa.toFixed(2).replace('.', ',')}</h2>
                            </div>
                            <div>
                                <span id="down" className="material-symbols-outlined">arrow_downward</span>
                            </div>
                        </div>
                        <div className="card-single">
                            <div>
                                <span>Cartões</span>
                                <h2>R$ {saldoCartao === undefined ? <FaSpinner className="spinner" /> : saldoCartao.toFixed(2).replace('.', ',')}</h2>
                            </div>
                            <div>
                                <span id="cartao" className="material-symbols-outlined">credit_card</span>
                            </div>
                        </div>
                    </div>

                    <div className="cards-dash">

                        <div className="card-pos">

                            <div className="card-pizza" >
                                <ChartBarra receita={receita} despesa={despesa} />
                                <div className="info-despesa-barra">
                                    <h3>Receitas: <span style={{ color: "#63B967" }}>R$ {receita === undefined ? <FaSpinner className="spinner" /> : receita.toFixed(2).replace('.', ',')}</span></h3>
                                    <h3>Despesas: <span style={{ color: "#E92121" }}>R$ {despesa === undefined ? <FaSpinner className="spinner" /> : despesa.toFixed(2).replace('.', ',')}</span></h3>
                                    <hr />
                                    <h3>Balanço: <span>R$ {saldo === undefined ? <FaSpinner className="spinner" /> : saldo.toFixed(2).replace('.', ',')}</span></h3>
                                </div>
                            </div>
                        </div>
                        <div className="card-pos">
                            <h2>Receitas por categorias</h2>
                            <br />
                            <ChartPizza props={graficoReceitas} mensagem="receita" />
                        </div>
                        <div className="card-pos">
                            <h2>Cartões</h2>
                            <div className="container-card-cartao-dash">
                                {limitarCartoes.map((cartao) => {
                                    const corSelecionada = corCartao.find(cor => cor.id === cartao.idCor);
                                    const bandeiraSelecionada = bandeiraCartao.find(bandeira => bandeira.opcao === cartao.bandeira);
                                    return (
                                        <div key={cartao.idCor} className="card-cartao-dash">
                                            <div className="cartao-dash" style={{ backgroundColor: `${corSelecionada.codigo}` }}>
                                                <div className="imagem-cartao-dash">
                                                    <img src={bandeiraSelecionada.codigo} alt="" />
                                                </div>
                                            </div>
                                            <div className="nome-cartao-dash">{cartao.nome}</div>
                                        </div>
                                    )
                                })}
                                <div className="saber-mais-cartao" onClick={() => navigate('/dashboard/cartoes')}>
                                    <span className="material-symbols-outlined">chevron_right</span>
                                    <span>Saber mais</span>
                                </div>
                            </div>
                        </div>
                        <div className="card-pos">
                            <h2>Despesa por categoria</h2>
                            <br />
                            <ChartPizza props={graficoDespesa} mensagem="despesa" />
                        </div>
                        <div className="card-pos">
                            <h2>Porquinho</h2>
                            <div className="container-card-cartao-dash">
                                {limitarPorquinho.map((porquinho) => {
                                    const porcentagem = (porquinho.valorAtual / porquinho.valorFinal) * 100;
                                    return (
                                        <div className="card-porquinho-home" key={porquinho.id}>
                                            <span>{porcentagem.toFixed(0)}%</span>
                                            <span>{porquinho.nome}</span>
                                        </div>
                                    )
                                })}
                                <div className="saber-mais-porquinho" onClick={() => navigate('/dashboard/porquinho')}>
                                    <span className="material-symbols-outlined">chevron_right</span>
                                    <span>Saber mais</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
            </div>
        </div>
    );
}

export default HomeDashboard;