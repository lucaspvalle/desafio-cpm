package org.desafio.ModeloMatematico.restricoes;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPSolver;
import org.desafio.ModeloDeDados.Turma;
import org.desafio.ModeloMatematico.variaveis.AlocacaoDeVoluntarioNaTurma;
import org.desafio.ModeloMatematico.variaveis.FolgaMaximoDeVoluntariosNaTurma;
import org.desafio.ModeloMatematico.variaveis.FolgaMinimoDeVoluntariosNaTurma;
import org.desafio.Parametros;

import java.util.ArrayList;

/**
 * Restrição para limitar a quantidade de voluntários alocados em uma turma, conforme a capacidade ideal.
 * Para cada TURMA, temos:
 *
 * <p>sum(VOLUNTARIO, v_Alocacao(VOLUNTARIO, TURMA))
 * - v_FolgaDeNumeroMaximo(TURMA)
 * <p><= numeroMaximoDeVoluntarios</p>
 *
 * <p>Assim como também temos a restrição similar para a quantidade mínima de voluntários:</p>
 * <p>sum(VOLUNTARIO, v_Alocacao(VOLUNTARIO, TURMA))
 * + v_FolgaDeNumeroMinimo(TURMA)
 * <p>>=  numeroMinimoDeVoluntarios</p>
 */
public class QuantidadeDeVoluntariosEmTurma {
    public QuantidadeDeVoluntariosEmTurma(
            MPSolver solver,
            Turma turma,
            ArrayList<AlocacaoDeVoluntarioNaTurma> alocacoesNaTurma,
            FolgaMaximoDeVoluntariosNaTurma folgaMaxima,
            FolgaMinimoDeVoluntariosNaTurma folgaMinima
    ) {
        double inf = Double.POSITIVE_INFINITY;
        MPConstraint cNumeroMaximo = solver.makeConstraint(
                -inf,
                Parametros.NUM_MAXIMO_VOLUNTARIOS,
                "c_NumeroMaximo{" + turma + "}");
        MPConstraint cNumeroMinimo = solver.makeConstraint(
                Parametros.NUM_MINIMO_VOLUNTARIOS,
                inf,
                "c_NumeroMinimo{" + turma + "}");

        for (AlocacaoDeVoluntarioNaTurma alocacao : alocacoesNaTurma) {
            cNumeroMaximo.setCoefficient(alocacao.variavel, 1);
            cNumeroMinimo.setCoefficient(alocacao.variavel, 1);
        }

        cNumeroMaximo.setCoefficient(folgaMaxima.variavel, -1);
        cNumeroMinimo.setCoefficient(folgaMinima.variavel, 1);
    }
}
