package sptech.moca.adapter

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import sptech.moca.R
import sptech.moca.activity.ExtratoPorquinhoActivity
import sptech.moca.fragment.ExtratoPorquinhoFragment
import sptech.moca.model.PorquinhoModel

class PorquinhoAdapter(private val dataList: List<PorquinhoModel>) :
    RecyclerView.Adapter<PorquinhoAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomePorquinho: TextView = itemView.findViewById(R.id.nome_porquinho)
        val imagemPorquinho: ImageView = itemView.findViewById(R.id.icone_porquinho)
        val progress: ProgressBar = itemView.findViewById(R.id.porcentagem_porquinho)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PorquinhoAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.linha_porquinho, parent, false)

        val viewHolder = PorquinhoAdapter.ViewHolder(view)

        return viewHolder
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: PorquinhoAdapter.ViewHolder, position: Int) {
        val item = dataList[position]

        val porcentagem = (item.valorAtual / item.valorFinal) * 100

        holder.nomePorquinho.text = item.nome
        holder.progress.progress = porcentagem.toInt()
        holder.progress.progressTintList = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.azul_botao))
        holder.progress.setBackgroundColor(Color.GRAY) // ou qualquer outra cor de fundo

        when {
            item.idIcone == 1 -> {
                holder.imagemPorquinho.setImageResource(R.drawable.baseline_local_airport_24)
            }
            item.idIcone == 2 -> {
                holder.imagemPorquinho.setImageResource(R.drawable.baseline_collections_bookmark_24)
            }
            item.idIcone == 3 -> {
                holder.imagemPorquinho.setImageResource(R.drawable.baseline_monetization_on_24)
            }
            item.idIcone == 4 -> {
                holder.imagemPorquinho.setImageResource(R.drawable.baseline_home_24)
            }
            item.idIcone == 5 -> {
                holder.imagemPorquinho.setImageResource(R.drawable.baseline_directions_car_24)
            }
            item.idIcone == 6 -> {
                holder.imagemPorquinho.setImageResource(R.drawable.baseline_family_restroom_24)
            }
            item.idIcone == 7 -> {
                holder.imagemPorquinho.setImageResource(R.drawable.baseline_auto_awesome_24)
            }
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ExtratoPorquinhoActivity::class.java)
            intent.putExtra("porquinhoId", item.idPorquinho)

            // Salvando o ID do porquinho no SharedPreferences
            val sharedPreferences = holder.itemView.context.getSharedPreferences("DadosPorquinho", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putLong("porquinhoId", item.idPorquinho)
            editor.apply()

            holder.itemView.context.startActivity(intent)
        }

    }
}