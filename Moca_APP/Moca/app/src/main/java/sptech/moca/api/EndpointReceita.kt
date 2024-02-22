package sptech.moca.api

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import sptech.moca.model.ReceitaModel

interface EndpointReceita {

    // Requsição das informações da RECEITAS
    @Headers("Content-Type: application/json")
    @GET("receitas/{idUsuario}/{mes}/{ano}")
    fun getInformacoesReceita(
        @Path("idUsuario") idUsuario: Long,
        @Path("mes") mes: Int,
        @Path("ano") ano: Int
    ): Call<List<ReceitaModel>>


    // Cadastrar receitas
    @Headers("Content-Type: application/json")
    @POST("receitas/")
    fun postCadastrarReceita(@Body requestBody: RequestBody): Call<ReceitaModel>

    @Headers("Content-Type: application/json")
    @PATCH("receitas/{idReceita}")
    fun editarReceita(
        @Path("idReceita") idReceita: Long,
        @Body requestBody: RequestBody
    ): Call<ReceitaModel>
}