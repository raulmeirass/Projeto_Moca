package org.example;

import java.util.List;

public class CartaoCredito extends Cartao{
    private double limite;

    public CartaoCredito(int cvv, String numero, String validade, List<Despesa> despesas, List<Receita> receitas, double limite) {
        super(cvv, numero, validade, despesas, receitas);
        this.limite = limite;
    }
    @Override
    public Double getValorGasto() {
        Double valor = 0.0;
        for (Despesa despesa : getDespesas()){
            valor += (despesa.getValor() * 1.08);
        }
        return  valor;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

}
