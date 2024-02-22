package sptech.moca.model

data class HomeInformationsModel(
    val saldo: Double,
    val receita: Double,
    val despesas: Double,
    val despesaCartao: Double,
    val cartoes: List<CartaoModel>,
    val graficoReceitas: GraficoReceitasModel,
    val graficoDespesas: GraficoDespesasModel,
    val porquinhos: List<PorquinhoModel>
)
