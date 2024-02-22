package sptech.moca.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sptech.moca.R
class TabelaAdapter(private val tabelaList: MutableList<Pair<String, Double>>) :
    RecyclerView.Adapter<TabelaAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewMes: TextView = itemView.findViewById(R.id.textViewMes)
        val textViewValor: TextView = itemView.findViewById(R.id.textViewValor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tabela_calculadora, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = tabelaList[position]
        holder.textViewMes.text = item.first
        holder.textViewValor.text = String.format("%.2f", item.second)
    }

    override fun getItemCount(): Int {
        return tabelaList.size
    }

    fun atualizarLista(novaLista: List<Pair<String, Double>>) {
        tabelaList.clear()
        tabelaList.addAll(novaLista)
        notifyDataSetChanged()
    }

}