import React, { useState } from 'react';

function Meses(props) {
  const dataAtual = new Date();
  // const anoAtual = dataAtual.getFullYear();

  const meses = [
    'Janeiro',
    'Fevereiro',
    'Março',
    'Abril',
    'Maio',
    'Junho',
    'Julho',
    'Agosto',
    'Setembro',
    'Outubro',
    'Novembro',
    'Dezembro'
  ];

  const [mesAtual, setMesAtual] = useState(dataAtual.getMonth());

  const exibirMes = () => {
    return meses[mesAtual];
  };

  const avancar = () => {
    if (mesAtual < meses.length - 1) {
      setMesAtual(mesAtual + 1);
      props.setMesAtual(mesAtual + 1); // atualiza o estado no componente pai
    }
  };

  const voltar = () => {
    if (mesAtual > 0) {
      setMesAtual(mesAtual - 1);
      props.setMesAtual(mesAtual - 1); // atualiza o estado no componente pai
    }
  };

  return (
    <div>
      <h2 className='heading'>
        <button className='setaPagination' onClick={() => voltar()}><span className="material-symbols-outlined">chevron_left</span></button>
        <p>{exibirMes()}</p>
        <button className='setaPagination' onClick={() => avancar()}><span className="material-symbols-outlined">chevron_right</span></button>
      </h2>
    </div>
  );
}

export default Meses;