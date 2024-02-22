import api from "../api.js";
import { useState } from "react";
import { FaSpinner } from "react-icons/fa";
import { useNavigate } from "react-router";

function PopUpCartao({ isOpen, setModalOpen }) {
    const idUsuario = localStorage.getItem("id");
    const [clicou, setClicou] = useState(false);
    const [limite, setLimite] = useState(0);
    const [tipo, setTipo] = useState('0');
    const [apelido, setApelido] = useState('');
    const [vencimento, setVencimento] = useState('')
    const [bandeira, setBandeira] = useState('0');
    const [cor, setCor] = useState('0');
    const navigate = useNavigate();
    const corCartao = [
        { id: 1, opcao: "Azul Royal", codigo: "#0071C5" },
        { id: 2, opcao: "Verde Esmeralda", codigo: "#50C878" },
        { id: 3, opcao: "Amarelo Sol", codigo: "#FFD700" },
        { id: 4, opcao: "Vermelho Cereja", codigo: "#DC143C" },
        { id: 5, opcao: "Roxo Violeta", codigo: "#8A2BE2" },
        { id: 6, opcao: "Laranja Coral", codigo: "#FF7F50" },
        { id: 7, opcao: "Cinza Prata", codigo: "#C0C0C0" },
    ];
    const bandeirasTipos = [
        { id: 1, opcao: "Visa" },
        { id: 2, opcao: "Elo" },
        { id: 3, opcao: "Mastercard" },
        { id: 4, opcao: "Hipercard" },
    ];
    const styles = {
        esconder: {
            display: 'none'
        },
        mostrar: {
            display: 'block'
        }
    };
    const [alertLimite, setAlertLimite] = useState('');
    const [alertTipo, setAlertTipo] = useState('');
    const [alertBandeira, setAlertBandeira] = useState('');
    const [alertVencimento, setAlertVencimento] = useState('');
    const [alertVencimentoRegex, setAlertVencimentoRegex] = useState('');
    const [alertCor, setAlertCor] = useState('');
    const [alertApelido, setAlertApelido] = useState('');


    function requisicao() {

        const regex = /^(0[1-9]|1[0-2])\/\d{2}$/;

        setAlertLimite(limite <= 0 ? 'Digite um valor válido' : '');
        setAlertTipo(tipo === '0' ? 'Selecione um tipo' : '');
        setAlertBandeira(bandeira === '0' ? 'Selecione uma bandeira' : '');
        setAlertVencimentoRegex(!regex.test(vencimento) ? 'Informe um vencimento válido EX: MM/AA' : '');
        setAlertVencimento(vencimento.trim() === '' ? 'Informe o vencimento' : '');
        setAlertCor(cor === '0' ? 'Selecione uma cor' : '');
        setAlertApelido(apelido.trim() === '' ? 'Digite um apelido para o cartão' : '');


        if (limite > 0 && tipo !== '0' && bandeira !== '0' && regex.test(vencimento) && vencimento.trim() !== '' && cor !== '0' && apelido.trim() !== '') {
            setClicou(true);

            api.post(`cartoes/`, {
                limite: limite,
                idCliente: idUsuario,
                idTipo: tipo,
                idCor: cor,
                bandeira: bandeira,
                apelido: apelido,
                vencimento: vencimento
            }).then((response) => {
                console.log(response);
                window.location.href = '/dashboard/cartoes';
            }).catch((err) => {
                console.error(err);
                setClicou(false);
            });
        }

    }


    // Se true ele vai abrir este POP UP
    if (isOpen) {
        return (
            <div id="demo-modal" className="modal">
                <div className="modal__content">
                    <h1>Novo Cartão</h1>

                    <div className="input-box">
                        <label className="input-label">Limite</label>
                        <input placeholder="00,00" className="input" type="number" onChange={(event) => setLimite(event.target.value)} />
                        <small>{alertLimite}</small>
                    </div>

                    <div className="input-box">
                        <label className="input-label">Tipo</label>
                        <select id="tipo" className="selecao" onChange={(event) => setTipo(event.target.value)}>
                            <option value="0">--Selecione--</option>
                            <option value="1">Débito</option>
                            <option value="2">Crédito</option>
                        </select>
                        <small>{alertTipo}</small>
                    </div>

                    <div className="input-box">
                        <label className="input-label">Bandeira</label>
                        <select id="banderia" className="selecao" onChange={(event) => { setBandeira(event.target.value) }}>
                            <option value="0">-- Selecione --</option>
                            {bandeirasTipos.map(opcao => (
                                <option key={opcao.id} value={opcao.opcao}>{opcao.opcao}</option>
                            ))}
                        </select>
                        <small>{alertBandeira}</small>
                    </div>

                    <div className="input-box">
                        <label className="input-label">Vencimento</label>
                        <input type="text" placeholder="MM/AA" className="input" id="vencimento" onChange={(event) => { setVencimento(event.target.value) }} />
                        <small>{alertVencimento}</small>
                        <small>{alertVencimentoRegex}</small>
                    </div>

                    <div className="input-box">
                        <label className="input-label">Cor</label>
                        <select id="cor" className="selecao" onChange={(event) => { setCor(event.target.value) }}>
                            <option value="0">-- Selecione --</option>
                            {corCartao.map(opcao => (
                                <option key={opcao.id} value={opcao.id}>
                                    {opcao.opcao}
                                </option>
                            ))}
                        </select>
                        <small>{alertCor}</small>
                    </div>

                    <div className="input-box">
                        <label className="input-label">Apelido</label>
                        <input type="text" className="input" onChange={(event) => setApelido(event.target.value)} />
                        <small>{alertApelido}</small>
                    </div>

                    <div className="modal__footer modal_footer_cartao">
                        <div><FaSpinner className="spinner" style={clicou ? styles.mostrar : styles.esconder} /></div>
                        <button style={clicou ? styles.esconder : styles.mostrar}
                            onClick={() => requisicao()}>Adicionar</button>
                    </div>

                    <span onClick={setModalOpen} className="modal__close">&times;</span>
                </div>
            </div>
        );
    }
    return null;
}

export default PopUpCartao;