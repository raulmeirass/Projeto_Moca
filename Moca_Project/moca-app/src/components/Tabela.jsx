import { useState } from "react";
import api from "../api";
import { useNavigate } from "react-router";


function LinhaTabela(props) {

    // Atributos
    // Constants para recuperar dados do localStorage
    const [isEditando, setIsEditando] = useState(false);
    const [data, setData] = useState();
    const [descricao, setDescricao] = useState();
    const [valor, setValor] = useState();
    const navigate = useNavigate();

    function pagarDespesa() {
        api.patch(`despesas/pagar/${props.despesa.idDespesa}`).then((response) => {
            console.log(response.status);
            props.atualizar();
        }).catch((err) => {
            console.error(err);
        });
    }

    function salvarEdicao() {

        if (window.location.pathname === '/dashboard/despesa') {
            api.patch(`despesas/${props.despesa.idDespesa}`, {
                descricao: descricao,
                valor: valor !== undefined ? valor : props.despesa.valor,
                data: data
            }).then((response) => {
                console.log(response);
                props.atualizar();
            }).catch((err) => {
                console.log(err);
            });
        } else if (window.location.pathname === '/dashboard/receita') {
            api.patch(`receitas/${props.receita.idReceita}`, {
                descricao: descricao,
                valor: valor !== undefined ? valor : props.receita.valor,
                data: data
            }).then((response) => {
                console.log(response);
                navigate('/dashboard/receita');
                props.atualizar();
            }).catch((err) => {
                console.log(err);
            })
        }
    }

    function deletar() {

        if (window.location.pathname === '/dashboard/despesa') {
            api.delete(`despesas/${props.despesa.idDespesa}`).then((response) => {
                console.log(response);
                props.atualizar();
            });
        } else if (window.location.pathname === '/dashboard/receita') {
            api.delete(`receitas/${props.receita.idReceita}`).then((response) => {
                console.log(response);
                props.atualizar();
            })
        }
    }

    return (
        <tbody>
            <tr>
                <td data-label="Situação">
                    {window.location.pathname === '/dashboard/despesa' && props.despesa.paid === false ?
                        <span id="negativo" title="Clicar para pagar" className="material-symbols-outlined" onClick={() => pagarDespesa()}>close</span>
                        :
                        <span id="positivo" title="Pago" className="material-symbols-outlined">done</span>
                    }
                    {window.location.pathname === '/dashboard/receita' ??
                        <span id="positivo" title="Recebido" className="material-symbols-outlined">done</span>
                    }
                </td>
                <td data-label="Data">
                    <input type="date"
                        disabled={isEditando === false}
                        defaultValue={window.location.pathname === '/dashboard/despesa' ? props.despesa.data : props.receita.data}
                        onChange={(event) => setData(event.target.value)} />
                    {/* {window.location.pathname === '/dashboard/despesa' ? props.despesa.data : props.receita.data} */}
                </td>
                <td data-label="Descrição">
                    <input type="text"
                        disabled={isEditando === false}
                        defaultValue={window.location.pathname === '/dashboard/despesa' ? props.despesa.descricao : props.receita.descricao}
                        onChange={(event) => setDescricao(event.target.value)} />
                    {/* {window.location.pathname === '/dashboard/despesa' ? props.despesa.descricao : props.receita.descricao} */}
                </td>
                <td data-label="Categoria">
                    {window.location.pathname === '/dashboard/despesa' ? props.despesa.idTipoDespesa : props.receita.idTipoReceita}
                </td>
                <td data-label="Valor">
                    <input type="number"
                        disabled={isEditando === false}
                        defaultValue={window.location.pathname === '/dashboard/despesa' ? props.despesa.valor : props.receita.valor}
                        onChange={(event) => setValor(event.target.value)} />
                    {/* {window.location.pathname === '/dashboard/despesa' ? props.despesa.valor : props.receita.valor} */}
                </td>
                <td data-label="Ações" className="acoes">
                    <button onClick={() => setIsEditando(!isEditando)}>
                        {isEditando ?
                            <span className="material-symbols-outlined salvar" onClick={() => salvarEdicao()}>save</span>
                            :
                            <span className="material-symbols-outlined editar">edit</span>}
                    </button>
                    <button onClick={() => deletar()}>
                        <span className="material-symbols-outlined deletar">delete</span>
                    </button>
                </td>
            </tr>
        </tbody>
    );
}

export default LinhaTabela;