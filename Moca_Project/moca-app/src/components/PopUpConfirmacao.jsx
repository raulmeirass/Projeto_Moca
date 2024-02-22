import { FaSpinner } from "react-icons/fa";
import api from "../api";
import { useState } from "react";

function Confirmacao({ isOpen, setModalOpen, idCartao }) {

    const styles = {
        esconder: {
            display: 'none'
        },
        mostrar: {
            display: 'block'
        }
    };
    const [clicou, setClicou] = useState(false);


    function confirmarDelete() {

        setClicou(true);
        api.delete(`cartoes/${idCartao}`).then((response) => {
            console.log(response);
            window.location.href = '/dashboard/cartoes';
        }).catch((err) => {
            console.error(err);
            setClicou(false);
        });
    }

    if (isOpen) {
        return (
            <div id="demo-modal" className="modal">
                <div className="modal__content">
                    <h1 style={{ textAlign: 'center' }}>Deseja excluir o cartão?</h1>
                    <div className="botoes-popup">
                        <div><FaSpinner className="spinner" style={clicou ? styles.mostrar : styles.esconder} /></div>
                        <button onClick={confirmarDelete} className="confirme-popup" style={clicou ? styles.esconder : styles.mostrar}>Sim</button>
                        <button className="nega-popup" onClick={setModalOpen}>Não</button>
                    </div>

                    <span onClick={setModalOpen} className="modal__close">&times;</span>
                </div>
            </div>
        );
    }
    return null;
}

export default Confirmacao;