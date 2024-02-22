package sptech.moca.activity

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response
import sptech.moca.api.EndpointReceita
import sptech.moca.databinding.ActivityCadastrarReceitasBinding
import sptech.moca.fragment.ReceitaFragment
import sptech.moca.model.OpcaoReceitaDespesaModel
import sptech.moca.model.ReceitaModel
import sptech.moca.util.NetworkUtils
import java.util.Calendar

class CadastrarReceitas : AppCompatActivity() {
    private lateinit var binding: ActivityCadastrarReceitasBinding
    private var selectedDate: String? = null

    val opcoesReceita = listOf(
        "-Selecione-",
        "Salário",
        "Rendimentos",
        "Vendas de Bens",
        "Freelance",
        "Aluguel",
        "Ajuda Financeira",
        "Reembolsos",
        "Prêmios",
        "Outras fontes de receita"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastrarReceitasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.selecionarData.setOnClickListener {
            hideKeyboard()
            showDatePicker()
        }

        binding.selecionarData.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                hideKeyboard()
                showDatePicker()
            }
        }

        binding.btnVoltarTelaReceita.setOnClickListener {
            finish()
        }

        binding.cadastrarReceita.setOnClickListener {
            when {
                binding.valorAdicionarReceita.text.isBlank() -> {
                    binding.valorAdicionarReceita.error = "Preencha os campos!"
                    return@setOnClickListener
                }

                binding.categoriaAdicionarReceita.selectedItemPosition == 0 -> {
                    showToast("Selecione uma categoria válida!")
                    return@setOnClickListener
                }

                binding.descricaoAdicionarReceita.text.isBlank() -> {
                    binding.descricaoAdicionarReceita.error = "Preencha os campos!"
                    return@setOnClickListener
                }

                selectedDate.isNullOrBlank() -> {
                    showToast("Selecione uma data válida!")
                    return@setOnClickListener
                }

                else -> {
                    cadastrarReceita()
                }
            }
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opcoesReceita)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categoriaAdicionarReceita.adapter = adapter
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun processSelectedDate(year: Int, month: Int, day: Int) {
        selectedDate = "$year-${month + 1}-$day"
        binding.selecionarData.setText(selectedDate)
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, day ->
                processSelectedDate(year, month, day)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }

    private fun cadastrarReceita() {
        val retrofitClient = NetworkUtils.getRetrofitInstance()
        val endpoint = retrofitClient.create(EndpointReceita::class.java)

        val sharedPreferences = getSharedPreferences("DadosUsuario", Context.MODE_PRIVATE)
        val idUsuario = sharedPreferences.getLong("idUsuario", -1)

        if (idUsuario == -1L) {
            println("O idUsuario é inválido")
            return
        }

        val posicaoSelecionada = binding.categoriaAdicionarReceita.selectedItemPosition

        val jsonPayload =
            "{\"descricao\":\"${binding.descricaoAdicionarReceita.text}\",\"valor\":${
                binding.valorAdicionarReceita.text.toString().toDouble()
            },\"data\":\"${selectedDate}\",\"idCliente\":${idUsuario},\"idTipoReceita\":${posicaoSelecionada}}"

        println(jsonPayload)

        val requestBody = jsonPayload.toRequestBody("application/json".toMediaTypeOrNull())

        val callback = endpoint.postCadastrarReceita(requestBody)

        callback.enqueue(object : retrofit2.Callback<ReceitaModel> {
            override fun onResponse(call: Call<ReceitaModel>, response: Response<ReceitaModel>) {
                if (response.isSuccessful) {
                    // Sucesso, imprima o corpo da resposta
                    println("Resposta bem-sucedida: ${response.body()}")
                    val intent = Intent(this@CadastrarReceitas, MainActivity::class.java)
                    startActivity(intent)
                    println("Deu certo!")
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

            override fun onFailure(call: Call<ReceitaModel>, t: Throwable) {
                // Lidar com o erro
                println("Falha na requisição: ${t.message}")
                showToast("Falha ao conectar ao servidor. Verifique sua conexão com a Internet.")
            }
        })
    }
}
