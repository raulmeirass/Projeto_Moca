package org.example;

import java.util.ArrayList;
import java.util.List;

public abstract class Cartao implements ICartao{
    private int cvv;
    private String numero;
    private String validade;
    private List<Despesa> despesas;
    private List<Receita> receitas;

    public Cartao(int cvv, String numero, String validade, List<Despesa> despesas, List<Receita> receitas) {
        this.cvv = cvv;
        this.numero = numero;
        this.validade = validade;
        this.despesas = new ArrayList<>();
        this.receitas = new ArrayList<>();
    }

    public int getCvv() {
        return cvv;
    }

    public List<Despesa> getDespesas() {
        return despesas;
    }

    public void setDespesas(List<Despesa> despesas) {
        this.despesas = despesas;
    }

    public List<Receita> getReceitas() {
        return receitas;
    }

    public void setReceitas(List<Receita> receitas) {
        this.receitas = receitas;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }
}
