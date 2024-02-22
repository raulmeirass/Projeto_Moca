package sptech.moca.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import sptech.moca.model.CartaoModel
import sptech.moca.model.ExtratoModel
import sptech.moca.model.HomeInformationsModel

interface EndpointExtrato {

    // Requsição das informações do Extrato
    @Headers("Content-Type: application/json")
    @GET("extrato/{idUsuario}/{mes}/{ano}")
    fun getInformationExtract(
        @Path("idUsuario") idUsuario: Long,
        @Path("mes") mes: Int,
        @Path("ano") ano: Int
    ): Call<ExtratoResponse>
}

data class ExtratoResponse(
    val items: List<ExtratoModel>
)