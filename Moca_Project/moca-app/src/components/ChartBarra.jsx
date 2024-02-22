import { useState } from "react";
import { useEffect } from "react";
import { useNavigate } from "react-router";
import { Bar, BarChart, ResponsiveContainer, Tooltip, XAxis } from "recharts";

function ChartBarra(props) {

    // Atributos
    const [receita, setReceita] = useState();
    const [despesa, setDespesa] = useState();
    const navigate = useNavigate();
    const data = [
        { name: 'Receita', valor: receita, fill: "#63B967" },
        { name: 'Despesa', valor: despesa, fill: "#E92121" },
    ];


    // useEffect que executa quando o valor do props muda
    useEffect(() => {
        setReceita(props.receita);
        setDespesa(props.despesa);
    }, [props]); // Adicionando props como dependências



    // Caso nao tenha nenhuma receita ou despesa com o mes escolhido
    // ele retorna para o usuario um botão para adicionar
    if (props.receita === 0 && props.despesa === 0) {
        return (
            <div style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
                <h4>Ops, parece que você não possui nada cadastrado!</h4>
                <button className="botaoGrafico" style={{ margin: "10px 0" }}
                    onClick={() => navigate('/dashboard/receita')}>
                    Adicionar Receita
                </button>
                <button className="botaoGrafico"
                    onClick={() => navigate('/dashboard/despesa')}>
                    Adicionar Despesa
                </button>
            </div>
        );
    }



    // Return do gráfico de despesa e receita
    // gráfico de barra na vertical
    return (
        <ResponsiveContainer width={250} height={200}>
            <BarChart
                data={data}
                margin={{
                    top: 5,
                    right: 40,
                    left: 40,
                    bottom: 5,
                }}
            >
                {/* <CartesianGrid strokeDasharray="3 3" /> */}
                <XAxis dataKey="name" />
                {/* <YAxis /> */}
                <Tooltip />
                {/* <Legend /> */}
                <Bar dataKey="valor" fill="#8884d8" />
                {/* <Bar dataKey="valor" fill="#82ca9d" /> */}
            </BarChart>
        </ResponsiveContainer>
    );
}

export default ChartBarra;