package sptech.moca.activity

import android.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response
import sptech.moca.api.EndpointCartao
import sptech.moca.databinding.ActivityCadastrarCartaoBinding
import sptech.moca.databinding.ActivityCadastrarDespesaBinding
import sptech.moca.fragment.CartoesFragment
import sptech.moca.model.CartaoModel
import sptech.moca.util.NetworkUtils

class CadastrarCartoesActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityCadastrarCartaoBinding.inflate(layoutInflater)
    }

    val opcaoTipoCartao = listOf(
        "-Selecione-",
        "Débito",
        "Crédito"
    )

    val bandeiraCartao = listOf(
        "-Selecione-",
        "Visa",
        "Elo",
        "Mastercard",
        "Hipercard"
    )

    val corCartao = listOf(
        "-Selecione-",
        "Azul Royal",
        "Verde Esmeralda",
        "Amarelo Sol",
        "Vermelho Cereja",
        "Roxo Violeta",
        "Laranja Coral",
        "Cinza Prata"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val adapterTipo =
            ArrayAdapter(this, R.layout.simple_spinner_item, opcaoTipoCartao)
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTipoCartao.adapter = adapterTipo

        val adapterBandeira =
            ArrayAdapter(this, R.layout.simple_spinner_item, bandeiraCartao)
        adapterBandeira.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.bandeiraCartao.adapter = adapterBandeira


        val adapterCor =
            ArrayAdapter(this, R.layout.simple_spinner_item, corCartao)
        adapterCor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.corCartao.adapter = adapterCor

        binding.cadastrarCartaoUsuario.setOnClickListener {
            when {
                binding.apelidoCartaoUsuario.text.isBlank() -> {
                    binding.apelidoCartaoUsuario.error = "Preencha todos os campos"
                }

                binding.spinnerTipoCartao.selectedItemPosition == 0 -> {
                    showToast("Selecione um tipo de cartão!")
                }

                binding.inputVencimentoCartao.text.isBlank() -> {
                    binding.inputVencimentoCartao.error = "Preencha todos os campos"
                }

                binding.bandeiraCartao.selectedItemPosition == 0 -> {
                    showToast("Selecione uma bandeira para o cartão!")
                }

                binding.corCartao.selectedItemPosition == 0 -> {
                    showToast("Selecione uma cor para o cartão!")
                }

                binding.apelidoCartaoUsuario.text.isBlank() -> {
                    binding.apelidoCartaoUsuario.error = "Preencha todos os campos"
                }

                else -> {
                    cadastrarCartao()
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun cadastrarCartao() {
        val retrofitClient = NetworkUtils.getRetrofitInstance()
        val endpoint = retrofitClient.create(EndpointCartao::class.java)
        val sharedPreferences =
            this.getSharedPreferences("DadosUsuario", Context.MODE_PRIVATE)
        val idUsuario = sharedPreferences.getLong("idUsuario", -1)

        val jsonPayload =
            "{\"limite\":${binding.limiteCartaoUsuario.text.toString().toDouble()}," +
                    "\"idCliente\":${idUsuario}," +
                    "\"idTipo\":${binding.spinnerTipoCartao.selectedItemPosition}," +
                    "\"idCor\":${binding.corCartao.selectedItemPosition}," +
                    "\"bandeira\": \"${binding.bandeiraCartao.selectedItem.toString()}\"," +
                    "\"apelido\": \"${binding.apelidoCartaoUsuario.text.toString()}\"," +
                    "\"vencimento\": \"${binding.inputVencimentoCartao.text.toString()}\"}"

        // Converta a String em um RequestBody
        val requestBody =
            jsonPayload.toRequestBody("application/json".toMediaTypeOrNull())
        // Faça a chamada à API passando o RequestBody
        val callback = endpoint.cadastrarCartao(requestBody)

        callback.enqueue(object : retrofit2.Callback<CartaoModel> {
            override fun onResponse(call: Call<CartaoModel>, response: Response<CartaoModel>) {
                if (response.isSuccessful) {
                    println("Execução feita com sucesso")
                    val intent = Intent(this@CadastrarCartoesActivity, MainActivity::class.java)
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

            override fun onFailure(call: Call<CartaoModel>, t: Throwable) {
                // Lidar com o erro
                println("Falha na requisição: ${t.message}")
                showToast("Falha ao conectar ao servidor. Verifique sua conexão com a Internet.")
            }

        })
    }
}