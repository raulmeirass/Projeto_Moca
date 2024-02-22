package sptech.moca.model

data class ReceitaModel(
    val data: String,
    val descricao: String,
    val idCliente: Long,
    val idReceita: Long,
    val idTipoReceita: String,
    val valor: Double
)
