package com.andre.TabelaFipe.models;

public class Dados {
    private String nome;
    private String codigo;

    // Getters e Setters

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "{codigo=" + codigo + ", nome=" + nome + "}";
    }
}
