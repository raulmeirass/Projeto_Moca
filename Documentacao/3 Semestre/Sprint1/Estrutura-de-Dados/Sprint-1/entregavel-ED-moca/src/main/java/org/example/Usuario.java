package org.example;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nome;
    private String email;
    private String senha;
    private List<Cartao> cartoes;
    private List<Cofrinho> cofrinhos;

    public Usuario(String nome, String email, String senha, List<Cartao> cartoes, List<Cofrinho> cofrinhos) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cartoes = new ArrayList<>();
        this.cofrinhos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Cartao> getCartoes() {
        return cartoes;
    }

    public void setCartoes(List<Cartao> cartoes) {
        this.cartoes = cartoes;
    }

    public List<Cofrinho> getCofrinhos() {
        return cofrinhos;
    }

    public void setCofrinhos(List<Cofrinho> cofrinhos) {
        this.cofrinhos = cofrinhos;
    }
}
