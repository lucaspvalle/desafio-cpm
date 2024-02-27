package org.desafio.ModeloDeDados;

public class NivelDeConhecimento {
    String nome;
    String equivalente;

    public NivelDeConhecimento(String[] line) {
        this.nome = line[0];
    }

    private String getNivel() {
        if (!this.equivalente.isEmpty()) {
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
