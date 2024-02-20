package org.desafio.ModeloMatematico.variaveis;

import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;

import org.desafio.ModeloDeDados.*;
import org.desafio.ModeloDeDados.enums.PreferenciaDeFaixaEtaria;
import org.desafio.ModeloDeDados.enums.PreferenciaDePeriodo;
import org.desafio.ModeloDeDados.enums.PreferenciaDeProgressaoDeTurma;

public class AlocacaoDeVoluntarioNaTurma {
    Voluntario voluntario;
    Turma turma;
    Boolean dominio;
    MPVariable variavel;

    private boolean voluntarioTemNivelParaTurma() {
        return (turma.getNivel().getValor() >= voluntario.getNivel().getValor());
    }

    private boolean voluntarioTemPreferenciaPelaFaixaEtariaDaTurma() {
        return (voluntario.getFaixaEtaria() == PreferenciaDeFaixaEtaria.FAIXA_INDIFERENTE)
                | (voluntario.getFaixaEtaria() == turma.getFaixaEtaria());
    }

    private boolean voluntarioTemPreferenciaPeloPeriodoDaTurma() {
        return (voluntario.getPeriodo() == PreferenciaDePeriodo.PERIODO_INDIFERENTE)
                | (voluntario.getPeriodo() == turma.getPeriodo());
    }

    private boolean voluntarioTemPreferenciaDeTurma() {
        if ((voluntario.getProgressao() == PreferenciaDeProgressaoDeTurma.PROGRESSAO_INDIFERENTE)) {
            return true;
        }

        Turma turmaAtualDoVoluntario = voluntario.getTurma();
        Turma turmaSeguinteDoVoluntario;

        if (voluntario.getProgressao() == PreferenciaDeProgressaoDeTurma.ACOMPANHAR) {
            turmaSeguinteDoVoluntario = turmaAtualDoVoluntario.getTurmaSeguinte();
        } else {
            turmaSeguinteDoVoluntario = turmaAtualDoVoluntario;
        }

        return (turma == turmaSeguinteDoVoluntario);
    }

    public AlocacaoDeVoluntarioNaTurma(MPSolver solver, Voluntario voluntario, Turma turma) {
        this.voluntario = voluntario;
        this.turma = turma;

        this.dominio = (
                //voluntarioTemNivelParaTurma()
                voluntarioTemPreferenciaPeloPeriodoDaTurma()
                & voluntarioTemPreferenciaPelaFaixaEtariaDaTurma()
                & voluntarioTemPreferenciaDeTurma());

        if (!this.dominio) {
            return;
        }

        this.variavel = solver.makeBoolVar(
                "v_alocacao{" +
                        this.voluntario.toString() +
                        ", " +
                        this.turma.toString() +
                        "}");
    }
}
