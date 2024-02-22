import { useState } from "react";

function PaginacaoMesesInput(props) {

    // Atributos
    const dataAtual = new Date();
    const mesPadrao = String(dataAtual.getMonth() + 1).padStart(2, '0'); // Ex: '05'
    const anoPadrao = String(dataAtual.getFullYear()); // Ex: '2023'

    const [mes, setMes] = useState(mesPadrao);
    const [ano, setAno] = useState(anoPadrao);

    const handleInputChange = (event) => {
        const value = event.target.value;
        const novoMes = value.substring(5, 7);
        const novoAno = value.substring(0, 4);
        setMes(value.substring(5, 7));
        setAno(value.substring(0, 4));
        props.setMesAno(novoMes, novoAno);
    };


    // Return do HTML
    return (
        <div className="input-paginacao">
            <input type="month" onChange={handleInputChange} defaultValue={`${ano}-${mes}`} />
        </div>
    );
}

export default PaginacaoMesesInput;