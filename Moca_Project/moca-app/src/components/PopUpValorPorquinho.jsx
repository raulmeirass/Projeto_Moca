import { useState } from "react";
import { FaSpinner } from "react-icons/fa";
import api from "../api";
import { useNavigate } from "react-router";

function PopUpValorPorquinho({ isOpen, setModalOpen, mensagem, idPorquinho, valorPorquinho }) {

    // Atributos
    const idUsuario = localStorage.getItem("id");
    const navigate = useNavigate();
    const [clicou, setClicou] = useState(false);
    const [valor, setValor] = useState(0);
    const [data, setData] = useState();
    const styles = {
        esconder: {
            display: 'none'
        },
        mostrar: {
            display: 'block'
        }
    };
    const [alertValor, setAlertValor] = useState('');
    const [alertValorInsuficiente, setAlertValorInsuficiente] = useState('');

    // Função da requisição
    function requisicao() {

        setAlertValor(valor <= 0 ? 'Informe um valor válido' : '');

        if (valor > 0) {
            if (mensagem === "adicionar") {
                api.put(`porquinhos/adicionarValor/${idUsuario}/${idPorquinho}/${valor}`).then((response) => {
                    console.log(response);
                    window.location.href = `/dashboard/porquinho/extrato/${idPorquinho}`;
                }).catch((err) => {
                    console.log(err);
                });
            } else {
                setAlertValorInsuficiente(valorPorquinho < valor ? 'Saldo do porquinho insuficiente' : '');
                if (valorPorquinho >= valor) {
                    api.put(`porquinhos/retirarValor/${idUsuario}/${idPorquinho}/${valor}`).then((response) => {
                        console.log(response);
                        window.location.href = `/dashboard/porquinho/extrato/${idPorquinho}`;
                    }).catch((err) => {
                        console.log(err);
                    });
                }
            }

        }

    }

    // Return do pop up
    if (isOpen) {
        return (
            <div id="demo-modal" className="modal">
                <div className="modal__content">
                    <h1>{mensagem === "adicionar" ? "Adicionar valor" : "Retirar valor"}</h1>

                    <div className="input-box">
                        <label className="input-label">Valor</label>
                        <input placeholder="R$0,00" className="input" type="number" onChange={(event) => setValor(event.target.value)} />
                        <small>{alertValor}</small>
                        <small>{alertValorInsuficiente}</small>
                    </div>

                    {/* <div className="input-box">
                        <label className="input-label">Data</label>
                        <input className="input" type="date" onChange={(event) => setData(event.target.value)} />
                    </div> */}

                    <div className={mensagem === "adicionar" ? "modal__footer modal_footer_receita" : "modal__footer modal_footer_depesa"}>
                        <div><FaSpinner className="spinner" style={clicou ? styles.mostrar : styles.esconder} /></div>
                        <button style={clicou ? styles.esconder : styles.mostrar}
                            onClick={() => requisicao()}>{mensagem === "adicionar" ? "Adicionar" : "Retirar"}</button>
                    </div>

                    <span onClick={setModalOpen} className="modal__close">&times;</span>
                </div>
            </div>
        );
    }
    return null;
}

export default PopUpValorPorquinho;