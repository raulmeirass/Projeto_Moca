package sptech.moca.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class UserModel (

//    Criando o retorno do Json
    @SerializedName("id")
    var id : Long,
    @SerializedName("nome")
    var nome : String,
    @SerializedName("email")
    var email : String,
    @SerializedName("senha")
    var senha : String,
    @SerializedName("idPerfil")
    var idPerfil : Long,
    @SerializedName("ultimoAcesso")
    var ultimoAcesso : Date,
    @SerializedName("telefone")
    var telefone : String,
    @SerializedName("enviaEmail")
    var enviaEmail : Boolean,
    @SerializedName("enviaSms")
    var enviaSms : Boolean,
    @SerializedName("token")
    var token : String

)