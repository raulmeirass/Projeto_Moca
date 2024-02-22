import api from "../../api.js";
import Sidebar from "../../components/Sidebar";
import "../../assets/css/style2.css";
import { useState } from "react";
import { useEffect } from "react";
import LinhaExtrato from "../../components/TabelaExtrato";
import { FaSpinner } from 'react-icons/fa';
import PaginacaoMesesInput from "../../components/PaginacaoMesesInput";
import { useNavigate } from "react-router";


function Extrato() {
    const [loading, setLoading] = useState(false);

    // Constants para recuperar dados do localStorage
    const nomeUsuario = localStorage.getItem("nome");
    const idUsuario = localStorage.getItem("id");
    // const tokenUsuario = localStorage.getItem("token");

    const [saldo, setSaldo] = useState();
    const [receita, setReceita] = useState();
    const [despesa, setDespesa] = useState();
    const [ativo, setAtivo] = useState(true);
    const [mesAtual, setMesAtual] = useState(new Date().getMonth() + 1);
    const [anoAtual, setAnoAtual] = useState(new Date().getFullYear());
    const [extrato, setExtrato] = useState([]);
    const navigate = useNavigate();
    const [selectedFile, setSelectedFile] = useState('');

    const handleFileChange = (event) => {
        const file = event.target.files[0];
        setSelectedFile(file);

        if (file) {
            const formData = new FormData();
            formData.append('file', file);

            const config = {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            };

            api.post(`extrato/arquivoTxt/upload/${idUsuario}`, formData, config)
                .then(response => {
                    console.log(response);
                })
                .catch(error => {
                    console.error(error);
                });
        }
    };



    // Validar se o usuario efetuou login antes de acessar a dashboard
    function verificarAutenticacao() {
        if (idUsuario === "") {
            navigate("/login");
        }

    }
    verificarAutenticacao();

    useEffect(() => {
        verificarAutenticacao();
        setLoading(true);
        requisicao(mesAtual, anoAtual);
    }, []);


    // Requisição do endpoint para mostrar as informações do usuário
    function requisicao(novoMes, novoAno) {
        api.get(`home/${idUsuario}/${novoMes}/${novoAno}`).then((response) => {
            console.log(response);
            setSaldo(response.data.saldo);
            setReceita(response.data.receita);
            setDespesa(response.data.despesas);
            setLoading(false);
        });

        api.get(`extrato/${idUsuario}/${novoMes}/${novoAno}`).then((response) => {
            setExtrato([...response.data.items]);
            console.log(response.data.items);
            // console.log('tabela')
        });
    }

    function download() {
        api.get(`extrato/arquivo/${idUsuario}/${mesAtual}/${anoAtual}`, {
            responseType: 'arraybuffer'
        }).then(response => {
            // Cria um blob a partir dos dados recebidos
            const blob = new Blob([response.data], { type: 'application/vnd.ms-excel' });
            // Cria uma URL temporária para o blob
            const url = window.URL.createObjectURL(blob);
            // Cria um elemento <a> no DOM
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', 'extrato.csv');
            // Simula um clique no elemento <a> para iniciar o download
            document.body.appendChild(link);
            link.click();
            link.remove();
        }).catch(error => {
            console.error('Erro ao baixar o arquivo:', error);
            // Exibe uma mensagem de erro para o usuário
        });

    }

    function downloadTxt() {
        api.get(`extrato/arquivoTxt/${idUsuario}/${mesAtual}/${anoAtual}`).then((response) => {
            // Cria um blob a partir dos dados recebidos
            const blob = new Blob([response.data], { type: 'text/plain' }); // Altere o tipo para 'text/plain' para gerar um arquivo TXT
            // Resto do código permanece igual
            const url = window.URL.createObjectURL(blob);
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', 'arquivo.txt'); // Altere o nome do arquivo para 'arquivo.txt' ou o nome desejado
            document.body.appendChild(link);
            link.click();
            link.remove();
        }).catch((err) => {
            console.error('Erro ao baixar o arquivo:', err);
        })
    }



    // Extrato por mes 
    const atualizarMesSelecionado = (novoMes, novoAno) => {
        setMesAtual(novoMes);
        setAnoAtual(novoAno);
        requisicao(novoMes, novoAno);
    }

    return (
        <div>
            <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" />
            {ativo && <Sidebar />}
            <div className="main-content">
                <header className="header">
                    <h2>
                        <label style={{ cursor: 'pointer' }} htmlFor="nav-toggle" onClick={() => ativo ? setAtivo(false) : setAtivo(true)}>
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
                        <div className="btn">
                            <button className="buttonDownload" onClick={() => download()}> Download Excel </button>
                            <button className="buttonDownload" onClick={() => downloadTxt()}> Download TXT </button>
                            {/* <div>
                                <label htmlFor="file-upload" className="buttonDownload">
                                    <i className="fas fa-cloud-upload-alt"></i> Upload arquivo
                                </label>
                                <input id="file-upload" type="file" style={{ display: 'none' }} onChange={handleFileChange} />
                            </div> */}
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
                                </tr>
                            </thead>
                            <tbody>
                                {extrato.map((extrato) => {
                                    return (
                                        <LinhaExtrato props={extrato} key={extrato.idReceita === null ? extrato.idDespesa : extrato.idReceita} />
                                    )
                                })}
                            </tbody>
                        </table>
                    </div>
                </main>
            </div>
        </div>
    );
}

export default Extrato;