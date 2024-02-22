package sptech.moca.activity

import android.R.id
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response
import sptech.moca.R
import sptech.moca.api.CartoesResponse
import sptech.moca.api.EndpointCartao
import sptech.moca.api.EndpointDespesa
import sptech.moca.databinding.ActivityCadastrarDespesaBinding
import sptech.moca.fragment.DespesaFragment
import sptech.moca.fragment.ReceitaFragment
import sptech.moca.model.CartaoModel
import sptech.moca.model.DespesaModel
import sptech.moca.model.OpcaoReceitaDespesaModel
import sptech.moca.util.NetworkUtils
import java.util.Calendar

class CadastrarDespesa : AppCompatActivity() {

    private lateinit var editTextDate: EditText
    private lateinit var datePicker: DatePicker
    private var selectedDate: String? = null
    private var idCartao: Int? = null
    private var listaCartoes: List<CartaoModel> = emptyList()

    private var idCartaoSelecionado: Long = -1

    val opcoesDespesa = listOf(
        "-Selecione-",
        "Moradia",
        "Alimentação",
        "Transporte",
        "Saúde",
        "Educação",
        "Lazer",
        "Vestuário",
        "Dívidas",
        "Impostos",
        "Outras"
    )

    val tipoDespesa = listOf(
        "-Selecione-",
        "Dinheiro",
        "Fixa",
        "Cartão"
    )

    val qtdParcelas = listOf(
        "-Selecione-",
        "1x",
        "2x",
        "3x",
        "4x",
        "5x",
        "6x",
        "7x",
        "8x",
        "9x",
        "10x",
        "11x",
        "12x"
    )

    val binding by lazy {
        ActivityCadastrarDespesaBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        receberCartoesCliente()

        val adapterCategoria =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, opcoesDespesa)
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categoriaAdicionarDespesa.adapter = adapterCategoria

