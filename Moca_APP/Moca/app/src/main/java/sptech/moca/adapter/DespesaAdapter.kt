package sptech.moca.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import sptech.moca.R
import sptech.moca.model.DespesaModel

class DespesaAdapter(private val dataList: List<DespesaModel>) : RecyclerView.Adapter<DespesaAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textData: TextView = itemView.findViewById(R.id.text_data)
        val textNomeItem: TextView = itemView.findViewById(R.id.descricao_despesa_receita_usuario)
        val textTipoItem: TextView = itemView.findViewById(R.id.tipo_despesa_receita_usuario)
        val valor: TextView = itemView.findViewById(R.id.valor_despesa_receita_usuario)
        // Outros componentes aqui
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.linha_receita_despesa_extrato, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.textData.text = item.data
        holder.textNomeItem.text = item.descricao
        holder.textTipoItem.text = item.idTipoDespesa
        holder.valor.text = "- ${item.valor}"
        holder.valor.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.vermelho))
        // Defina outros valores para os componentes conforme necessário
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}