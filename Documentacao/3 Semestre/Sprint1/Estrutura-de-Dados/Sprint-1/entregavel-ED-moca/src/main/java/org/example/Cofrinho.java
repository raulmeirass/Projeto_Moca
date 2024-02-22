package org.example;

public class Cofrinho {
    private double valorFinal;
    private double valorAtual;
    private String descricao;
    private boolean isConcluido;

    public Cofrinho(double valorFinal, double valorAtual, String descricao, boolean isConcluido) {
        this.valorFinal = valorFinal;
        this.valorAtual = valorAtual;
        this.descricao = descricao;
        this.isConcluido = isConcluido;
    }

    public double getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(double valorFinal) {
        this.valorFinal = valorFinal;
    }

    public double getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(double valorAtual) {
        this.valorAtual = valorAtual;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isConcluido() {
        return isConcluido;
    }

    public void setConcluido(boolean concluido) {
        isConcluido = concluido;
    }
}
