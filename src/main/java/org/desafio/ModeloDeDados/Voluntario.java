package org.desafio.ModeloDeDados;

import org.desafio.ModeloDeDados.enums.Genero;
import org.desafio.ModeloDeDados.enums.PreferenciaDeFaixaEtaria;
import org.desafio.ModeloDeDados.enums.PreferenciaDePeriodo;
import org.desafio.ModeloDeDados.enums.PreferenciaDeProgressaoDeTurma;

import java.util.ArrayList;

public class Voluntario {
    int id;
    Genero genero;
    //String funcao;
    int anoDeInicio;
    Turma turma;
    Double comprometimento;
    NivelDeConhecimento nivel;
    PreferenciaDePeriodo periodo;
    PreferenciaDeProgressaoDeTurma progressao;
    PreferenciaDeFaixaEtaria faixaEtaria;
    ArrayList<Voluntario> voluntariosNaMesmaEscala = new ArrayList<>();

    public Voluntario(
            int id,
            String genero,
            // String funcao,
            int anoDeInicio,
            Turma turma,
            Double comprometimento,
            String nivel,
            String periodo,
            String progressao,
            String faixaEtaria
    ) {
        this.id = id;
        this.genero = Genero.getGenero(genero);
        //this.funcao = funcao;
        this.anoDeInicio = anoDeInicio;
        this.turma = turma;
        this.comprometimento = comprometimento;
        this.nivel = new NivelDeConhecimento(nivel);
        this.periodo = PreferenciaDePeriodo.getPreferencia(periodo);
        this.progressao = PreferenciaDeProgressaoDeTurma.getPreferencia(progressao);
        this.faixaEtaria = PreferenciaDeFaixaEtaria.getPreferencia(faixaEtaria);
    }

    public Integer getId() {
        return id;
    }

    public Turma getTurmaAtual() {
        return turma;
    }

    public Double getComprometimento() {
        return comprometimento;
    }

    public PreferenciaDePeriodo getPeriodo() {
        return periodo;
    }

    public PreferenciaDeProgressaoDeTurma getProgressao() {
        return progressao;
    }

    public PreferenciaDeFaixaEtaria getFaixaEtaria() {
        return faixaEtaria;
    }

    public NivelDeConhecimento getNivel() {
        return nivel;
    }

    public ArrayList<Voluntario> getVoluntariosNaMesmaEscala() {
        return voluntariosNaMesmaEscala;
    }

    public Genero getGenero() {
        return genero;
    }

    public boolean isVeterano() {
        int anoAtual = 2018;  // Year.now().getValue()
        return (anoAtual - anoDeInicio >= 1);
    }

    public void setVoluntariosNaMesmaEscala(Voluntario voluntariosNaMesmaEscala) {
        this.voluntariosNaMesmaEscala.add(voluntariosNaMesmaEscala);
    }

    @Override
    public String toString() {
        return "Vol. " + id;
    }
}
