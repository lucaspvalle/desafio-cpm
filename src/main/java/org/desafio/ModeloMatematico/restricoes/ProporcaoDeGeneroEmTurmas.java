package org.desafio.ModeloMatematico.restricoes;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import org.desafio.ModeloDeDados.Turma;
import org.desafio.ModeloDeDados.Voluntario;
import org.desafio.ModeloDeDados.enums.Genero;
import org.desafio.ModeloMatematico.variaveis.AlocacaoDeVoluntarioNaTurma;

import java.util.ArrayList;

/**
 * Restri��o para que haja uma propor��o o mais igualit�ria poss�vel de homens e mulheres alocados em uma mesma turma.
 * Para cada TURMA, temos:
 *
 * <p>sum(VOLUNTARIO_HOMEM, v_Alocacao(VOLUNTARIO_HOMEM, TURMA))</p>
 * <p>- sum(VOLUNTARIO_MULHER, v_Alocacao(VOLUNTARIO_MULHER, TURMA))</p>
 * <p>=</p>
 * <p>v_FolgaMaisHomensAlocadosNaTurma(TURMA) - v_FolgaMaisMulheresAlocadasNaTurma(TURMA)</p>
 *
 * <p>Como penalizamos as vari�veis de folga na fun��o objetivo, queremos que elas sempre assumam o menor valor
 * poss�vel (isto �, 0). Portanto, para que isso seja verdadeiro, o n�mero de homens e mulheres deve ser o mais
 * pr�ximo poss�vel.</p>
 */
public class ProporcaoDeGeneroEmTurmas {
    private int calculaCoeficienteDoVoluntarioPeloGenero(Voluntario voluntario) {
        if (voluntario.getGenero() == Genero.MASCULINO) {
            return 1;
        } else { //Genero.FEMININO
            return -1;
        }
    }

    public ProporcaoDeGeneroEmTurmas(MPSolver solver, Turma turma, ArrayList<AlocacaoDeVoluntarioNaTurma> alocacoesNaTurma) {
        MPConstraint cCalculaProporcaoDeGeneroNaTurma = solver.makeConstraint(
                0, 0,"c_CalculaProporcaoDeGeneroNaTurma{" + turma + "}");

        alocacoesNaTurma.forEach(alocacao ->
                cCalculaProporcaoDeGeneroNaTurma.setCoefficient(
                        alocacao.variavel, calculaCoeficienteDoVoluntarioPeloGenero(alocacao.voluntario)));

        MPVariable vFolgaMaisHomensAlocadosNaTurma = solver.makeNumVar(
                0, alocacoesNaTurma.size(), "v_FolgaMaisHomensAlocadosNaTurma{" + turma + "}");
        MPVariable vFolgaMaisMulheresAlocadasNaTurma = solver.makeNumVar(
                0, alocacoesNaTurma.size(), "v_FolgaMaisMulheresAlocadasNaTurma{" + turma + "}");

        cCalculaProporcaoDeGeneroNaTurma.setCoefficient(vFolgaMaisHomensAlocadosNaTurma, -1);
        cCalculaProporcaoDeGeneroNaTurma.setCoefficient(vFolgaMaisMulheresAlocadasNaTurma, 1);
    }
}
