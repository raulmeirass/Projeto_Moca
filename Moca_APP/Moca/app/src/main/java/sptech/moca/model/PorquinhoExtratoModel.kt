package sptech.moca.model

data class PorquinhoExtratoModel(
    val idPorquinho: Long,
    val categoria: String,
    val data: String,
    val descricao: String,
    val saque: Boolean,
    val valor: Double
)
