package sptech.moca.model

data class ConfiguracoesModel(
    val id: Long,
    val nome: String,
    val email: String,
    val idTipoPefil: Long,
    val telefone: String,
    val enviaEmail: Boolean,
    val enviaSms: Boolean
)
