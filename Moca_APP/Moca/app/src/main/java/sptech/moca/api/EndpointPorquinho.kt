package sptech.moca.api

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import sptech.moca.model.CartaoModel
import sptech.moca.model.PorquinhoExtratoModel
import sptech.moca.model.PorquinhoModel

interface EndpointPorquinho {

    @GET("porquinhos/{idUsuario}")
    fun getPorquinhos(@Path("idUsuario") idUsuario: Long): Call<List<PorquinhoModel>>

    @GET("porquinhos/mostrarPorcentagem/{idUsuario}/{idPorquinho}")
    fun mostrarPorcentagem(
        @Path("idUsuario") idUsuario: Long,
        @Path("idPorquinho") idPorquinho: Long
    ): Call<Double>

    @GET("porquinhos/{idUsuario}/{idPorquinho}")
    fun mostrarPorquinhoEscolhido(
        @Path("idUsuario") idUsuario: Long,
        @Path("idPorquinho") idPorquinho: Long
    ): Call<PorquinhoModel>

    @GET("porquinhos/historico/{idPorquinho}")
    fun mostrarExtratoPorquinho(@Path("idPorquinho") idPorquinho: Long): Call<PorquinhoExtratoResponse>

    @Headers("Content-Type: application/json")
    @POST("porquinhos/")
    fun cadastrarPorquinho(@Body requestBody: RequestBody): Call<PorquinhoModel>

    @Headers("Content-Type: application/json")
    @PUT("porquinhos/adicionarValor/{idUsuario}/{idPorquinho}/{valor}")
    fun adicionarValor(
        @Path("idUsuario") idUsuario: Long,
        @Path("idPorquinho") idPorquinho: Long,
        @Path("valor") valor: Double
    ): Call<PorquinhoModel>

    @Headers("Content-Type: application/json")
    @PUT("porquinhos/retirarValor/{idUsuario}/{idPorquinho}/{valor}")
    fun retirarValor(
        @Path("idUsuario") idUsuario: Long,
        @Path("idPorquinho") idPorquinho: Long,
        @Path("valor") valor: Double
    ): Call<PorquinhoModel>
}

data class PorquinhoExtratoResponse(
    val items: List<PorquinhoExtratoModel>
)