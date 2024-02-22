import api from '../api.js';
import { useState } from "react";
import { FaSpinner } from "react-icons/fa";
import { useNavigate } from 'react-router';

function PopUpPorquinho({ isOpen, setModalOpen }) {
    const idUsuario = localStorage.getItem("id");
    const [clicou, setClicou] = useState(false);
    const [nomeMeta, setNomeMeta] = useState('');
    const [valorMeta, setValorMeta] = useState(0);
    const navigate = useNavigate();
    const styles = {
        esconder: {
            display: 'none'
        },
        mostrar: {
            display: 'block'
        }
    };
    const [alertNome, setAlertNome] = useState('');
    const [alertValor, setAlertValor] = useState('');
    const categoriasPorquinho = [
        { id: 1, nome: 'Viagem' },
        { id: 2, nome: 'Estudos' },
        { id: 3, nome: 'Reserva de emergência' },
        { id: 4, nome: 'Casa' },
        { id: 5, nome: 'Carro' },
        { id: 6, nome: 'Casamento' },
        { id: 7, nome: 'Outros' },
    ];
    const [categoria, setCategoria] = useState();

    function requisicao() {

        setAlertNome(nomeMeta.trim() == '' ? 'Digite o nome da meta' : '');
        setAlertValor(valorMeta <= 0 ? 'Informe um valor válido' : '');

        if (valorMeta > 0 && nomeMeta.trim() !== '') {

            setClicou(true);
            api.post(`porquinhos/`, {
                nome: nomeMeta,
                valorFinal: valorMeta,
                valorAtual: 0,
                isConcluido: false,
                idCliente: idUsuario,
                concluido: false,
                idIcone: categoria
            }).then((response) => {
                console.log(response);
                window.location.href = '/dashboard/porquinho';
            }).catch((err) => {
                console.error(err);
                setClicou(false);
            });

        }
    }

    if (isOpen) {
        return (
            <div id="demo-modal" className="modal">
                <div className="modal__content">
                    <h1>Novo Porquinho</h1>

                    <div className="input-box">
                        <label className="input-label">Nome da meta</label>
                        <input className="input" type="text" onChange={(event) => setNomeMeta(event.target.value)} />
                        <small>{alertNome}</small>
                    </div>

                    <div className="input-box">
                        <label className="input-label">Valor final da Meta</label>
                        <input placeholder="R$0,00" className="input" type="number" onChange={(event) => setValorMeta(event.target.value)} />
                        <small>{alertValor}</small>
                    </div>

                    <div className="input-box">
                        <label className="input-label">Icone</label>
                        <select id="banco" className="selecao" onChange={(event) => setCategoria(event.target.value)}>
                            <option value="0">-- Selecione --</option>
                            {categoriasPorquinho.map((categoria) => {
                                return (
                                    <option key={categoria.id} value={categoria.id}>{categoria.nome}</option>
                                );
                            })}
                        </select>
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

export default PopUpPorquinho;