package sptech.moca.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Response
import sptech.moca.R
import sptech.moca.activity.CadastrarPorquinhoActivity
import sptech.moca.adapter.PorquinhoAdapter
import sptech.moca.api.EndpointPorquinho
import sptech.moca.databinding.ActivityPorquinhoBinding
import sptech.moca.model.PorquinhoModel
import sptech.moca.util.NetworkUtils

class PorquinhoFragment : Fragment() {

    private var _binding: ActivityPorquinhoBinding? = null
    private val binding get() = _binding!!

    private val porquinhosList = mutableListOf<PorquinhoModel>()
    private lateinit var adaptador: PorquinhoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityPorquinhoBinding.inflate(inflater, container, false)
        val view = binding.root

        exibirPorquinhos()

        binding.criarPorquinhoUsuario.setOnClickListener{
            val intent = Intent(requireContext(), CadastrarPorquinhoActivity::class.java)
            startActivity(intent)
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewPorquinho)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adaptador = PorquinhoAdapter(porquinhosList)
        recyclerView.adapter = adaptador

//        binding.recyclerViewPorquinho.adapter = adaptador



        // Inflar o layout do fragmento aqui
        return view
    }

    private fun showToast(message: String) {
        context?.let {
            Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun exibirPorquinhos(){
        val retrofitClient = NetworkUtils.getRetrofitInstance()
        val endpoint = retrofitClient.create(EndpointPorquinho::class.java)
        val sharedPreferences =
            requireActivity().getSharedPreferences("DadosUsuario", Context.MODE_PRIVATE)
        val idUsuario = sharedPreferences.getLong("idUsuario", -1)

        val callback = endpoint.getPorquinhos(idUsuario)

        callback.enqueue(object : retrofit2.Callback<List<PorquinhoModel>>{
            override fun onResponse(
                call: Call<List<PorquinhoModel>>,
                response: Response<List<PorquinhoModel>>
            ) {
                if (response.isSuccessful) {
                    val listaDeCartoes: List<PorquinhoModel>? = response.body()
                    // Sucesso, imprima o corpo da resposta
                    println("Resposta bem-sucedida: ${listaDeCartoes}")
                    println("Deu certo o porquinho!")
                    porquinhosList.clear()
                    porquinhosList.addAll(listaDeCartoes!!)
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

            override fun onFailure(call: Call<List<PorquinhoModel>>, t: Throwable) {
                // Lidar com o erro
                println("Falha na requisição: ${t.message}")
                showToast("Falha ao conectar ao servidor. Verifique sua conexão com a Internet.")
            }

        })
    }
}