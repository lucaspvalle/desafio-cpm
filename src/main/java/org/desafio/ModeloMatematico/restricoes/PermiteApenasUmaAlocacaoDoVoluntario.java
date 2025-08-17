package org.desafio.ModeloMatematico.restricoes;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPSolver;
import org.desafio.ModeloDeDados.Turma;
import org.desafio.ModeloDeDados.Voluntario;
import org.desafio.ModeloMatematico.variaveis.AlocacaoDeVoluntarioNaTurma;

import java.util.HashMap;

/**
 * Restrição para que o voluntário só possa ser alocado em, no máximo, uma turma.
 * Para cada VOLUNTARIO, temos:
 *
 * <p>sum(TURMA, v_Alocacao(VOLUNTARIO, TURMA)) <= 1</p>
 */
public class PermiteApenasUmaAlocacaoDoVoluntario {
    public PermiteApenasUmaAlocacaoDoVoluntario(
            MPSolver solver,
            Voluntario voluntario,
            HashMap<Turma, AlocacaoDeVoluntarioNaTurma> possiveisAlocacoesDoVoluntario
    ) {
        MPConstraint constr = solver.makeConstraint(0, 1, "c_UnicaAlocacao{" + voluntario + "}");
        possiveisAlocacoesDoVoluntario.forEach((turma, alocacao) ->
                constr.setCoefficient(alocacao.variavel, 1));
    }
}