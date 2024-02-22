import "../assets/css/popup.css";
import { useEffect, useState } from "react";
import { FaSpinner } from "react-icons/fa";
import api from "../api.js";
import { useNavigate } from "react-router";
import { set } from "date-fns";

function PopUpCadastro({ isOpen, setModalOpen, children }) {

    // Atributos

    const idUsuario = localStorage.getItem("id");
    const navigate = useNavigate();
    const styles = {
        esconder: { display: 'none' },
        mostrar: { display: 'block' }
    };
    const opcoesDespesa = [
        { id: 1, opcao: "Moradia" },
        { id: 2, opcao: "Alimentação" },
        { id: 3, opcao: "Transporte" },
        { id: 4, opcao: "Saúde" },
        { id: 5, opcao: "Educação" },
        { id: 6, opcao: "Lazer" },
        { id: 7, opcao: "Vestuário" },
        { id: 8, opcao: "Dívidas" },
        { id: 9, opcao: "Impostos" },
        { id: 10, opcao: "Outras" }
    ];
    const opcoesReceita = [
        { id: 1, opcao: "Salário" },
        { id: 2, opcao: "Rendimentos" },
        { id: 3, opcao: "Vendas de Bens" },
        { id: 4, opcao: "Freelance" },
        { id: 5, opcao: "Aluguel" },
        { id: 6, opcao: "Ajuda Financeira" },
        { id: 7, opcao: "Reembolsos" },
        { id: 8, opcao: "Prêmios" },
        { id: 9, opcao: "Outras fontes de receita" }
    ];
    const tipoDespesa = [
        { id: 1, opcao: "Dinheiro" },
        { id: 2, opcao: "Fixa" },
        { id: 3, opcao: "Cartão" }
    ];
    const parcelas = [
        { id: 1, opcao: "1x" },
        { id: 2, opcao: "2x" },
        { id: 3, opcao: "3x" },
        { id: 4, opcao: "4x" },
        { id: 5, opcao: "5x" },
        { id: 6, opcao: "6x" },
        { id: 7, opcao: "7x" },
        { id: 8, opcao: "8x" },
        { id: 9, opcao: "9x" },
        { id: 10, opcao: "10x" },
        { id: 11, opcao: "11x" },
        { id: 12, opcao: "12x" },
    ];
    const dateSystem = new Date();
    // Campos
    const [categoria, setCategoria] = useState('0');
    const [clicou, setClicou] = useState(false);
    const [valorValue, setValorValue] = useState(0);
    const [descricaoValue, setDescricaoValue] = useState('');
    const [tipoDespesaValue, setTipoDespesaValue] = useState('0');
    const [fixa, setFixa] = useState(false);
    const [parcelada, setParcelada] = useState(false);
    const [isCartao, setIsCartao] = useState('0');
    const [dadosCartao, setDadosCartao] = useState([]);
    const [idCartao, setIdCartao] = useState();
    const [data, setData] = useState('');
    const [quantidadeParcelas, setQuantidadeParcelas] = useState('0');
    const [desabilitado, setDesabilitado] = useState(false);
    // Alerts
    const [alertValor, setAlertValor] = useState('');
    const [alertCategoria, setAlertCategoria] = useState('');
    const [alertDescricao, setAlertDescricao] = useState('');
    const [alertData, setAlertData] = useState('');
    const [alertQtdParcelas, setAlertQtdParcelas] = useState('');
    const [alertCartao, setAlertCartao] = useState('');
    const [alertTpDespesa, setAlertTpDespesa] = useState('');
    const [alertLimite, setAlertLimite] = useState('');




    function adicionarDespesa() {

        const cartaoEncontrado = dadosCartao.find(cartao => cartao.idCartao === parseInt(isCartao));

        if (tipoDespesaValue === '1') {

            setAlertValor(valorValue <= 0 ? "Informe um valor positivo" : '');
            setAlertCategoria(categoria === '0' ? "Selecione uma categoria" : '');
            setAlertDescricao(descricaoValue.trim() === '' ? "Digite uma descrição" : '');
            setAlertData(data === '' ? "Informe uma data" : '');
            setAlertTpDespesa(tipoDespesaValue === '0' ? "Selecione o tipo de despesa" : "");

            if (valorValue > 0 && categoria !== '0' && descricaoValue.trim() !== '' && data !== '') {
                setClicou(true);
                api.post("despesas/", {
                    descricao: descricaoValue,
                    valor: valorValue,
                    data: data,
                    isPaid: false,
                    isParcela: false,
                    idCliente: idUsuario,
                    idTipoDespesa: categoria,
                }).then((response) => {
                    console.log(response.data);
                    window.location.href = '/dashboard/despesa';
                }).catch((err) => {
                    if (err.response.status() === 404) {
                        alert("Página não encontrada!");
                    }
                    setClicou(false);
                });
            }

        } else if (tipoDespesaValue === '2') {

            setAlertValor(valorValue <= 0 ? "Informe um valor positivo" : '');
            setAlertCategoria(categoria === '0' ? "Selecione uma categoria" : '');
            setAlertDescricao(descricaoValue.trim() === '' ? "Digite uma descrição" : '');
            setAlertData(data === '' ? "Informe uma data" : '');
            setAlertTpDespesa(tipoDespesaValue === '0' ? "Selecione o tipo de despesa" : "");

            if (valorValue > 0 && categoria !== '0' && descricaoValue.trim() !== '' && data !== '' && parseInt(isCartao) !== 0) {

                // setClicou(true);
                // console.log((cartaoEncontrado.utilizado - cartaoEncontrado.limite));
                if ((cartaoEncontrado.limite - cartaoEncontrado.utilizado) >= (valorValue * 12)) {
                    setAlertLimite('');
                    api.post("despesas/fixa", {
                        descricao: descricaoValue,
                        valor: valorValue,
                        data: data,
                        idCliente: idUsuario,
                        idTipoDespesa: categoria,
                        isCartao: true,
                        idCartao: isCartao,
                        paid: false,
                        parcela: true
                    }).then((response) => {
                        console.log(response);
                        window.location.href = '/dashboard/despesa';
                    }).catch((err) => {
                        console.log(err);
                        setClicou(false);
                    });
                } else if ((cartaoEncontrado.limite - cartaoEncontrado.utilizado) < (valorValue * 12)) {
                    setAlertLimite('Valor ultrapassa o limite do cartão');
                    setClicou(false);
                }
            }

            if (parseInt(isCartao) === 0 && valorValue > 0 && categoria !== '0' && descricaoValue.trim() !== '' && data !== '') {
                setAlertLimite('');
                api.post("despesas/fixa", {
                    descricao: descricaoValue,
                    valor: valorValue,
                    data: data,
                    idCliente: idUsuario,
                    idTipoDespesa: categoria,
                    isCartao: false,
                    idCartao: null,
                    paid: false,
                    parcela: true
                }).then((response) => {
                    console.log(response);
                    window.location.href = '/dashboard/despesa';
                }).catch((err) => {
                    console.log(err)
                    setClicou(false);
                });
            }

        } else if (tipoDespesaValue === '3') {

            setAlertValor(valorValue <= 0 ? "Informe um valor positivo" : '');
            setAlertCategoria(categoria === '0' ? "Selecione uma categoria" : '');
            setAlertDescricao(descricaoValue.trim() === '' ? "Digite uma descrição" : '');
            setAlertData(data === '' ? "Informe uma data" : '');
            setAlertQtdParcelas(quantidadeParcelas === '0' ? 'Selecione quantas parcelas' : '');
            setAlertCartao(isCartao === '0' ? 'Selecione um cartão' : '');
            setAlertTpDespesa(tipoDespesaValue === '0' ? "Selecione o tipo de despesa" : "");

            if (valorValue > 0 && categoria !== '0' && descricaoValue.trim() !== '' && data !== ''
                && quantidadeParcelas !== '0' && parseInt(isCartao) !== 0) {
                setClicou(true);
                if ((cartaoEncontrado.limite - cartaoEncontrado.utilizado) >= valorValue) {
                    api.post("despesas/parcelada", {
                        descricao: descricaoValue,
                        valor: valorValue,
                        data: data,
                        idCliente: idUsuario,
                        idTipoDespesa: categoria,
                        parcelas: quantidadeParcelas,
                        idCartao: isCartao
                    }).then((response) => {
                        console.log(response);
                        window.location.href = '/dashboard/despesa';
                    }).catch((err) => {
                        console.log(err)
                        setClicou(false);
                    });
                } else if ((cartaoEncontrado.limite - cartaoEncontrado.utilizado) < valorValue) {
                    setAlertLimite('Valor ultrapassa o limite do cartão');
                    setClicou(false);
                } else {
                    setClicou(false);
                }
            }
        } else if (tipoDespesaValue === '0') {

            setAlertValor(valorValue <= 0 ? "Informe um valor positivo" : '');
            setAlertCategoria(categoria === '0' ? "Selecione uma categoria" : '');
            setAlertDescricao(descricaoValue.trim() === '' ? "Digite uma descrição" : '');
            setAlertData(data === '' ? "Informe uma data" : '');
            setAlertTpDespesa(tipoDespesaValue === '0' ? "Selecione o tipo de despesa" : "");
        }

    }

    function adicionarReceita() {

        setAlertValor(valorValue <= 0 ? "Informe um valor positivo!" : "");
        setAlertCategoria(categoria === '0' ? "Selecione uma categoria!" : "");
        setAlertDescricao(descricaoValue.trim() === '' ? "Digite uma descrição!" : "");
        setAlertData(data === '' ? "Informe uma data" : "");


        if (valorValue > 0 && categoria !== '0' && descricaoValue.trim() !== '' && data !== '') {
            setClicou(true);
            api.post("receitas/", {
                descricao: descricaoValue,
                valor: valorValue,
                data: data,
                idCliente: idUsuario,
                idTipoReceita: categoria,
            }).then((response) => {
                console.log(response.data);
                window.location.href = '/dashboard/receita';
            }).catch((err) => {
                console.error(err);
                setClicou(false);
            });

        }
    }

    useEffect(() => {
        const data = new Date();
        const ano = data.getFullYear();
        api.get(`cartoes/${idUsuario}/${data.getMonth() + 1}/${ano}`).then((response) => {
            console.log(response.data.cartoes);
            setDadosCartao(response.data.cartoes);
        });
    }, []);


    if (isOpen) {
        return (
            <div id="demo-modal" className="modal">
                <div className="modal__content">
                    <h1>{window.location.pathname === '/dashboard/despesa' ? 'Nova Despesa' : 'Nova Receita'}</h1>

                    <div className="input-box">
                        <label className="input-label">Valor</label>
                        <input placeholder="00,00" onChange={(event) => setValorValue(event.target.value)} className="input" type="number" />
                        <small>{alertValor}</small>
                    </div>

                    <div className="input-box">
                        <label className="input-label">Categoria</label>
                        <select id="categoria" className="selecao" onChange={(event) => { setCategoria(event.target.value) }}>
                            <option value="0">-- Selecione --</option>
                            {window.location.pathname === '/dashboard/despesa' ?
                                opcoesDespesa.map(opcao => (
                                    <option key={opcao.id} value={opcao.id}>
                                        {opcao.opcao}
                                    </option>
                                )) :
                                opcoesReceita.map(opcao => (
                                    <option key={opcao.id} value={opcao.id}>
                                        {opcao.opcao}
                                    </option>
                                ))
                            }
                        </select>
                        <small>{alertCategoria}</small>
                    </div>

                    <div className="input-box">
                        <label className="input-label">Descrição</label>
                        <input onChange={(event) => setDescricaoValue(event.target.value)} className="input" type="text" />
                        <small>{alertDescricao}</small>
                    </div>

                    <div className="input-box">
                        <label className="input-label">Data</label>
                        <input className="input" type="date" onChange={(event) => setData(event.target.value)} />
                        <small>{alertData}</small>
                    </div>

                    {/* Mostra as opcoes de despesas para serem cadastradas */}
                    {window.location.pathname === '/dashboard/despesa' ?
                        <div>
                            <div className="input-box">
                                <label className="input-label">Tipo de despesa</label>
                                <select onChange={(event) => {
                                    setTipoDespesaValue(event.target.value);
                                    const selectedOption = parseInt(event.target.value);
                                    const isParceladaOption = selectedOption === 3;
                                    const isFixa = selectedOption === 2;
                                    const hasCartoes = dadosCartao.length > 0;
                                    setParcelada(isParceladaOption);
                                    setFixa(isFixa);
                                    setDesabilitado(isParceladaOption && !hasCartoes);
                                }} className="selecao" >
                                    <option value="0">--Selecione--</option>
                                    {tipoDespesa.map(tipo => (
                                        <option key={tipo.id} value={tipo.id}>{tipo.opcao}</option>
                                    ))}
                                </select>
                                <small>{alertTpDespesa}</small>
                                {parcelada && !desabilitado ? null : (
                                    <small>{desabilitado ? 'Nenhum cartão disponível' : ''}</small>
                                )}
                                {/* {parcelada && dadosCartao.length === 0 ? setDesabilitado(true) : setDesabilitado(false)} */}
                            </div>
                        </div>
                        : null}

                    {/* Se for fixa abre estas opcoes*/}
                    {fixa ?
                        <div>
                            <div className="input-box">
                                <label className="input-label">Cartão</label>
                                <select id="" className="selecao" onChange={(event) => { setIsCartao(event.target.value) }}>
                                    <option value="0">--Selecione--</option>
                                    <option value="0">Nenhum</option>
                                    {dadosCartao.map(dados => (
                                        <option key={dados.idCartao} value={dados.idCartao}>{dados.apelido}</option>
                                    ))}
                                </select>
                                <small>{alertLimite}</small>
                            </div>
                        </div>
                        : null}

                    {parcelada && dadosCartao.length > 0 ?
                        <div>
                            <div className="input-box">
                                <label className="input-label">Parcelas</label>
                                <select id="" className="selecao" onChange={(event) => setQuantidadeParcelas(event.target.value)}>
                                    <option value="0">--Selecione--</option>
                                    {parcelas.map(parcelas => (
                                        <option key={parcelas.id} value={parcelas.id}>{parcelas.opcao}</option>
                                    ))}
                                </select>
                                <small>{alertQtdParcelas}</small>
                            </div>
                            <div className="input-box">
                                <label className="input-label">Cartão</label>
                                <select id="" className="selecao" onChange={(event) => setIsCartao(event.target.value)}>
                                    <option value="0">--Selecione--</option>
                                    {dadosCartao.map(dados => (
                                        <option key={dados.idCartao} value={dados.idCartao}>{dados.apelido}</option>
                                    ))}
                                </select>
                                <small>{alertCartao}</small>
                                <small>{alertLimite}</small>
                            </div>
                        </div>
                        : null}

                    <div className={window.location.pathname === '/dashboard/despesa' ? "modal__footer modal_footer_receita" : "modal__footer modal_footer_receita"}>
                        <div><FaSpinner className="spinner" style={clicou ? styles.mostrar : styles.esconder} /></div>
                        <button disabled={desabilitado === true} style={clicou ? styles.esconder : styles.mostrar}
                            onClick={window.location.pathname === '/dashboard/despesa' ? () => adicionarDespesa() : () => adicionarReceita()}>Adicionar</button>
                    </div>

                    <span onClick={setModalOpen} className="modal__close">&times;</span>
                </div>
            </div>
        );
    }

    return null;
}

export default PopUpCadastro;