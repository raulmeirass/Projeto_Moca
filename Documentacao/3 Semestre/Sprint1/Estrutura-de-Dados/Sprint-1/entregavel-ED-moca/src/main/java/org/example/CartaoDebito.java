package org.example;

import java.util.List;

public class CartaoDebito extends Cartao{
    public CartaoDebito(int cvv, String numero, String validade, List<Despesa> despesas, List<Receita> receitas) {
        super(cvv, numero, validade, despesas, receitas);
    }

    @Override
    public Double getValorGasto() {
        Double valor = 0.0;
        for (Despesa despesa : getDespesas()){
            valor += despesa.getValor();
        }
        return  valor;
    }
}
