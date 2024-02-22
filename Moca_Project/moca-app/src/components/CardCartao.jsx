import api from "../api.js";
import Mastercard from "../assets/img/Mastercard-Logo.png";
import Visa from "../assets/img/logo-visa.png";
import Elo from "../assets/img/logo-elo.png";
import Hipercard from "../assets/img/hipercard.png";
import Confirmacao from "./PopUpConfirmacao.jsx";
import { useState } from "react";

function CartoesCard(props) {
    const nomeUsuario = localStorage.getItem("nome");
    // const [loading, setLoading] = useState(false);
    const [showModal, setShowModal] = useState(false);
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

    // Encontra o objeto de cor correspondente ao idCor
    const corSelecionada = corCartao.find(cor => cor.id === props.props.idCor);
    const bandeiraSelecionada = bandeiraCartao.find(bandeira => bandeira.opcao === props.props.bandeira);


    return (
        <div className="card-credit">
            <div className="cartao" style={{ backgroundColor: corSelecionada.codigo, borderRadius: "20px" }}>
                <div className="tipo-cartao">
                    <div className="tipo">Crédito</div>
                    <div className="bandeira"><img src={bandeiraSelecionada.codigo} alt="" /></div>
                    <div className="nome-usuario">{nomeUsuario}</div>
                </div>
            </div>
            <div className="informacoes-cartao">
                <h2>{props.props.apelido}
                    <button onClick={() => setShowModal(true)}>
                        <span className="material-symbols-outlined deletar-cartao">delete</span>
                        </button></h2>
                <div className="informacoes">
                    <div>Limite:<span>R$ {props.props.limite}</span></div>
                    <div>Venci.: <span>{props.props.vencimento}</span></div>
                    <div>
                        <h5>{props.props.porcentagemUtilizado > 100 ? "100" : props.props.porcentagemUtilizado}% utilizado</h5>
                        <div className="progresso">
                            <div className="barra-progresso" style={{ width: `${props.props.porcentagemUtilizado > 100 ? "100%" : props.props.porcentagemUtilizado}%` }}></div>
                        </div>
                    </div>
                </div>
            </div>
            <Confirmacao isOpen={showModal} setModalOpen={() => { setShowModal(!showModal) }} idCartao={props.props.idCartao}/>
        </div>
    );
}

export default CartoesCard;