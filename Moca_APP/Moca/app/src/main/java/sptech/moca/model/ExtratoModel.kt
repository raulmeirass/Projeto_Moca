package sptech.moca.model

data class ExtratoModel(
    val categoria: String,
    val data: String,
    val descricao: String,
    val idDespesa: Long,
    val idReceita: Long,
    val situacao: String,
    val valor: Double
)
