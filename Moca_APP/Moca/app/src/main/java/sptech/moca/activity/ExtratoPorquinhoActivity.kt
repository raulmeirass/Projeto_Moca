package sptech.moca.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Response
import sptech.moca.R
import sptech.moca.adapter.ExtratoPorquinhoAdapter
import sptech.moca.adapter.PorquinhoAdapter
import sptech.moca.api.CartoesResponse
import sptech.moca.api.EndpointPorquinho
import sptech.moca.api.PorquinhoExtratoResponse
import sptech.moca.databinding.ActivityExtratoPorquinhoBinding
import sptech.moca.model.CartaoModel
import sptech.moca.model.PorquinhoExtratoModel
import sptech.moca.model.PorquinhoModel
import sptech.moca.util.NetworkUtils

class ExtratoPorquinhoActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityExtratoPorquinhoBinding.inflate(layoutInflater)
    }



    private val porquinhosList = mutableListOf<PorquinhoExtratoModel>()
    private lateinit var adaptador: ExtratoPorquinhoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        exibirHistoricoPorquinho()
        mostrarEscolhido()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewExtratoPorquinho)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adaptador = ExtratoPorquinhoAdapter(porquinhosList)
        recyclerView.adapter = adaptador

        binding.imageViewExtratoPorquinho.setOnClickListener {
            val intent = Intent(this@ExtratoPorquinhoActivity, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnAdicionarValor.setOnClickListener {
            exibirPopup("Adicionar Valor") { valor ->
                // Aqui você pode chamar a função desejada com o valor inserido
                showToast("Adicionando valor: $valor")
            }
        }

        binding.btnRetirarValor.setOnClickListener {
            exibirPopup("Retirar Valor") { valor ->
                // Aqui você pode chamar a função desejada com o valor inserido
                showToast("Retirando valor: $valor")
            }
        }
    }

    private fun exibirPopup(titulo: String, onConfirmar: (Double) -> Unit) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.popup_layout, null)

        val editTextValor = dialogLayout.findViewById<EditText>(R.id.editTextValor)

        builder.setTitle(titulo)
        builder.setView(dialogLayout)
        builder.setPositiveButton("Confirmar") { _, _ ->
            // Chamando a função de callback ao confirmar
            val valor = editTextValor.text.toString().toDoubleOrNull() ?: 0.0

            if (titulo.toString().equals("Adicionar Valor")){
                adicionarValorPorquinho(valor)
            } else {
                retirarValorPorquinho(valor)
            }
            onConfirmar(valor)
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    private fun adicionarValorPorquinho(valor: Double){
        val retrofitClient = NetworkUtils.getRetrofitInstance()
        val endpoint = retrofitClient.create(EndpointPorquinho::class.java)
        val sharedPreferences =getSharedPreferences("DadosUsuario", Context.MODE_PRIVATE)
        val idUsuario = sharedPreferences.getLong("idUsuario", -1)
        // No onCreate da ExtratoPorquinhoActivity
        val sharedPreferences2 = getSharedPreferences("DadosPorquinho", Context.MODE_PRIVATE)
        val porquinhoId = sharedPreferences2.getLong("porquinhoId", -1)

        val callback = endpoint.adicionarValor(idUsuario, porquinhoId, valor)

        callback.enqueue(object : retrofit2.Callback<PorquinhoModel>{
            override fun onResponse(
                call: Call<PorquinhoModel>,
                response: Response<PorquinhoModel>
            ) {
                if (response.isSuccessful) {
                    Log.d("ExtratoPorquinhoActivity", "Deu certp")
                    mostrarEscolhido()
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
                // Lidar com o erro
                exibirHistoricoPorquinho()
                mostrarEscolhido()
            }

        })
    }

    private fun retirarValorPorquinho(valor: Double){

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    private fun exibirHistoricoPorquinho(){
        val retrofitClient = NetworkUtils.getRetrofitInstance()
        val endpoint = retrofitClient.create(EndpointPorquinho::class.java)
        val sharedPreferences =getSharedPreferences("DadosUsuario", Context.MODE_PRIVATE)
        val idUsuario = sharedPreferences.getLong("idUsuario", -1)
        // No onCreate da ExtratoPorquinhoActivity
        val sharedPreferences2 = getSharedPreferences("DadosPorquinho", Context.MODE_PRIVATE)
        val porquinhoId = sharedPreferences2.getLong("porquinhoId", -1)
        println("ID do porquinho recuperado: $porquinhoId")


        val callback = endpoint.mostrarExtratoPorquinho(porquinhoId)

        callback.enqueue(object : retrofit2.Callback<PorquinhoExtratoResponse> {
            override fun onResponse(
                call: Call<PorquinhoExtratoResponse>,
                response: Response<PorquinhoExtratoResponse>
            ) {
                if (response.isSuccessful) {
                    val porquinhoExtratoResponse: PorquinhoExtratoResponse? = response.body()
                    val listaDePorquinhosExtrato: List<PorquinhoExtratoModel>? = porquinhoExtratoResponse?.items
                    // Sucesso, imprima o corpo da resposta
                    println("Resposta bem-sucedida: ${listaDePorquinhosExtrato}")
                    println("Deu certo o historico porquinho!")
                    porquinhosList.clear()
                    porquinhosList.addAll(listaDePorquinhosExtrato!!)
                    adaptador.notifyDataSetChanged()
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

            override fun onFailure(call: Call<PorquinhoExtratoResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun mostrarEscolhido(){
        val retrofitClient = NetworkUtils.getRetrofitInstance()
        val endpoint = retrofitClient.create(EndpointPorquinho::class.java)
        val sharedPreferences =getSharedPreferences("DadosUsuario", Context.MODE_PRIVATE)
        val idUsuario = sharedPreferences.getLong("idUsuario", -1)
        // No onCreate da ExtratoPorquinhoActivity
        val sharedPreferences2 = getSharedPreferences("DadosPorquinho", Context.MODE_PRIVATE)
        val porquinhoId = sharedPreferences2.getLong("porquinhoId", -1)
        println("ID do porquinho recuperado: $porquinhoId")


        val callback = endpoint.mostrarPorquinhoEscolhido(idUsuario,porquinhoId)

        callback.enqueue(object : retrofit2.Callback<PorquinhoModel>{
            override fun onResponse(
                call: Call<PorquinhoModel>,
                response: Response<PorquinhoModel>
            ) {
                if (response.isSuccessful) {
                    binding.receitaExtratoPorquinho.text = response.body()?.valorAtual.toString()
                    binding.despesaExtratoPorquinho.text = response.body()?.valorFinal.toString()
                }
            }

            override fun onFailure(call: Call<PorquinhoModel>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}