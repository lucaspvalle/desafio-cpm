package org.desafio.ModeloDeDados;


public class NivelDeConhecimento {
    String nome;
    String equivalente;

    public NivelDeConhecimento(String nome, String equivalente) {
        this.nome = nome;
        this.equivalente = equivalente;
    }

    public NivelDeConhecimento(String nome) {
        this.nome = nome;
    }

    private String getNivel() {
        if (this.equivalente != null) {
            return this.equivalente;
        }
        return this.nome;
    }

    public int getValor() {
        return this.getNivel().charAt(0);
    }

    @Override
    public String toString() {
        return "Nivel " + nome;
    }
}
