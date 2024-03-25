package org.desafio.ModeloMatematico.restricoes;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import org.desafio.ModeloDeDados.Turma;
import org.desafio.ModeloMatematico.variaveis.AlocacaoDeVoluntarioNaTurma;

import java.util.ArrayList;

/**
 * Restrição para limitar a quantidade de voluntários alocados em uma turma, conforme a capacidade ideal.
 * Para cada TURMA, temos:
 *
 * <p>sum(VOLUNTARIO, v_Alocacao(VOLUNTARIO, TURMA))
 * <p><= numeroMaximoDeVoluntarios - v_FolgaDeNumeroMaximo(TURMA)</p>
 *
 * <p>Assim como também temos a restrição similar para a quantidade mínima de voluntários:</p>
 * <p>sum(VOLUNTARIO, v_Alocacao(VOLUNTARIO, TURMA))
 * <p>>=  numeroMinimoDeVoluntarios - v_FolgaDeNumeroMinimo(TURMA)</p>
 */
public class QuantidadeDeVoluntariosEmTurma {
    final int numeroMaximoDeVoluntarios = 8;
    final int numeroMinimoDeVoluntarios = 3;

    public QuantidadeDeVoluntariosEmTurma(MPSolver solver, Turma turma, ArrayList<AlocacaoDeVoluntarioNaTurma> alocacoesNaTurma) {
        double inf = Double.POSITIVE_INFINITY;
        MPConstraint cNumeroMaximo = solver.makeConstraint(-inf, numeroMaximoDeVoluntarios,"c_NumeroMaximo{" + turma + "}");
        MPConstraint cNumeroMinimo = solver.makeConstraint(numeroMinimoDeVoluntarios, inf, "c_NumeroMinimo{" + turma + "}");

        for (AlocacaoDeVoluntarioNaTurma alocacao : alocacoesNaTurma) {
            cNumeroMaximo.setCoefficient(alocacao.variavel, 1);
            cNumeroMinimo.setCoefficient(alocacao.variavel, 1);
        }

        MPVariable vFolgaMaxima = solver.makeNumVar(0, numeroMaximoDeVoluntarios, "v_FolgaDeNumeroMaximo{" + turma + "}");
        MPVariable vFolgaMinima = solver.makeNumVar(0, numeroMinimoDeVoluntarios, "v_FolgaDeNumeroMinimo{" + turma + "}");

        cNumeroMaximo.setCoefficient(vFolgaMaxima, -1);
        cNumeroMinimo.setCoefficient(vFolgaMinima, 1);
    }
}
