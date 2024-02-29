package org.desafio.ModeloDeDados;

import org.desafio.ModeloDeDados.enums.PreferenciaDeFaixaEtaria;
import org.desafio.ModeloDeDados.enums.PreferenciaDePeriodo;

import java.util.ArrayList;

public class Turma {
    String nome;
    PreferenciaDeFaixaEtaria faixaEtaria;
    NivelDeConhecimento nivel;
    PreferenciaDePeriodo periodo;
    Turma turmaSeguinte;

    public Turma(String[] line, ArrayList<String> csvEquivalencias) {
        this.nome = line[0];
        this.faixaEtaria = PreferenciaDeFaixaEtaria.getPreferencia(line[1]);
        this.nivel = new NivelDeConhecimento(line[2], csvEquivalencias);
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
        return "Turma " + nome;
    }
}
