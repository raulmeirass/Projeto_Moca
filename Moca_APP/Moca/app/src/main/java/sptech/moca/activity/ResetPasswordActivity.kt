package sptech.moca.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import sptech.moca.R

class ResetPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        supportActionBar?.hide()

//==================================================================================================
//        Mudar para a tela de Login
//==================================================================================================
        val textJaPossuiLogin = findViewById<TextView>(R.id.entrarTextBtn)
        textJaPossuiLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


//==================================================================================================
//        Mostrar e esconder senha da tela de Cadastro
//==================================================================================================
//        Senha
        val editarTextSenha = findViewById<EditText>(R.id.inputResetSenha)
        val imageViewSenhaVisivel = findViewById<ImageView>(R.id.iconeResetSenhaVer)

//        Esconder e mostrar senha
        imageViewSenhaVisivel.setOnClickListener(View.OnClickListener {
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
        })

//==================================================================================================
//        Esconder e mostrar Confirme senha
//==================================================================================================

        //        Confirme senha
        val editarTextConfirmeSenha = findViewById<EditText>(R.id.inputConfirmeSenhaReset)
        val imageViewConfirmeSenhaVisivel = findViewById<ImageView>(R.id.iconeConfirmeSenhaResetVer)

        imageViewConfirmeSenhaVisivel.setOnClickListener(View.OnClickListener {
            if (editarTextConfirmeSenha.inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                editarTextConfirmeSenha.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                imageViewConfirmeSenhaVisivel.setImageResource(R.drawable.mostrar_senha)
            } else {
                editarTextConfirmeSenha.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                imageViewConfirmeSenhaVisivel.setImageResource(R.drawable.esconder_senha)
            }
        })
    }
}