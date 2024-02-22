package sptech.moca.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.fitness.data.DataPoint
import org.eazegraph.lib.models.PieModel
import retrofit2.Response
import sptech.moca.api.EndpointHome
import sptech.moca.databinding.ActivityDashboardBinding
import sptech.moca.model.HomeInformationsModel
import sptech.moca.util.NetworkUtils
import java.util.Calendar

class DashboardFragment : Fragment() {

    private var _binding: ActivityDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityDashboardBinding.inflate(inflater, container, false)
        val view = binding.root

        // Inflar o layout do fragmento aqui
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Configurar a lógica do fragmento aqui
        // Por exemplo, esconder a barra de menu do topo
//        requireActivity().supportActionBar?.hide()
        dashboardRequest()
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
                    "R$ ${response.body()!!.saldo}".also { binding.saldoUsuarioDashboard.text = it }

                    "R$ ${response.body()!!.receita}".also {
                        binding.receitaUsuarioDashboard.text = it
                    }
                    "R$ ${response.body()!!.despesas}".also {
                        binding.despesaUsuarioDashboard.text = it
                    }

//                    println("R$ ${response.body()!!.saldo}")
//                    println("R$ ${response.body()!!.receita}")
//                    println("R$ ${response.body()!!.despesas}")

                    // Valores do gráfico
                    "R$ ${response.body()!!.receita}".also {
                        binding.receitaGraficoUsuarioDashboard.text = it
                    }
                    "R$ ${response.body()!!.despesas}".also {
                        binding.despesaGraficoUsuarioDashboard.text = it
                    }
                    "R$ ${response.body()!!.receita - response.body()!!.despesas}".also {
                        binding.balancoGraficoUsuarioDashboard.text = it
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<HomeInformationsModel>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }
}