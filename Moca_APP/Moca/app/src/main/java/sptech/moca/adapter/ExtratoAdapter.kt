package sptech.moca.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import sptech.moca.R
import sptech.moca.model.ExtratoModel

class ExtratoAdapter(private val dataList: List<ExtratoModel>) :
    RecyclerView.Adapter<ExtratoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textData: TextView = itemView.findViewById(R.id.text_data)
        val textNomeItem: TextView = itemView.findViewById(R.id.descricao_despesa_receita_usuario)
        val textTipoItem: TextView = itemView.findViewById(R.id.tipo_despesa_receita_usuario)
        val valor: TextView = itemView.findViewById(R.id.valor_despesa_receita_usuario)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.linha_receita_despesa_extrato, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("ExtratoAdapter", "onBindViewHolder chamado para posição: $position")
        val item = dataList[position]

        val valorItem = if (item.idReceita != null && item.idReceita.toInt() != 0) "+ ${item.valor}" else "- ${item.valor}"

        holder.textData.text = item.data
        holder.textNomeItem.text = item.descricao
        holder.textTipoItem.text = item.categoria
        holder.valor.text = valorItem

        if (item.idReceita != null && item.idReceita.toInt() != 0) {
            holder.valor.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.verde))
        } else {
            holder.valor.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.vermelho))
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}