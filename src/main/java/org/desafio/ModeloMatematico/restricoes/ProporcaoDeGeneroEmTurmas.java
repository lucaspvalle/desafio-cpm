package org.desafio.ModeloMatematico.restricoes;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPSolver;
import org.desafio.ModeloDeDados.Turma;
import org.desafio.ModeloDeDados.Voluntario;
import org.desafio.ModeloDeDados.enums.Genero;
import org.desafio.ModeloMatematico.variaveis.AlocacaoDeVoluntarioNaTurma;
import org.desafio.ModeloMatematico.variaveis.FolgaMaisHomensNaTurma;
import org.desafio.ModeloMatematico.variaveis.FolgaMaisMulheresNaTurma;

import java.util.HashMap;

/**
 * Restrição para que haja uma proporção o mais igualitária possível de homens e mulheres alocados em uma mesma turma.
 * Para cada TURMA, temos:
 *
 * <p>sum(VOLUNTARIO_HOMEM, v_Alocacao(VOLUNTARIO_HOMEM, TURMA))</p>
 * <p>- sum(VOLUNTARIO_MULHER, v_Alocacao(VOLUNTARIO_MULHER, TURMA))</p>
 * <p>=</p>
 * <p>v_FolgaMaisHomensAlocadosNaTurma(TURMA) - v_FolgaMaisMulheresAlocadasNaTurma(TURMA)</p>
 *
 * <p>Como penalizamos as variáveis de folga na função objetivo, queremos que elas sempre assumam o menor valor
 * possível (isto é, 0). Portanto, para que isso seja verdadeiro, o número de homens e mulheres deve ser o mais
 * próximo possível.</p>
 */
public class ProporcaoDeGeneroEmTurmas {
    private int calculaCoeficientPeloGenero(Voluntario voluntario) {
        if (voluntario.getGenero() == Genero.MASCULINO) return 1; else return -1;
    }

    public ProporcaoDeGeneroEmTurmas(
            MPSolver solver,
            Turma turma,
            HashMap<Voluntario, AlocacaoDeVoluntarioNaTurma> alocacoesNaTurma,
            FolgaMaisHomensNaTurma folgaHomens,
            FolgaMaisMulheresNaTurma folgaMulheres
    ) {
        MPConstraint constr = solver.makeConstraint(
                0, 0,"c_CalculaProporcaoDeGeneroNaTurma{" + turma + "}");

        alocacoesNaTurma.forEach((vol, alocacao) ->
                constr.setCoefficient(alocacao.variavel, calculaCoeficientPeloGenero(vol)));

        constr.setCoefficient(folgaHomens.variavel, -1);
        constr.setCoefficient(folgaMulheres.variavel, 1);
    }
}
