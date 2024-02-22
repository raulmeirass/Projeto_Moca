package sptech.moca.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Response
import sptech.moca.R
import sptech.moca.activity.CadastroActivity
import sptech.moca.api.EndpointConfiguracoes
import sptech.moca.api.EndpointExtrato
import sptech.moca.databinding.ActivityConfiguracoesBinding
import sptech.moca.databinding.ActivityReceitaBinding
import sptech.moca.model.ConfiguracoesModel
import sptech.moca.util.NetworkUtils
import java.util.Calendar

class ConfiguracoesFragment : Fragment() {

    private var _binding: ActivityConfiguracoesBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityConfiguracoesBinding.inflate(inflater, container, false)

        exibirConfiguracoes()

        binding.btnSair.setOnClickListener {
            deslogar()
        }


        // Inflar o layout do fragmento de Configurações aqui
        return binding.root
    }

    private fun showToast(message: String) {
        context?.let {
            Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun exibirConfiguracoes() {
        val retrofitClient = NetworkUtils.getRetrofitInstance()
        val endpoint = retrofitClient.create(EndpointConfiguracoes::class.java)
        val sharedPreferences =
            requireActivity().getSharedPreferences("DadosUsuario", Context.MODE_PRIVATE)
        val idUsuario = sharedPreferences.getLong("idUsuario", -1)

        val callback = endpoint.getInformationExtract(idUsuario)

        callback.enqueue(object : retrofit2.Callback<ConfiguracoesModel> {
            override fun onResponse(
                call: Call<ConfiguracoesModel>,
                response: Response<ConfiguracoesModel>
            ) {
                if (response.isSuccessful) {
                    // Sucesso, imprima o corpo da resposta
                    println("Resposta bem-sucedida: ${response.body()}")
                    println("Deu certo o Configurações!")
                    binding.notificacoesConfig.isChecked = response.body()?.enviaEmail == true

                    binding.smsConfig.isChecked = response.body()?.enviaSms == true
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

            override fun onFailure(call: Call<ConfiguracoesModel>, t: Throwable) {
                println("Falha na requisição: ${t.message}")
                showToast("Falha ao conectar ao servidor. Verifique sua conexão com a Internet.")
            }

        })
    }

    // Outros métodos e lógica relacionada ao fragmento de Configurações
    private fun deslogar() {
        val sharedPreferences =
            requireContext().getSharedPreferences("DadosUsuario", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putBoolean("firstTime", true)
        editor.putLong("idUsuario", 0)
        editor.putString("token", "")
        editor.apply()
        val intent = Intent(requireContext(), CadastroActivity::class.java)
        startActivity(intent)
    }
}