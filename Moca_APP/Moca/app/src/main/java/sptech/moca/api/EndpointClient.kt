package sptech.moca.api

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import sptech.moca.model.UserModel

interface EndpointClient {

    // Requisição de CADASTRO
    @Headers("Content-Type: application/json")
    @POST("usuarios/cadastrar")
    fun registerUser(@Body requestBody: RequestBody) : Call<UserModel>

    // Requisição de LOGIN
    @Headers("Content-Type: application/json")
    @POST("usuarios/login")
    fun authenticate(@Body requestBody: RequestBody) : Call<UserModel>

}