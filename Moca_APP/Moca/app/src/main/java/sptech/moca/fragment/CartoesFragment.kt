package sptech.moca.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Response
import sptech.moca.R
import sptech.moca.activity.CadastrarCartoesActivity
import sptech.moca.activity.MainActivity
import sptech.moca.adapter.CartoesAdapter
import sptech.moca.adapter.DespesaAdapter
import sptech.moca.api.CartoesResponse
import sptech.moca.api.EndpointCartao
import sptech.moca.api.EndpointDespesa
import sptech.moca.databinding.ActivityCartoesBinding
import sptech.moca.databinding.ActivityDashboardBinding
import sptech.moca.model.CartaoModel
import sptech.moca.model.DespesaModel
import sptech.moca.util.NetworkUtils
import java.util.Calendar

class CartoesFragment : Fragment() {

    private var _binding: ActivityCartoesBinding? = null
    private val binding get() = _binding!!

    private val cartoesList = mutableListOf<CartaoModel>()
    private lateinit var adaptador: CartoesAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityCartoesBinding.inflate(inflater, container, false)
        val view = binding.root

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewCartoes)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adaptador = CartoesAdapter(cartoesList)
        recyclerView.adapter = adaptador

        exibirCartoes()

        binding.criarCartaoUsuario.setOnClickListener {
            val intent = Intent(requireContext(), CadastrarCartoesActivity::class.java)
            startActivity(intent)
        }

        // Inflar o layout do fragmento aqui
        return view
    }

    private fun showToast(message: String) {
        context?.let {
            Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
        }
    }


    private fun exibirCartoes() {
        val retrofitClient = NetworkUtils.getRetrofitInstance()
        val endpoint = retrofitClient.create(EndpointCartao::class.java)
        val sharedPreferences =
            requireActivity().getSharedPreferences("DadosUsuario", Context.MODE_PRIVATE)
        val idUsuario = sharedPreferences.getLong("idUsuario", -1)

        val calendar = Calendar.getInstance()
        val ano = calendar.get(Calendar.YEAR)

        val callback = endpoint.getCartoes(idUsuario, calendar.get(Calendar.MONTH) + 1, ano)

        callback.enqueue(object : retrofit2.Callback<CartoesResponse> {
            override fun onResponse(
                call: Call<CartoesResponse>,
                response: Response<CartoesResponse>
            ) {
                if (response.isSuccessful) {
                    // Sucesso, imprima o corpo da resposta
                    val cartoesResponse: CartoesResponse? = response.body()
                    val listaDeCartoes: List<CartaoModel>? = cartoesResponse?.cartoes
                    println("Resposta bem-sucedida: $listaDeCartoes")
                    // Após modificar a lista de dados (ex: dentro de exibirCartoes())
                    cartoesList.clear()
                    cartoesList.addAll(listaDeCartoes!!)
                    adaptador.notifyDataSetChanged()
                    Log.d("CartoesFragment", "Lista de cartões atualizada. Tamanho: ${listaDeCartoes?.size}")
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

            override fun onFailure(call: Call<CartoesResponse>, t: Throwable) {
                // Lidar com o erro
                println("Falha na requisição: ${t.message}")
                showToast("Falha ao conectar ao servidor. Verifique sua conexão com a Internet.")
            }

        })
    }

    fun deletarCartao(idCartao: Long){
        val retrofitClient = NetworkUtils.getRetrofitInstance()
        val endpoint = retrofitClient.create(EndpointCartao::class.java)

        val callback = endpoint.deletarCartao(idCartao)

        callback.enqueue(object : retrofit2.Callback<CartaoModel>{
            override fun onResponse(call: Call<CartaoModel>, response: Response<CartaoModel>) {
                if (response.isSuccessful) {
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
//                    exibirCartoes()
                    println("Cartão excluido com sucesso")
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