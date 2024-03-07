package org.desafio.ModeloMatematico.variaveis;

import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import org.desafio.ModeloDeDados.Turma;
import org.desafio.ModeloDeDados.Voluntario;
import org.desafio.ModeloDeDados.enums.PreferenciaDeFaixaEtaria;
import org.desafio.ModeloDeDados.enums.PreferenciaDePeriodo;
import org.desafio.ModeloDeDados.enums.PreferenciaDeProgressaoDeTurma;

public class AlocacaoDeVoluntarioNaTurma {
    public Voluntario voluntario;
    public Turma turma;
    public Boolean dominio;
    public MPVariable variavel;

    private boolean voluntarioTemNivelParaTurma() {
        return (this.voluntario.getNivel().getValor() >= this.turma.getNivel().getValor());
    }

    private boolean voluntarioTemPreferenciaPelaFaixaEtariaDaTurma() {
        return (this.voluntario.getFaixaEtaria() == PreferenciaDeFaixaEtaria.FAIXA_INDIFERENTE)
                | (this.voluntario.getFaixaEtaria() == this.turma.getFaixaEtaria());
    }

    private boolean voluntarioTemPreferenciaPeloPeriodoDaTurma() {
        return (this.voluntario.getPeriodo() == PreferenciaDePeriodo.PERIODO_INDIFERENTE)
                | (this.voluntario.getPeriodo() == this.turma.getPeriodo());
    }

    private boolean voluntarioTemPreferenciaDeTurma() {
        if ((this.voluntario.getProgressao() == PreferenciaDeProgressaoDeTurma.PROGRESSAO_INDIFERENTE)) {
            return true;
        }

        Turma turmaAtualDoVoluntario = this.voluntario.getTurmaAtual();
        Turma turmaSeguinteDoVoluntario;

        if (this.voluntario.getProgressao() == PreferenciaDeProgressaoDeTurma.ACOMPANHAR) {
            turmaSeguinteDoVoluntario = turmaAtualDoVoluntario.getTurmaSeguinte();
        } else { //PreferenciaDeProgressaoDeTurma.PERMANECER
            turmaSeguinteDoVoluntario = turmaAtualDoVoluntario;
        }

        return (this.turma == turmaSeguinteDoVoluntario);
    }

    public AlocacaoDeVoluntarioNaTurma(MPSolver solver, Voluntario voluntario, Turma turma) {
        this.voluntario = voluntario;
        this.turma = turma;

        this.dominio = (
                voluntarioTemNivelParaTurma()
                & voluntarioTemPreferenciaPeloPeriodoDaTurma()
                & voluntarioTemPreferenciaPelaFaixaEtariaDaTurma()
                & voluntarioTemPreferenciaDeTurma());

        if (!this.dominio) {
            return;
        }

        this.variavel = solver.makeBoolVar(toString());
    }

    @Override
    public String toString() {
        return "v_AlocacaoDeVoluntarioNaTurma{" +
                voluntario.toString() +
                ", "
                + turma.toString() +
                "}";
    }
}
