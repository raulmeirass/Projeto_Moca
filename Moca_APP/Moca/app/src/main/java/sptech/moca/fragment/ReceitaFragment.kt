package sptech.moca.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sptech.moca.R
import sptech.moca.activity.CadastrarReceitas
import sptech.moca.activity.LoginActivity
import sptech.moca.adapter.ReceitaAdapter
import sptech.moca.api.EndpointHome
import sptech.moca.api.EndpointReceita
import sptech.moca.databinding.ActivityReceitaBinding
import sptech.moca.model.HomeInformationsModel
import sptech.moca.model.ReceitaModel
import sptech.moca.util.NetworkUtils
import java.util.Calendar

class ReceitaFragment : Fragment() {
    private var _binding: ActivityReceitaBinding? = null
    private val binding get() = _binding!!

    private val receitaList = mutableListOf<ReceitaModel>()
    private lateinit var adaptador: ReceitaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityReceitaBinding.inflate(inflater, container, false)
        val view = binding.root

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adaptador = ReceitaAdapter(receitaList)
        recyclerView.adapter = adaptador

        binding.btnIrCadastrarReceita.setOnClickListener {
            val intent = Intent(requireContext(), CadastrarReceitas::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun dashboardRequest() {
        val retrofitClient = NetworkUtils.getRetrofitInstance()
        val endpoint = retrofitClient.create(EndpointHome::class.java)
        val sharedPreferences =
            requireActivity().getSharedPreferences("DadosUsuario", Context.MODE_PRIVATE)
        val idUsuario = sharedPreferences.getLong("idUsuario", 0L)
        val calendar = Calendar.getInstance()
        val ano = calendar.get(Calendar.YEAR)
        val posicaoSpinner = binding.spinnerMeses.selectedItemPosition + 1
        val callback = endpoint.getInformations(idUsuario, calendar.get(Calendar.MONTH) + 1, ano)

        callback.enqueue(object : Callback<HomeInformationsModel> {
            override fun onResponse(
                call: Call<HomeInformationsModel>,
                response: Response<HomeInformationsModel>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        // Valores
                        binding.receitaUsuarioRecetas.text = String.format("R$ %.2f", body.receita)
                        // Após receber os dados da dashboard, faça a chamada para receitaRequest
                    }
                }
            }

            override fun onFailure(call: Call<HomeInformationsModel>, t: Throwable) {
                // Lidar com o erro
                Log.e("ReceitaFragment", "Erro na requisição: ${t.message}", t)
            }
        })
    }

    private fun receitaRequest() {
        val retrofitClient = NetworkUtils.getRetrofitInstance()
        val endpoint = retrofitClient.create(EndpointReceita::class.java)
        val sharedPreferences =
            requireActivity().getSharedPreferences("DadosUsuario", Context.MODE_PRIVATE)
        val idUsuario = sharedPreferences.getLong("idUsuario", 0L)
        val calendar = Calendar.getInstance()
        val ano = calendar.get(Calendar.YEAR)
        val posicaoSpinner = binding.spinnerMeses.selectedItemPosition + 1
        val callback =
            endpoint.getInformacoesReceita(idUsuario, calendar.get(Calendar.MONTH) + 1, ano)

        callback.enqueue(object : Callback<List<ReceitaModel>> {
            override fun onResponse(
                call: Call<List<ReceitaModel>>,
                response: Response<List<ReceitaModel>>
            ) {
                if (response.isSuccessful) {
                    val receitas = response.body()
                    if (receitas != null) {
                        receitaList.addAll(receitas)
                        // Notifique o adaptador sobre a mudança nos dados
                        adaptador.notifyDataSetChanged()
                        println(receitas)
                        // Adicione a verificação de nulo aqui
                        Log.d("ReceitaFragment", "Resposta bem-sucedida $receitas")
                    }
                }
            }

            override fun onFailure(call: Call<List<ReceitaModel>>, t: Throwable) {
                // Lidar com o erro
                Log.e("ReceitaFragment", "Erro na requisição: ${t.message}", t)
            }
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dashboardRequest()
        receitaRequest()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
