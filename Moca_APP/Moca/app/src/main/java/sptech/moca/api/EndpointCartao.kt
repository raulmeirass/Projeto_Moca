package sptech.moca.api

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import sptech.moca.model.CartaoModel

interface EndpointCartao {

    @GET("cartoes/{idUsuario}/{mes}/{ano}")
    fun getCartoes(
        @Path("idUsuario") idUsuario: Long,
        @Path("mes") mes: Int,
        @Path("ano") ano: Int
    ) : Call<CartoesResponse>

    @Headers("Content-Type: application/json")
    @POST("cartoes/")
    fun cadastrarCartao(@Body requestBody: RequestBody) : Call<CartaoModel>

    @DELETE("cartoes/{idCartao}")
    fun deletarCartao(@Path("idCartao") idCartao: Long) : Call<CartaoModel>

}

data class CartoesResponse(
    val cartoes: List<CartaoModel>
)
