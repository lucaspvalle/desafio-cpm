package org.desafio.ModeloMatematico.restricoes;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import org.desafio.ModeloDeDados.Turma;
import org.desafio.ModeloDeDados.Voluntario;
import org.desafio.ModeloMatematico.Variaveis;
import org.desafio.ModeloMatematico.variaveis.AlocacaoDeVoluntarioNaTurma;

import java.util.ArrayList;

/**
 * Restrição para que haja uma proporção o mais igualitária possível de calouros e veteranos alocados em uma mesma turma.
 * Para cada TURMA, temos:
 *
 * <p>sum(VOLUNTARIO_VETERANO, v_Alocacao(VOLUNTARIO_VETERANO, TURMA))</p>
 * <p>- sum(VOLUNTARIO_CALOURO, v_Alocacao(VOLUNTARIO_CALOURO, TURMA))</p>
 * <p>=</p>
 * <p>v_FolgaMaisVeteranosAlocadosNaTurma(TURMA) - v_FolgaMaisCalourosAlocadasNaTurma(TURMA)</p>
 *
 * <p>Como penalizamos as variáveis de folga na função objetivo, queremos que elas sempre assumam o menor valor
 * possível (isto é, 0). Portanto, para que isso seja verdadeiro, o número de veteranos e calouros deve ser o mais
 * próximo possível.</p>
 */
public class ProporcaoDeExperienciaEmTurmas {
    private int calculaCoeficienteDoVoluntarioPelaExperiencia(Voluntario voluntario) {
        if (voluntario.isVeterano()) {
            return 1;
        } else {
            return -1;
        }
    }

    public ProporcaoDeExperienciaEmTurmas(MPSolver solver, Variaveis variaveis, Turma turma, ArrayList<AlocacaoDeVoluntarioNaTurma> alocacoesNaTurma) {
        MPConstraint cCalculaProporcaoDeExperienciaNaTurma = solver.makeConstraint(
                0, 0,"c_CalculaProporcaoDeExperienciaNaTurma{" + turma + "}");

        alocacoesNaTurma.forEach(alocacao ->
                cCalculaProporcaoDeExperienciaNaTurma.setCoefficient(
                        alocacao.variavel, calculaCoeficienteDoVoluntarioPelaExperiencia(alocacao.voluntario)));

        MPVariable vFolgaMaisCalourosAlocadosNaTurma = variaveis.makeNumVar(
                0, alocacoesNaTurma.size(), "v_FolgaMaisCalourosAlocadosNaTurma{" + turma + "}", -1.0);
        MPVariable vFolgaMaisVeteranosAlocadasNaTurma = variaveis.makeNumVar(
                0, alocacoesNaTurma.size(), "v_FolgaMaisVeteranosAlocadasNaTurma{" + turma + "}", -1.0);

        cCalculaProporcaoDeExperienciaNaTurma.setCoefficient(vFolgaMaisCalourosAlocadosNaTurma, 1);
        cCalculaProporcaoDeExperienciaNaTurma.setCoefficient(vFolgaMaisVeteranosAlocadasNaTurma, -1);
    }
}