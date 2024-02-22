import { useState } from "react";
import { useEffect } from "react";
import { useNavigate } from "react-router";
import { Cell, Pie, PieChart, ResponsiveContainer, Tooltip } from "recharts";

function ChartPizza(props) {

    // Atributos
    const [graficoDespesa, setGraficoDespesa] = useState([props.props]);
    const navigate = useNavigate();
    const colors = ["#0D2535", "#5388D8", "#98A8DF", "#00D1FF", "#63B967", "#A921E9"]; // Array de cores
    const styleBotao = {
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        width: "100%",
        height: "100px"
    }


    // useEffect que executa quando o valor do props muda
    useEffect(() => {
        setGraficoDespesa(props.props);
    }, [props]);



    // Caso nao tenha nenhuma receita ou despesa com o mes escolhido
    // ele retorna para o usuario um botão para adicionar
    if (graficoDespesa.length === 0) {
        return <div style={styleBotao}>
            <button className="botaoGrafico"
                onClick={() => props.mensagem === "receita" ?
                    navigate('/dashboard/receita') : navigate('/dashboard/despesa')}>
                Adicionar {props.mensagem}
            </button>
        </div>;
    }



    // Return do gráfico de pizza para o card
    return (
        <ResponsiveContainer width={380} height={200}>
            <PieChart>
                {graficoDespesa && graficoDespesa.length > 0 ? (
                    <Pie
                        dataKey="porcentagem"
                        isAnimationActive={true}
                        data={graficoDespesa}
                        // cx={200}
                        // cy={200}
                        // outerRadius={80}
                        label={({ descricao }) => `${descricao}`}
                    >
                        {graficoDespesa.map((item, index) => (
                            <Cell
                                key={`cell-${index}`}
                                fill={colors[index % colors.length]}
                            />
                        ))}
                    </Pie>
                ) : null}
                <Tooltip />
                {/* <Legend /> */}
            </PieChart>
        </ResponsiveContainer>
    );

}

export default ChartPizza;