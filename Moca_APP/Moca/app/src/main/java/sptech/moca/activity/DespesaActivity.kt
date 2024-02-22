package sptech.moca.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import sptech.moca.R
import sptech.moca.databinding.ActivityDespesaBinding

class DespesaActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityDespesaBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}