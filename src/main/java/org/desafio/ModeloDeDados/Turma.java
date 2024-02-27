package org.desafio.ModeloDeDados;

import org.desafio.ModeloDeDados.enums.PreferenciaDeFaixaEtaria;
import org.desafio.ModeloDeDados.enums.PreferenciaDePeriodo;

public class Turma {
    String nome;
    PreferenciaDeFaixaEtaria faixaEtaria;
    NivelDeConhecimento nivel;
    PreferenciaDePeriodo periodo;
    Turma turmaSeguinte;

    public Turma(String[] line) {
        this.nome = line[0];
        this.faixaEtaria = PreferenciaDeFaixaEtaria.getPreferencia(line[1]);
        //this.nivel
        this.periodo = PreferenciaDePeriodo.getPreferencia(line[3]);
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
        return "turma_" + nome;
    }
}
