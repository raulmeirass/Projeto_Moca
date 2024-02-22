package sptech.moca.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import sptech.moca.R

class SliderAdapter(private val context: Context) : PagerAdapter() {

    private val imagens = arrayOf(
        R.drawable.organizacao_onboarding_image,
        R.drawable.limite_cartao_onboarding_image,
        R.drawable.organizacao_onboarding_image,
        R.drawable.limite_cartao_onboarding_image
    )

    private val titulos = arrayOf(
        R.string.titulo_onboarding_organizacao,
        R.string.titulo_onboarding_limite_cartao,
        R.string.titulo_onboarding_notificacoes,
        R.string.titulo_onboarding_matenha_controle
    )

    private val descricoes = arrayOf(
        R.string.text_onboarding_organizacao,
        R.string.text_onboarding_limite_cartao,
        R.string.text_onboarding_notificacoes,
        R.string.text_onboarding_matenha_controle
    )

    override fun getCount(): Int {
        return titulos.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.slides_layout, container, false)


        // Aqui você pode configurar as visualizações dentro do layout de item
        val imageView = layout.findViewById<ImageView>(R.id.imagem_onboarding)
        val tituloTextView = layout.findViewById<TextView>(R.id.titulo_onboarding)
        val descricaoTextView = layout.findViewById<TextView>(R.id.descricao_onboarding)

        imageView.setImageResource(imagens[position])
        tituloTextView.setText(titulos[position])
        descricaoTextView.setText(descricoes[position])

        container.addView(layout)
        return layout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
