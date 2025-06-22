package org.desafio.ModeloMatematico.restricoes;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPSolver;
import org.desafio.ModeloDeDados.Turma;
import org.desafio.ModeloDeDados.Voluntario;
import org.desafio.ModeloMatematico.variaveis.AlocacaoDeVoluntarioNaTurma;
import org.desafio.ModeloMatematico.variaveis.FolgaMaisCalourosNaTurma;
import org.desafio.ModeloMatematico.variaveis.FolgaMaisVeteranosNaTurma;

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
    private int calculaCoeficientePelaExperiencia(Voluntario voluntario) {
        if (voluntario.isVeterano()) {
            return 1;
        } else {
            return -1;
        }
    }

    public ProporcaoDeExperienciaEmTurmas(
            MPSolver solver,
            Turma turma,
            ArrayList<AlocacaoDeVoluntarioNaTurma> alocacoesNaTurma,
            FolgaMaisCalourosNaTurma folgaCalouros,
            FolgaMaisVeteranosNaTurma folgaVeteranos
    ) {
        MPConstraint constr = solver.makeConstraint(
                0, 0,"c_CalculaProporcaoDeExperienciaNaTurma{" + turma + "}");

        alocacoesNaTurma.forEach(alocacao ->
                constr.setCoefficient(alocacao.variavel, calculaCoeficientePelaExperiencia(alocacao.voluntario)));

        constr.setCoefficient(folgaCalouros.variavel, 1);
        constr.setCoefficient(folgaVeteranos.variavel, -1);
    }
}