package org.example;

import java.util.Date;

public class Despesa {
    private String descricao;
    private Date data;
    private String categoria;
    private Double valor;
    private int parcelas;
    private boolean isFixo;

    public Despesa(String descricao, Date data, String categoria, Double valor, int parcelas, boolean isFixo) {
        this.descricao = descricao;
        this.data = data;
        this.categoria = categoria;
        this.valor = valor;
        this.parcelas = parcelas;
        this.isFixo = isFixo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public int getParcelas() {
        return parcelas;
    }

    public void setParcelas(int parcelas) {
        this.parcelas = parcelas;
    }

    public boolean isFixo() {
        return isFixo;
    }

    public void setFixo(boolean fixo) {
        isFixo = fixo;
    }
}
