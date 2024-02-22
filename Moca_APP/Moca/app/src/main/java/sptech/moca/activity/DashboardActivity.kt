package sptech.moca.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import retrofit2.Call
import retrofit2.Response
import sptech.moca.R
import sptech.moca.api.EndpointClient
import sptech.moca.api.EndpointHome
import sptech.moca.databinding.ActivityDashboardBinding
import sptech.moca.model.HomeInformationsModel
import sptech.moca.util.NetworkUtils
import java.util.Calendar

class DashboardActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityDashboardBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Esconder a barra de menu do topo
        supportActionBar?.hide()
    }
}