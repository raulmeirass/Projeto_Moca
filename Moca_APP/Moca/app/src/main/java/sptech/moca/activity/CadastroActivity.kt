package sptech.moca.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Patterns
import com.google.gson.JsonSyntaxException
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response
import sptech.moca.R
import sptech.moca.api.EndpointClient
import sptech.moca.databinding.ActivityCadastroBinding
import sptech.moca.model.UserModel
import sptech.moca.util.NetworkUtils
import java.io.IOException

class CadastroActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityCadastroBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

//==================================================================================================
//        Mudar para a tela de Login
//==================================================================================================
        binding.entrarTextBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


//==================================================================================================
//        Mostrar e esconder senha da tela de Cadastro
//==================================================================================================
//        Senha
        val editarTextSenha = binding.inputSenha
        val imageViewSenhaVisivel = binding.iconeSenhaVer
//        Confirme senha
        val editarTextConfirmeSenha = binding.inputConfirmeSenha
        val imageViewConfirmeSenhaVisivel = binding.iconeConfirmeSenhaVer

//        Esconder e mostrar senha
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

//==================================================================================================
//        Esconder e mostrar Confirme senha
//==================================================================================================
        imageViewConfirmeSenhaVisivel.setOnClickListener {
            if (editarTextConfirmeSenha.inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                editarTextConfirmeSenha.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                imageViewConfirmeSenhaVisivel.setImageResource(R.drawable.mostrar_senha)
            } else {
                editarTextConfirmeSenha.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                imageViewConfirmeSenhaVisivel.setImageResource(R.drawable.esconder_senha)
            }
        }


        //============================================================================
        // Validação para campos vazios e CADASTRO
        //============================================================================
        binding.btnCadastro.setOnClickListener {
            when {
                binding.inputNome.text.isBlank() -> {
                    binding.inputNome.error = "Preencha os campos!"
                    return@setOnClickListener // "mata" o método
                }
                binding.inputEmail.text.isBlank() -> {
                    binding.inputEmail.error = "Preencha os campos!"
                    return@setOnClickListener // "mata" o método
                }
                !isEmailValid(binding.inputEmail.text) -> {
                    binding.inputEmail.error = "Email inválido!"
                    return@setOnClickListener // "mata" o método
                }
                binding.inputSenha.text.isBlank() -> {
                    binding.inputSenha.error = "Preencha os campos!"
                    return@setOnClickListener // "mata" o método
                }
                binding.inputConfirmeSenha.text.isBlank() -> {
                    binding.inputConfirmeSenha.error = "Preencha os campos!"
                    return@setOnClickListener // "mata" o método
                }
                !binding.inputSenha.text.toString().equals(binding.inputConfirmeSenha.text.toString()) -> {
                    "Senha e confirme senha não conferem!".also { binding.mensagemDeErro.text = it }
                }
                binding.inputSenha.text.length < 6 -> {
                    binding.inputSenha.error = "A senha deve conter pelo menos 6 caracteres!"
                }
                else -> {
                    // Removendo os valores caso estejam certos
                    binding.inputNome.error = null
                    binding.inputEmail.error = null
                    binding.inputSenha.error = null
                    binding.inputConfirmeSenha.error = null
                    binding.mensagemDeErro.text = null
                    "".also { binding.mensagemDeErro.text = it }

                    cadastro()
                }
            }
        }
    }


    private fun cadastro() {
        val retrofitClient = NetworkUtils.getRetrofitInstance()
        val endpoint = retrofitClient.create(EndpointClient::class.java)

        // Crie sua carga útil "raw" como uma String
        val jsonPayload =
            "{\"nome\":\"${binding.inputNome.text}\",\"email\":\"${binding.inputEmail.text}\",\"senha\":\"${binding.inputSenha.text}\",\"telefone\":\"${""}\",\"idTipoPerfil\":\"${5}\"}"

        // Converta a String em um RequestBody
        val requestBody = jsonPayload.toRequestBody("application/json".toMediaTypeOrNull())

        // Faça a chamada à API passando o RequestBody
        val callback = endpoint.registerUser(requestBody)


        callback.enqueue(object : retrofit2.Callback<UserModel> {
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                when {
                    response.isSuccessful -> {
                        "".also { binding.mensagemDeErro.text = it }
                        // Mudando de tela caso o cadastro seja efetuado com sucesso
                        val intent = Intent(this@CadastroActivity, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    response.code() == 409 -> {
                        "Email já existente!".also { binding.mensagemDeErro.text = it }
                    }
                    else -> {
                        // Tratamento de erros genéricos
                        val errorMsg = when (response.code()) {
                            404 -> "Recurso não encontrado"
                            500 -> "Erro interno do servidor"
                            else -> "Erro desconhecido: ${response.code()}"
                        }
                        errorMsg.also { binding.mensagemDeErro.text = it }
                    }
                }
            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                val errorMsg = when (t) {
                    is IOException -> "Erro de conexão: Verifique sua conexão com a internet."
                    is JsonSyntaxException -> "Erro de parsing: A resposta do servidor não pôde ser analisada."
                    else -> "Erro ao realizar login!\nErro: ${t.message}"
                }
                errorMsg.also { binding.mensagemDeErro.text = it }
            }
        })



    }


    private fun isEmailValid(email: CharSequence): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}