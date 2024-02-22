package sptech.moca.fragment

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
import sptech.moca.activity.CadastrarDespesa
import sptech.moca.adapter.DespesaAdapter
import sptech.moca.adapter.ReceitaAdapter
import sptech.moca.api.EndpointDespesa
import sptech.moca.api.EndpointHome
import sptech.moca.api.EndpointReceita
import sptech.moca.databinding.ActivityDespesaBinding
import sptech.moca.databinding.ActivityReceitaBinding
import sptech.moca.model.DespesaModel
import sptech.moca.model.HomeInformationsModel
import sptech.moca.model.ReceitaModel
import sptech.moca.util.NetworkUtils
import java.util.Calendar

class DespesaFragment : Fragment() {
    private var _binding: ActivityDespesaBinding? = null
    private val binding get() = _binding!!

    private val despesaList = mutableListOf<DespesaModel>()
    private lateinit var adaptador: DespesaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityDespesaBinding.inflate(inflater, container, false)
        val view = binding.root

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewDespesa)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adaptador = DespesaAdapter(this.despesaList)
        recyclerView.adapter = adaptador

        dashboardRequest()
        despesaRequest()

        binding.irAdicionarDespesa.setOnClickListener {
            val intent = Intent(requireContext(), CadastrarDespesa::class.java)
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
                        binding.valorDespesa.text = String.format("R$ %.2f", body.despesas)
                        // Após receber os dados da dashboard, faça a chamada para receitaRequest
                    }
                }
            }

            override fun onFailure(call: Call<HomeInformationsModel>, t: Throwable) {
                // Lidar com o erro
            }
        })
    }

    private fun despesaRequest() {
        val retrofitClient = NetworkUtils.getRetrofitInstance()
        val endpoint = retrofitClient.create(EndpointDespesa::class.java)
        val sharedPreferences =
            requireActivity().getSharedPreferences("DadosUsuario", Context.MODE_PRIVATE)
        val idUsuario = sharedPreferences.getLong("idUsuario", 0L)
        val calendar = Calendar.getInstance()
        val ano = calendar.get(Calendar.YEAR)
        val posicaoSpinner = binding.spinnerMeses.selectedItemPosition + 1
        val callback =
            endpoint.getInformationsExpence(idUsuario, calendar.get(Calendar.MONTH) + 1, ano)

        callback.enqueue(object : Callback<List<DespesaModel>> {
            override fun onResponse(
                call: Call<List<DespesaModel>>,
                response: Response<List<DespesaModel>>
            ) {
                if (response.isSuccessful) {
                    val despesas = response.body()
                    if (despesas != null) {
                        despesaList.clear()
                        despesaList.addAll(despesas)
                        adaptador.notifyDataSetChanged()
                        Log.d("DespesaFragment", "Resposta bem-sucedida $despesas")
                    }
                }
            }

            override fun onFailure(call: Call<List<DespesaModel>>, t: Throwable) {
                // Lidar com o erro
                Log.e("DespesaFragment", "Erro na requisição: ${t.message}", t)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dashboardRequest()
        despesaRequest()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}