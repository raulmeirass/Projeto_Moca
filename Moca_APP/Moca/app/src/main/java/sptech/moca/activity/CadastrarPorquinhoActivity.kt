package sptech.moca.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response
import sptech.moca.R
import sptech.moca.api.EndpointCartao
import sptech.moca.api.EndpointPorquinho
import sptech.moca.databinding.ActivityCadastrarPorquinhoBinding
import sptech.moca.databinding.ActivityPorquinhoBinding
import sptech.moca.model.PorquinhoModel
import sptech.moca.util.NetworkUtils

class CadastrarPorquinhoActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityCadastrarPorquinhoBinding.inflate(layoutInflater)
    }

    val categoriaPorquinho = listOf(
        "-Selecione-",
        "Viagem",
        "Estudos",
        "Reserva de emergência",
        "Casa",
        "Carro",
        "Casamento",
        "Outros"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, categoriaPorquinho)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerIconePorquinho.adapter = adapter

        binding.cadastrarPorquinhoUsuario.setOnClickListener {
            when {
                binding.nomeMeta.text.isBlank() -> {
                    binding.nomeMeta.error = "Preencha todos os campos"
                }

                binding.valorFinalMeta.text.isBlank() || binding.valorFinalMeta.text.toString()
                    .toDouble() <= 0.0 -> {
                    binding.nomeMeta.error = "Preencha todos os campos corretamente"
                }

                binding.spinnerIconePorquinho.selectedItemPosition == 0 -> {
                    showToast("Selecione um tipo de cartão!")
                }
                else -> {
                    cadastrarPorquinho()
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    private fun cadastrarPorquinho() {
        val retrofitClient = NetworkUtils.getRetrofitInstance()
        val endpoint = retrofitClient.create(EndpointPorquinho::class.java)
        val sharedPreferences =
            this.getSharedPreferences("DadosUsuario", Context.MODE_PRIVATE)
        val idUsuario = sharedPreferences.getLong("idUsuario", -1)

        val jsonPayload =
            "{\"nome\":\"${binding.nomeMeta.text.toString()}\"," +
                    "\"valorFinal\":${binding.valorFinalMeta.text.toString().toDouble()}," +
                    "\"valorAtual\":${0}," +
                    "\"isConcluido\": ${false}," +
                    "\"idCliente\":${idUsuario}," +
                    "\"concluido\": ${false}," +
                    "\"idIcone\": ${binding.spinnerIconePorquinho.selectedItemPosition}}"

        // Converta a String em um RequestBody
        val requestBody =
            jsonPayload.toRequestBody("application/json".toMediaTypeOrNull())
        // Faça a chamada à API passando o RequestBody
        val callback = endpoint.cadastrarPorquinho(requestBody)

        callback.enqueue(object : retrofit2.Callback<PorquinhoModel> {
            override fun onResponse(
                call: Call<PorquinhoModel>,
                response: Response<PorquinhoModel>
            ) {
                if (response.isSuccessful) {
                    println("Execução feita com sucesso")
                    val intent = Intent(this@CadastrarPorquinhoActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    // Erro, imprima o corpo da resposta e o código de resposta
                    println(
                        "Erro na resposta: ${response.code()} - ${
                            response.errorBody()?.string()
                        }"
                    )
                    showToast("Erro ao cadastrar receita. Tente novamente.")
                }
            }

            override fun onFailure(call: Call<PorquinhoModel>, t: Throwable) {
                println("Falha na requisição: ${t.message}")
                showToast("Falha ao conectar ao servidor. Verifique sua conexão com a Internet.")
            }

        })
    }
}