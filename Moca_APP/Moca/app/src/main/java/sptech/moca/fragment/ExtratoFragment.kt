package sptech.moca.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Response
import sptech.moca.R
import sptech.moca.api.EndpointExtrato
import sptech.moca.model.ExtratoModel
import sptech.moca.util.NetworkUtils
import java.util.Calendar
import sptech.moca.adapter.ExtratoAdapter
import sptech.moca.api.EndpointHome
import sptech.moca.api.ExtratoResponse
import sptech.moca.databinding.ActivityExtratoBinding
import sptech.moca.model.HomeInformationsModel


class ExtratoFragment : Fragment() {
    private var _binding: ActivityExtratoBinding? = null
    private val binding get() = _binding!!

    private val extratoList = mutableListOf<ExtratoModel>()
    private lateinit var adaptador: ExtratoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityExtratoBinding.inflate(inflater, container, false)
        val view = binding.root

        adaptador = ExtratoAdapter(extratoList)
        binding.recyclerViewExtrato.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewExtrato.adapter = adaptador

        exibirExtrato()
        dashboardRequest()

        return view
    }

    private fun showToast(message: String) {
        context?.let {
            Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
        }
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

        callback.enqueue(object : retrofit2.Callback<HomeInformationsModel> {
            override fun onResponse(
                call: retrofit2.Call<HomeInformationsModel>,
                response: Response<HomeInformationsModel>
            ) {
                if (response.isSuccessful) {

                    // Valores
                    "R$ ${response.body()!!.saldo}".also { binding.saldoExtrato.text = it }

                    "R$ ${response.body()!!.receita}".also {
                        binding.receitaExtrato.text = it
                    }
                    "R$ ${response.body()!!.despesas}".also {
                        binding.despesaExtrato.text = it
                    }
                }
            }
            override fun onFailure(call: retrofit2.Call<HomeInformationsModel>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }

    private fun exibirExtrato() {
        val retrofitClient = NetworkUtils.getRetrofitInstance()
        val endpoint = retrofitClient.create(EndpointExtrato::class.java)
        val sharedPreferences =
            requireActivity().getSharedPreferences("DadosUsuario", Context.MODE_PRIVATE)
        val idUsuario = sharedPreferences.getLong("idUsuario", -1)

        val calendar = Calendar.getInstance()
        val ano = calendar.get(Calendar.YEAR)

        val callback =
            endpoint.getInformationExtract(idUsuario, calendar.get(Calendar.MONTH) + 1, ano)

        callback.enqueue(object : retrofit2.Callback<ExtratoResponse> {
            override fun onResponse(
                call: Call<ExtratoResponse>,
                response: Response<ExtratoResponse>
            ) {
                if (response.isSuccessful) {
                    val extratoResponse: ExtratoResponse? = response.body()
                    val listaDeExtrato: List<ExtratoModel>? = extratoResponse?.items

                    if (!listaDeExtrato.isNullOrEmpty()) {
                        extratoList.clear()
                        extratoList.addAll(listaDeExtrato)
                        adaptador.notifyDataSetChanged()
                    }
                } else {
                    println(
                        "Erro na resposta: ${response.code()} - ${
                            response.errorBody()?.string()
                        }"
                    )
                    showToast("Erro ao carregar extrato. Tente novamente.")
                }
            }

            override fun onFailure(call: Call<ExtratoResponse>, t: Throwable) {
                println("Falha na requisição: ${t.message}")
                showToast("Falha ao conectar ao servidor. Verifique sua conexão com a Internet.")
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exibirExtrato()
        dashboardRequest()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}