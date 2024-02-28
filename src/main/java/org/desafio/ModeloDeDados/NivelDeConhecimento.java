package org.desafio.ModeloDeDados;

import java.util.ArrayList;
import java.util.Optional;

public class NivelDeConhecimento {
    String nome;
    String equivalente;

    public NivelDeConhecimento(String nome, ArrayList<String> equivalencias) {
        this.nome = nome;

        Optional<String> possuiEquivalente = equivalencias.stream().filter(
                e -> e.split(",")[0].equals(nome)
        ).findFirst();

        possuiEquivalente.ifPresent(s -> this.equivalente = s.split(",")[1]);
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
