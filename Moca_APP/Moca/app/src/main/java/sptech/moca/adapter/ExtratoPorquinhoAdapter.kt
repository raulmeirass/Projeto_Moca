package sptech.moca.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sptech.moca.R
import sptech.moca.model.DespesaModel
import sptech.moca.model.PorquinhoExtratoModel
import sptech.moca.model.PorquinhoModel

class ExtratoPorquinhoAdapter(private val dataList: List<PorquinhoExtratoModel>) : RecyclerView.Adapter<ExtratoPorquinhoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textData: TextView = itemView.findViewById(R.id.text_data)
        val textNomeItem: TextView = itemView.findViewById(R.id.descricao_despesa_receita_usuario)
        val textTipoItem: TextView = itemView.findViewById(R.id.tipo_despesa_receita_usuario)
        val valor: TextView = itemView.findViewById(R.id.valor_despesa_receita_usuario)
        // Outros componentes aqui
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExtratoPorquinhoAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.linha_receita_despesa_extrato, parent, false)
        return ExtratoPorquinhoAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ExtratoPorquinhoAdapter.ViewHolder, position: Int) {
        val item = dataList[position]

        holder.textData.text = item.data

        if (item.saque == false){
            holder.textNomeItem.text = "Adicionado"
        }

        holder.valor.text = item.valor.toString()
    }
}