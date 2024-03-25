package org.desafio.ModeloDeDados;

import org.desafio.ModeloDeDados.dao.TurmaDao;
import org.desafio.ModeloDeDados.enums.Genero;
import org.desafio.ModeloDeDados.enums.PreferenciaDeFaixaEtaria;
import org.desafio.ModeloDeDados.enums.PreferenciaDePeriodo;
import org.desafio.ModeloDeDados.enums.PreferenciaDeProgressaoDeTurma;

import java.time.Year;
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

    public Voluntario(String[] line, TurmaDao turmaDao) {
        this.id = Integer.parseInt(line[0]);
        this.genero = Genero.getGenero(line[1]);
        //this.funcao = line[2];
        this.anoDeInicio = Integer.parseInt(line[3].split(",")[0]);
        this.turma = turmaDao.getTurmaPorId(line[4]);
        this.comprometimento = Double.parseDouble(line[5].replace(',', '.'));
        this.nivel = new NivelDeConhecimento(line[6]);
        this.periodo = PreferenciaDePeriodo.getPreferencia(line[7]);
        this.progressao = PreferenciaDeProgressaoDeTurma.getPreferencia(line[8]);
        this.faixaEtaria = PreferenciaDeFaixaEtaria.getPreferencia(line[9]);
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
        return (Year.now().getValue() - anoDeInicio >= 1);
    }

    public void setVoluntariosNaMesmaEscala(Voluntario voluntariosNaMesmaEscala) {
        this.voluntariosNaMesmaEscala.add(voluntariosNaMesmaEscala);
    }

    @Override
    public String toString() {
        return "Vol. " + id;
    }
}
