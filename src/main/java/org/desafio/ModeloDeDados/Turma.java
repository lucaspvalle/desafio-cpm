package org.desafio.ModeloDeDados;

import org.desafio.ModeloDeDados.enums.PreferenciaDeFaixaEtaria;
import org.desafio.ModeloDeDados.enums.PreferenciaDePeriodo;

public class Turma {
    String nome;
    PreferenciaDeFaixaEtaria faixaEtaria;
    NivelDeConhecimento nivel;
    PreferenciaDePeriodo periodo;
    Turma turmaSeguinte;

    public Turma(String nome, String faixaEtaria, String nivel, String equivalente, String periodo) {
        this.nome = nome;
        this.faixaEtaria = PreferenciaDeFaixaEtaria.getPreferencia(faixaEtaria);
        this.nivel = new NivelDeConhecimento(nivel, equivalente);
        this.periodo = PreferenciaDePeriodo.getPreferencia(periodo);
    }

    public String getNome() {
        return nome;
    }

    public NivelDeConhecimento getNivel() {
        return nivel;
    }

    public PreferenciaDeFaixaEtaria getFaixaEtaria() {
        return faixaEtaria;
    }

    public PreferenciaDePeriodo getPeriodo() {
        return periodo;
    }

    public Turma getTurmaSeguinte() {
        return turmaSeguinte;
    }

    public void setTurmaSeguinte(Turma turmaSeguinte) {
        this.turmaSeguinte = turmaSeguinte;
    }

    @Override
    public String toString() {
        return "Turma " + nome;
    }
}
