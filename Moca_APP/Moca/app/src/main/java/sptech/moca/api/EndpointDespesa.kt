package sptech.moca.api

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import sptech.moca.model.DespesaModel

interface EndpointDespesa {

    // Requsição das informações da DESPESA
    @Headers("Content-Type: application/json")
    @GET("despesas/{idUsuario}/{mes}/{ano}")
    fun getInformationsExpence(
        @Path("idUsuario") idUsuario: Long,
        @Path("mes") mes: Int,
        @Path("ano") ano: Int
    ): Call<List<DespesaModel>>

    // Despesa
    @Headers("Content-Type: application/json")
    @POST("despesas/")
    fun postCadastrarDespesa(@Body requestBody: RequestBody): Call<DespesaModel>


    // Despesa Fixa
    @Headers("Content-Type: application/json")
    @POST("despesas/fixa")
    fun postCadastrarDespesaFixa(@Body requestBody: RequestBody): Call<DespesaModel>

    // Despesa Fixa ** verificar se tem cartão ** ai vai ser outro request body


    // Despesa Parcelada
    @Headers("Content-Type: application/json")
    @POST("despesas/parcelada")
    fun postCadastrarDespesaParcelada(@Body requestBody: RequestBody): Call<DespesaModel>

    //Pagar despesa
    @PATCH("despesas/{idDespesa}")
    fun pagarDespesa(@Path("idDespesa") idDespesa: Long): Call<DespesaModel>

    @Headers("Content-Type: application/json")
    @PATCH("despesas/{idDespesa}")
    fun editarDespesa(
        @Path("idDespesa") idDespesa: Long,
        @Body requestBody: RequestBody
    ): Call<DespesaModel>

    @DELETE("despesas/{idDespesa}")
    fun deletarDespesa(@Path("idDespesa") idDespesa: Long): Call<DespesaModel>
}