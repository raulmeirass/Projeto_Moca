package sptech.moca.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import sptech.moca.model.HomeInformationsModel

interface EndpointHome {
    // Requsição das informações da DASHBOARD
    @Headers("Content-Type: application/json")
    @GET("home/{idUsuario}/{mes}/{ano}")
    fun getInformations(
        @Path("idUsuario") idUsuario: Long,
        @Path("mes") mes: Int,
        @Path("ano") ano: Int
    ): Call<HomeInformationsModel>
}