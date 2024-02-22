package sptech.moca.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import sptech.moca.model.ConfiguracoesModel

interface EndpointConfiguracoes {

    @Headers("Content-Type: application/json")
    @GET("usuarios/config/{idUsuario}")
    fun getInformationExtract(
        @Path("idUsuario") idUsuario: Long,
    ): Call<ConfiguracoesModel>
}