        val adapterTipoDespesa =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, tipoDespesa)
        adapterTipoDespesa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.adicionarTipoDespesa.adapter = adapterTipoDespesa


        val adapterQtdParcelas =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, qtdParcelas)
        adapterQtdParcelas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.parcelasAdicionarDespesa.adapter = adapterQtdParcelas

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

        binding.cartaoAdicionarDespesa.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    // Verifique se a posição selecionada não é uma das posições adicionais
                    if (position >= 2) {
                        // Acesse o cartão selecionado usando a listaCartoes
                        val cartaoSelecionado = listaCartoes[position - 2]
                        // Acesse o ID do cartão
                        idCartaoSelecionado = cartaoSelecionado.idCartao
                    } else {
                        // Se "-Selecione-" ou "Nenhum" for selecionado, defina o ID como -1 ou um valor padrão
                        idCartaoSelecionado = -1
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Não é necessário fazer nada aqui
                }
            }

        binding.adicionarTipoDespesa.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when {
                        binding.adicionarTipoDespesa.selectedItemPosition == 0 -> {
                            binding.llyCartao.visibility = View.GONE
                            binding.llyParcelas.visibility = View.GONE
                        }

                        binding.adicionarTipoDespesa.selectedItemPosition == 1 -> {
                            binding.llyCartao.visibility = View.GONE
                            binding.llyParcelas.visibility = View.GONE
                        }

                        binding.adicionarTipoDespesa.selectedItemPosition == 2 -> {
                            binding.llyCartao.visibility = View.VISIBLE
                            binding.llyParcelas.visibility = View.GONE
                            println(listaCartoes)
                        }

                        binding.adicionarTipoDespesa.selectedItemPosition == 3 -> {
                            binding.llyCartao.visibility = View.VISIBLE
                            binding.llyParcelas.visibility = View.VISIBLE
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    val errorMessage = "Selecione uma opção válida"
                    (binding.adicionarTipoDespesa.selectedView as? TextView)?.error = errorMessage
                }

            }

        binding.cadastrarDespesaUsuario.setOnClickListener {
            when {
                binding.adicionarTipoDespesa.selectedItemPosition == 1 -> {
                    if (binding.valorDespesaUsuario.text.toString()
                            .toDouble() > 0.0 && binding.categoriaAdicionarDespesa.selectedItemPosition != 0
                        && binding.descricaoDespesaUsuario.text.toString()
                            .isNotBlank() && selectedDate != null
                    ) {
                        cadastrarDespesa()
                    }
                }

                binding.adicionarTipoDespesa.selectedItemPosition == 2 -> {
                    if (binding.valorDespesaUsuario.text.toString()
                            .toDouble() > 0 && binding.categoriaAdicionarDespesa.selectedItemPosition != 0
                        && binding.descricaoDespesaUsuario.text.toString()
                            .isNotBlank() && selectedDate != null
                    ) {
                        if (binding.cartaoAdicionarDespesa.selectedItemPosition != 0 && binding.cartaoAdicionarDespesa.selectedItemPosition != 1) {
                            if (listaCartoes.any { cartao ->
                                    (cartao.limite - cartao.utilizado) >= (binding.valorDespesaUsuario.toString()
                                        .toDouble() * 12)
                                }) {
                                cadastrarDespesaFixaComCartao()
                            } else {
                                println("Valor ultrapassa o limite do cartão")
                            }
                        } else {
                            cadastrarDespesaFixaSemCartao()
                        }

                    }
                }

                binding.adicionarTipoDespesa.selectedItemPosition == 3 -> {
                    if (binding.valorDespesaUsuario.text.toString()
                            .toDouble() > 0.0 && binding.categoriaAdicionarDespesa.selectedItemPosition != 0
                        && binding.descricaoDespesaUsuario.text.toString()
                            .isNotBlank() && selectedDate != null
                        && binding.cartaoAdicionarDespesa.selectedItemPosition != 0
                        && binding.cartaoAdicionarDespesa.selectedItemPosition != 1
                        && binding.parcelasAdicionarDespesa.selectedItemPosition != 0
                    ) {
                        cadastrarDespesaParcelada()
                    }
                }

            }
        }

        binding.imageView.setOnClickListener {
            val intent = Intent(this@CadastrarDespesa, DespesaFragment::class.java)
            startActivity(intent)
        }

    }

    private fun exibirCartoes() {
        val listaApelidos = listaCartoes.map { it.apelido }.toMutableList()

        val itemAdicional = "-Selecione-" // Substitua isso pelo texto desejado
        listaApelidos.add(0, itemAdicional)
        val itemAdicionalNenhum = "Nenhum" // Substitua isso pelo texto desejado
        listaApelidos.add(1, itemAdicionalNenhum)

        val adapterCartoes = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaApelidos)
        adapterCartoes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.cartaoAdicionarDespesa.adapter = adapterCartoes
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

    fun receberCartoesCliente() {
        val retrofitClient = NetworkUtils.getRetrofitInstance()
        val endpoint = retrofitClient.create(EndpointCartao::class.java)
        val sharedPreferences = getSharedPreferences("DadosUsuario", Context.MODE_PRIVATE)
        val idUsuario = sharedPreferences.getLong("idUsuario", -1)
        val calendar = Calendar.getInstance()
        val ano = calendar.get(Calendar.YEAR)
        val callback = endpoint.getCartoes(idUsuario, calendar.get(Calendar.MONTH) + 1, ano)

        callback.enqueue(object : retrofit2.Callback<CartoesResponse> {
            override fun onResponse(
                call: Call<CartoesResponse>,
                response: Response<CartoesResponse>
            ) {
                when {
                    response.isSuccessful -> {
                        listaCartoes = response.body()?.cartoes ?: emptyList()
                        println(listaCartoes)
                        exibirCartoes()
                    }

                    response.code() == 401 -> println("Não autorizado. Faça login novamente.")
                    response.code() == 404 -> println("Recurso não encontrado.")
                    else -> println("Ocorreu um erro. Tente novamente mais tarde.")
                }
            }

            override fun onFailure(call: Call<CartoesResponse>, t: Throwable) {

            }

        })
    }

    private fun cadastrarDespesa() {
        val retrofitClient = NetworkUtils.getRetrofitInstance()
        val endpoint = retrofitClient.create(EndpointDespesa::class.java)
        val sharedPreferences = getSharedPreferences("DadosUsuario", Context.MODE_PRIVATE)
        val idUsuario = sharedPreferences.getLong("idUsuario", -1)

        val jsonPayload = "{\"descricao\":\"${binding.descricaoDespesaUsuario.text}\"," +
                "\"valor\":${binding.valorDespesaUsuario.text.toString().toDouble()}," +
                "\"data\":\"${selectedDate}\"," +
                "\"isPaid\":${false}," +
                "\"isParcela\":${false}," +
                "\"idCliente\":${idUsuario}," +
                "\"idTipoDespesa\":${binding.categoriaAdicionarDespesa.selectedItemPosition}}"

        // Converta a String em um RequestBody
        val requestBody = jsonPayload.toRequestBody("application/json".toMediaTypeOrNull())
        // Faça a chamada à API passando o RequestBody
        val callback = endpoint.postCadastrarDespesa(requestBody)

        callback.enqueue(object : retrofit2.Callback<DespesaModel> {
            override fun onResponse(call: Call<DespesaModel>, response: Response<DespesaModel>) {
                if (response.isSuccessful) {
                    // Sucesso, imprima o corpo da resposta
                    println("Resposta bem-sucedida: ${response.body()}")
                    val intent = Intent(this@CadastrarDespesa, MainActivity::class.java)
                    startActivity(intent)
                    println("Deu certo!")
                } else {
                    // Erro, imprima o corpo da resposta e o código de resposta
                    println(
                        "Erro na resposta: ${response.code()} - ${
                            response.errorBody()?.string()
                        }"
                    )
                    println(jsonPayload)
                    showToast("Erro ao cadastrar receita. Tente novamente.")
                }
            }

            override fun onFailure(call: Call<DespesaModel>, t: Throwable) {
                // Lidar com o erro
                println("Falha na requisição: ${t.message}")
                showToast("Falha ao conectar ao servidor. Verifique sua conexão com a Internet.")
            }

        })
    }

    private fun cadastrarDespesaFixaSemCartao() {
        val retrofitClient = NetworkUtils.getRetrofitInstance()
        val endpoint = retrofitClient.create(EndpointDespesa::class.java)
        val sharedPreferences = getSharedPreferences("DadosUsuario", Context.MODE_PRIVATE)
        val idUsuario = sharedPreferences.getLong("idUsuario", -1)
        // Crie sua carga útil "raw" como uma String

        val jsonPayload = "{\"descricao\":\"${binding.descricaoDespesaUsuario.text.toString()}\"," +
                "\"valor\":${binding.valorDespesaUsuario.text.toString().toDouble()}," +
                "\"data\":\"${selectedDate}\"," +
                "\"idCliente\":\"${idUsuario}\"," +
                "\"idTipoDespesa\":${binding.categoriaAdicionarDespesa.selectedItemPosition}," +
                "\"isCartao\":\"${false}\"," +
                "\"idCartao\":\"${null}\"," +
                "\"paid\":\"${false}\"," +
                "\"parcela\":\"${true}\"}"

        val requestBody =
            jsonPayload.toRequestBody("application/json".toMediaTypeOrNull())

        val callback = endpoint.postCadastrarDespesaFixa(requestBody)

        callback.enqueue(object : retrofit2.Callback<DespesaModel> {
            override fun onResponse(call: Call<DespesaModel>, response: Response<DespesaModel>) {
                if (response.isSuccessful) {
                    // Sucesso, imprima o corpo da resposta
                    println("Resposta bem-sucedida: ${response.body()}")
                    val intent = Intent(this@CadastrarDespesa, MainActivity::class.java)
                    startActivity(intent)
                    println("Deu certo despesa fixa com cartão!")
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

            override fun onFailure(call: Call<DespesaModel>, t: Throwable) {
                println("Falha na requisição: ${t.message}")
                showToast("Falha ao conectar ao servidor. Verifique sua conexão com a Internet.")
            }

        })
    }

    private fun cadastrarDespesaFixaComCartao() {
        val retrofitClient = NetworkUtils.getRetrofitInstance()
        val endpoint = retrofitClient.create(EndpointDespesa::class.java)
        val sharedPreferences = getSharedPreferences("DadosUsuario", Context.MODE_PRIVATE)
        val idUsuario = sharedPreferences.getLong("idUsuario", -1)

        val jsonPayloadCartao =
            "{\"descricao\":\"${binding.descricaoDespesaUsuario.text.toString()}\"," +
                    "\"valor\":\"${binding.valorDespesaUsuario.text}\"," +
                    "\"data\":\"${selectedDate}\"," +
                    "\"idCliente\":\"${idUsuario}\"," +
                    "\"idTipoDespesa\":\"${binding.categoriaAdicionarDespesa.selectedItemPosition}\"," +
                    "\"isCartao\":\"${true}\"," +
                    "\"idCartao\":\"$idCartaoSelecionado\"," +
                    "\"paid\":\"${false}\"," +
                    "\"parcela\":\"${true}\"}"

        val requestBody =
            jsonPayloadCartao.toRequestBody("application/json".toMediaTypeOrNull())
        // Faça a chamada à API passando o RequestBody
        val callback = endpoint.postCadastrarDespesaFixa(requestBody)

        callback.enqueue(object : retrofit2.Callback<DespesaModel> {
            override fun onResponse(
                call: Call<DespesaModel>,
                response: Response<DespesaModel>
            ) {
                if (response.isSuccessful) {
                    // Sucesso, imprima o corpo da resposta
                    println("Resposta bem-sucedida: ${response.body()}")
                    val intent = Intent(this@CadastrarDespesa, MainActivity::class.java)
                    startActivity(intent)
                    println("Deu certo despesa fixa com cartão!")
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

            override fun onFailure(call: Call<DespesaModel>, t: Throwable) {
                // Lidar com o erro
                println("Falha na requisição: ${t.message}")
                showToast("Falha ao conectar ao servidor. Verifique sua conexão com a Internet.")
            }

        })
    }

    private fun cadastrarDespesaParcelada() {
        val retrofitClient = NetworkUtils.getRetrofitInstance()
        val endpoint = retrofitClient.create(EndpointDespesa::class.java)
        val sharedPreferences = getSharedPreferences("DadosUsuario", Context.MODE_PRIVATE)
        val idUsuario = sharedPreferences.getLong("idUsuario", -1)
        // Crie sua carga útil "raw" como uma String
        val jsonPayload =
            "{\"descricao\":\"${binding.descricaoDespesaUsuario.text.toString()}\"," +
                    "\"valor\":${binding.valorDespesaUsuario.text.toString().toDouble()}," +
                    "\"data\":\"${selectedDate}\"," +
                    "\"idCliente\":${idUsuario}," +
                    "\"idTipoDespesa\":${binding.categoriaAdicionarDespesa.selectedItemPosition}," +
                    "\"parcelas\":${binding.parcelasAdicionarDespesa.selectedItemPosition}," +
                    "\"idCartao\": ${idCartaoSelecionado}}"

        // Converta a String em um RequestBody
        val requestBody =
            jsonPayload.toRequestBody("application/json".toMediaTypeOrNull())
        // Faça a chamada à API passando o RequestBody
        val callback = endpoint.postCadastrarDespesaParcelada(requestBody)

        callback.enqueue(object : retrofit2.Callback<DespesaModel> {
            override fun onResponse(call: Call<DespesaModel>, response: Response<DespesaModel>) {
                if (response.isSuccessful) {
                    // Sucesso, imprima o corpo da resposta
                    println("Resposta bem-sucedida: ${response.body()}")
                    val intent = Intent(this@CadastrarDespesa, MainActivity::class.java)
                    startActivity(intent)
                    println("Deu certo despesa parcelada!")
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

            override fun onFailure(call: Call<DespesaModel>, t: Throwable) {
                // Lidar com o erro
                val intent = Intent(this@CadastrarDespesa, MainActivity::class.java)
                startActivity(intent)
            }

        })
    }
}