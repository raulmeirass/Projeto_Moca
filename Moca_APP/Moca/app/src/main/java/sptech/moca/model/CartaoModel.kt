package sptech.moca.model

data class CartaoModel(
    val apelido: String,
    val bandeira: String,
    val idCartao: Long,
    val idCor: Long,
    val limite: Double,
    val porcentagemUtilizado: Double,
    val utilizado: Double,
    val vencimento: String
)

