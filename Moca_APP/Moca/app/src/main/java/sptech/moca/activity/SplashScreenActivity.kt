package sptech.moca.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.constraintlayout.motion.widget.MotionLayout
import sptech.moca.R

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var motionLayout: MotionLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash_screen)

        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        motionLayout = findViewById(R.id.mainLayout)
        motionLayout.startLayoutAnimation()
        motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                val sharedPreferences = getSharedPreferences("DadosUsuario", Context.MODE_PRIVATE)

                if (sharedPreferences.contains("idUsuario") && sharedPreferences.contains("token")) {
                    val idUsuario = sharedPreferences.getLong("idUsuario", 0)
                    val token = sharedPreferences.getString("token", "")

                    if (idUsuario != 0L && !token.isNullOrEmpty()) {
                        // As SharedPreferences existem e estão preenchidas com valores não nulos ou vazios
                        startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                    } else {
                        // As SharedPreferences existem, mas pelo menos um valor é nulo ou vazio
                        startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
                    }
                } else {
                    // As SharedPreferences não existem
                    startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
                }


                finish()
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
            }

        })
    }
}