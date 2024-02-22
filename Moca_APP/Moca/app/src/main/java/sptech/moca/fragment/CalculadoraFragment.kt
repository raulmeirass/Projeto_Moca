package sptech.moca.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sptech.moca.R
import sptech.moca.adapter.TabelaAdapter
import kotlin.math.pow

class CalculadoraFragment : Fragment() {

    private lateinit var editTextValorInicial: EditText
    private lateinit var editTextAporteMensal: EditText
    private lateinit var editTextTaxaJuros: EditText
    private lateinit var editTextPeriodo: EditText
    private lateinit var btnCalcular: Button

    private lateinit var recyclerView: RecyclerView
    private lateinit var tabelaAdapter: TabelaAdapter

    private lateinit var textViewResultadoFinal: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar o layout do fragmento de Calculadora aqui
        val view = inflater.inflate(R.layout.activity_calculadora, container, false)
        initializeViews(view)
        setupListeners()
        setupRecyclerView()
        return view
    }

    private fun initializeViews(view: View) {
        editTextValorInicial = view.findViewById(R.id.input_valor_inicial)
        editTextAporteMensal = view.findViewById(R.id.input_aporte_mensal)
        editTextTaxaJuros = view.findViewById(R.id.input_taxa_juros)
        editTextPeriodo = view.findViewById(R.id.input_periodo_meses)
        btnCalcular = view.findViewById(R.id.btn_calcular)

        recyclerView = view.findViewById(R.id.recyclerViewTabela)

        textViewResultadoFinal = view.findViewById(R.id.resultado_final_calculadora)
    }

    private fun setupRecyclerView() {
        tabelaAdapter = TabelaAdapter(ArrayList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = tabelaAdapter
    }

    private fun setupListeners() {
        btnCalcular.setOnClickListener {
            calcularJurosCompostos()
        }
    }

    private fun calcularJurosCompostos() {
        val valorInicial = editTextValorInicial.text.toString().toDoubleOrNull() ?: 0.0
        val aporteMensal = editTextAporteMensal.text.toString().toDoubleOrNull() ?: 0.0
        val taxaJuros = editTextTaxaJuros.text.toString().toDoubleOrNull() ?: 0.0
        val periodo = editTextPeriodo.text.toString().toIntOrNull() ?: 0

        val tabelaResultados =
            gerarTabelaJurosCompostos(valorInicial, aporteMensal, taxaJuros, periodo)

        tabelaAdapter.atualizarLista(tabelaResultados)

        // Exibir o resultado final
        val resultadoFinal = tabelaResultados.lastOrNull()?.second ?: 0.0
        textViewResultadoFinal.text = String.format("R$ %.2f", resultadoFinal)

        tabelaAdapter.atualizarLista(tabelaResultados)
    }

    private fun gerarTabelaJurosCompostos(
        valorInicial: Double,
        aporteMensal: Double,
        taxaJuros: Double,
        periodo: Int
    ): List<Pair<String, Double>> {
        val tabelaResultados = mutableListOf<Pair<String, Double>>()

        for (i in 1..periodo) {
            val taxaDecimal = taxaJuros / 100
            val montante =
                valorInicial * (1 + taxaDecimal).pow(i) + aporteMensal * ((1 + taxaDecimal).pow(i) - 1) / taxaDecimal
            tabelaResultados.add(Pair("Mês $i", montante))
        }

        return tabelaResultados
    }
}