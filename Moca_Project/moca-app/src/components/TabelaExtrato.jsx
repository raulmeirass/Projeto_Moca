import { format } from "date-fns";

function LinhaExtrato(props) {

    // Atributos
    const dataFormatada = format(new Date(props.props.data), 'dd/MM/yyyy');

    // Return do HTML
    return (
        <tr>
            <td data-label="Situação">
                {props.props.situacao === 'Recebida' ?
                    <span id="positivo" className="material-symbols-outlined">
                        done
                    </span> :
                    <span id="negativo" className="material-symbols-outlined">
                        close
                    </span>}
            </td>
            <td data-label="Data">{dataFormatada}</td>
            <td data-label="Descrição">{props.props.descricao}</td>
            <td data-label="Categoria">{props.props.categoria}</td>
            <td data-label="Valor">R$ {props.props.valor.toFixed(2).replace('.', ',')}</td>
        </tr>
    );
}

export default LinhaExtrato;