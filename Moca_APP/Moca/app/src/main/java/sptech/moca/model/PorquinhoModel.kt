package sptech.moca.model

data class PorquinhoModel(
    val idPorquinho: Long,
    val nome: String,
    val valorFinal: Double,
    val valorAtual: Double,
    val isConcluido: Boolean,
    val idCliente: Long,
    val idIcone: Int
)
