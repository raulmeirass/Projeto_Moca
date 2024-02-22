package sptech.moca.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Response
import sptech.moca.R
import sptech.moca.activity.MainActivity
import sptech.moca.api.EndpointCartao
import sptech.moca.model.CartaoModel
import sptech.moca.util.NetworkUtils

class CartoesAdapter(private val dataList: List<CartaoModel>) :
    RecyclerView.Adapter<CartoesAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val apelidoCartao: TextView = itemView.findViewById(R.id.apelido_cartao)
        val limiteCartao: TextView = itemView.findViewById(R.id.limite_cartao)
        val vencimento: TextView = itemView.findViewById(R.id.vencimento_cartao)
        val corCartao: LinearLayout = itemView.findViewById(R.id.cor_cartao)
        val progress: ProgressBar = itemView.findViewById(R.id.utilizado_cartao)
        // Outros componentes aqui
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.linha_cartoes, parent, false)

        return ViewHolder(view)
    }




    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]

        holder.apelidoCartao.text = "Apelido: ${item.apelido}"
        holder.limiteCartao.text = "Limite: ${item.limite.toString()}"
        holder.vencimento.text = "Vencimento: ${item.vencimento}"
        holder.progress.progress = item.porcentagemUtilizado.toInt()
        holder.progress.progressTintList = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.azul_botao))
        holder.progress.setBackgroundColor(Color.WHITE) // ou qualquer outra cor de fundo



        when {
            item.idCor.toInt() == 1 -> {
                holder.corCartao.setBackgroundColor((ContextCompat.getColor(holder.itemView.context, R.color.azul_royal)))
            }
            item.idCor.toInt() == 2 -> {
                holder.corCartao.setBackgroundColor((ContextCompat.getColor(holder.itemView.context, R.color.verde_esmeralda)))
            }
            item.idCor.toInt() == 3 -> {
                holder.corCartao.setBackgroundColor((ContextCompat.getColor(holder.itemView.context, R.color.amarelo_sol)))
            }
            item.idCor.toInt() == 4 -> {
                holder.corCartao.setBackgroundColor((ContextCompat.getColor(holder.itemView.context, R.color.vermelho_cereja)))
            }
            item.idCor.toInt() == 5 -> {
                holder.corCartao.setBackgroundColor((ContextCompat.getColor(holder.itemView.context, R.color.roxo_violeta)))
            }
            item.idCor.toInt() == 6 -> {
                holder.corCartao.setBackgroundColor((ContextCompat.getColor(holder.itemView.context, R.color.laranja_coral)))
            }
            item.idCor.toInt() == 7 -> {
                holder.corCartao.setBackgroundColor((ContextCompat.getColor(holder.itemView.context, R.color.cinza_prata)))
            }
        }
    }



    override fun getItemCount(): Int {
        return dataList.size
    }
}