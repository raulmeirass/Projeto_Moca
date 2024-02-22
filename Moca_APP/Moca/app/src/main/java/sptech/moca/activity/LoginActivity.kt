package sptech.moca.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response
import sptech.moca.R
import sptech.moca.api.EndpointClient
import sptech.moca.databinding.ActivityLoginBinding
import sptech.moca.model.UserModel
import sptech.moca.util.NetworkUtils

class LoginActivity : AppCompatActivity() {

    // Declarar uma variável para SharedPreferences
    private lateinit var onBoardingScreen: SharedPreferences

    // Inicialização do View Binding
    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Ocultar a barra de ação
        supportActionBar?.hide()

        //============================================================================
        // Mudar para a tela de cadastro
        //============================================================================
        binding.cadastrarTextBtn.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }


        //============================================================================
        // Mudar para a tela de resetar senha
        //============================================================================
//        binding.esqueceuSenhaTextBn.setOnClickListener {
//            val intent = Intent(this, ResetPasswordActivity::class.java)
//            startActivity(intent)
//        }


        //============================================================================
        // Mostrar e esconder senha da tela de Login
        //============================================================================
        val editarTextSenha = binding.inputSenha
        val imageViewSenhaVisivel = binding.iconeSenhaVer

        imageViewSenhaVisivel.setOnClickListener {
            if (editarTextSenha.inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                editarTextSenha.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                imageViewSenhaVisivel.setImageResource(R.drawable.mostrar_senha)
            } else {
                editarTextSenha.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                imageViewSenhaVisivel.setImageResource(R.drawable.esconder_senha)
            }

            // Coloque o cursor no final do texto
            editarTextSenha.setSelection(editarTextSenha.text.length)
        }


        //============================================================================
        // Validação para campos vazios e login
        //============================================================================
        binding.btnLogin.setOnClickListener {
            if (binding.inputEmail.text.isBlank()) {
                binding.inputEmail.error = "Preencha os campos!"
                return@setOnClickListener // "mata" o método
            } else if (binding.inputSenha.text.isBlank()) {
                binding.inputSenha.error = "Preencha os campos!"
                return@setOnClickListener // "mata" o método
            } else {
                login()
            }
        }

    }


    //============================================================================
    // Retrofit
    //============================================================================
    private fun login() {
        val retrofitClient = NetworkUtils.getRetrofitInstance()
//        val retrofitClient = NetworkUtils.getRetrofitInstance("https://54.225.15.170:8443/api/")
        val endpoint = retrofitClient.create(EndpointClient::class.java)

        // Crie sua carga útil "raw" como uma String
        val jsonPayload =
            "{\"email\":\"${binding.inputEmail.text}\",\"senha\":\"${binding.inputSenha.text}\"}"
        // Converta a String em um RequestBody
        val requestBody = jsonPayload.toRequestBody("application/json".toMediaTypeOrNull())
        // Faça a chamada à API passando o RequestBody
        val callback = endpoint.authenticate(requestBody)

        callback.enqueue(object : retrofit2.Callback<UserModel> {
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                if (response.isSuccessful) {
                    // Lógica para tratamento de login bem-sucedido
                    val user = response.body()

                    if (user != null) {
                        // Navegue para a tela de Onboarding ou tela principal com base na primeira vez de acesso
                        val onBoardingScreen = getSharedPreferences("onBoardingScreen", MODE_PRIVATE)
                        val isFirstTime = onBoardingScreen.getBoolean("firstTime", true)

                        val intent = if (isFirstTime) {
                            // Define que o usuário já viu a tela de Onboarding
                            val editor = onBoardingScreen.edit()
                            editor.putBoolean("firstTime", false)
                            editor.apply()
                            Intent(this@LoginActivity, OnboardingActivity::class.java)
                        } else {
                            Intent(this@LoginActivity, MainActivity::class.java)
                        }
                        startActivity(intent)
                        finish()
                    }

                    // Salvando dados do Usuário
                    val sharedPreferences = getSharedPreferences("DadosUsuario", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putLong("idUsuario", response.body()!!.id)
                    editor.putString("token", response.body()!!.token)
                    editor.apply()
                } else {
                    // Tratamento de erros
                    when (response.code()) {
                        400 -> binding.mensagemDeErro.text = "Requisição inválida"
                        401 -> binding.mensagemDeErro.text = "Não autorizado"
                        403 -> binding.mensagemDeErro.text = "Credenciais inválidas"
                        404 -> binding.mensagemDeErro.text = "Usuário não encontrado"
                        500 -> binding.mensagemDeErro.text = "Erro no servidor"
                        else -> binding.mensagemDeErro.text = "Erro desconhecido"
                    }
                }
            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                // Tratamento de falha na chamada da API
                println("Erro ao realizar login!\nErro: ${t.message}")
                "Erro ao realizar login".also { binding.mensagemDeErro.text = it }
            }
        })

    }

}
