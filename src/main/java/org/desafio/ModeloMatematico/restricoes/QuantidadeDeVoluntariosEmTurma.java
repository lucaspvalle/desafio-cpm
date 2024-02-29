package org.desafio.ModeloMatematico.restricoes;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import org.desafio.ModeloDeDados.Turma;
import org.desafio.ModeloMatematico.variaveis.AlocacaoDeVoluntarioNaTurma;

import java.util.ArrayList;

public class QuantidadeDeVoluntariosEmTurma {
    final int numeroMaximoDeVoluntarios = 8;
    final int numeroMinimoDeVoluntarios = 3;

    public QuantidadeDeVoluntariosEmTurma(MPSolver solver, Turma turma, ArrayList<AlocacaoDeVoluntarioNaTurma> alocacoesNaTurma) {
        if (alocacoesNaTurma.isEmpty()) {
            return;
        }

        double inf = Double.POSITIVE_INFINITY;
        MPConstraint cNumeroMaximo = solver.makeConstraint(-inf, numeroMaximoDeVoluntarios,"c_NumeroMaximo{" + turma + "}");
        MPConstraint cNumeroMinimo = solver.makeConstraint(numeroMinimoDeVoluntarios, inf, "c_NumeroMinimo{" + turma + "}");

        for (AlocacaoDeVoluntarioNaTurma alocacao : alocacoesNaTurma) {
            cNumeroMaximo.setCoefficient(alocacao.variavel, 1);
            cNumeroMinimo.setCoefficient(alocacao.variavel, 1);
        }

        MPVariable variavelDeFolgaMaxima = solver.makeNumVar(0, numeroMaximoDeVoluntarios, "v_FolgaDeNumeroMaximo{" + turma + "}");
        MPVariable variavelDeFolgaMinima = solver.makeNumVar(0, numeroMinimoDeVoluntarios, "v_FolgaDeNumeroMinimo{" + turma + "}");

        cNumeroMaximo.setCoefficient(variavelDeFolgaMaxima, -1);
        cNumeroMinimo.setCoefficient(variavelDeFolgaMinima, 1);
    }
}
