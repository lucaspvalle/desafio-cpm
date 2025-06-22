package org.desafio.ModeloMatematico.variaveis;

import com.google.ortools.linearsolver.MPSolver;
import org.desafio.ModeloDeDados.Turma;
import org.desafio.ModeloDeDados.Voluntario;
import org.desafio.ModeloDeDados.enums.PreferenciaDeFaixaEtaria;
import org.desafio.ModeloDeDados.enums.PreferenciaDePeriodo;
import org.desafio.ModeloDeDados.enums.PreferenciaDeProgressaoDeTurma;

public class AlocacaoDeVoluntarioNaTurma extends Variavel {
    public Voluntario voluntario;
    public Turma turma;

    public boolean dominio;
    public Double peso;

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
        PreferenciaDeProgressaoDeTurma preferenciaDoVoluntario = this.voluntario.getProgressao();
        Turma turmaAtualDoVoluntario = this.voluntario.getTurmaAtual();

        if (preferenciaDoVoluntario == PreferenciaDeProgressaoDeTurma.PROGRESSAO_INDIFERENTE) {
            return true;
        }

        if (preferenciaDoVoluntario == PreferenciaDeProgressaoDeTurma.PERMANECER) {
            return this.turma.equals(turmaAtualDoVoluntario);
        }

        if (this.voluntario.getProgressao() == PreferenciaDeProgressaoDeTurma.ACOMPANHAR) {
            return this.turma.equals(turmaAtualDoVoluntario.getTurmaSeguinte());
        }

        return false;
    }

    public AlocacaoDeVoluntarioNaTurma(MPSolver solver, Voluntario voluntario, Turma turma) {
        this.voluntario = voluntario;
        this.turma = turma;

        this.dominio = (
                voluntarioTemNivelParaTurma()
                        & voluntarioTemPreferenciaPeloPeriodoDaTurma()
                        & voluntarioTemPreferenciaPelaFaixaEtariaDaTurma()
                        & voluntarioTemPreferenciaDeTurma());

        this.peso = this.voluntario.getComprometimento();
        if (this.dominio) {
            this.variavel = makeBoolVar(solver, toString(), this.peso);
        }
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
