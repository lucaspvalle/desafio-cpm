package org.desafio.ModeloMatematico.restricoes;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPSolver;
import org.desafio.ModeloDeDados.Voluntario;
import org.desafio.ModeloMatematico.variaveis.LiberaPeriodoParaMesmaEscala;

import java.util.ArrayList;

/**
 * Voluntários que tenham algum grau de parentesco ou contato não deverão obrigatoriamente estar alocados na mesma turma.
 * Entretanto, precisam estar alocados no mesmo período.
 *
 * <p>Para cada VOLUNTARIO e VOLUNTARIO_NA_ESCALA, temos:</p>
 * <p>sum(PERIODO, v_LiberaPeriodoParaMesmaEscala(VOLUNTARIO, VOLUNTARIO_NA_ESCALA, PERIODO)) <= 1</p>
 * */
public class ControlaPeriodoSugeridoParaEscala {
    public ControlaPeriodoSugeridoParaEscala(
            MPSolver solver,
            Voluntario voluntario,
            Voluntario voluntarioNaMesmaEscala,
            ArrayList<LiberaPeriodoParaMesmaEscala> liberaPeriodo
    ) {
        MPConstraint constr = solver.makeConstraint(
                0, 1, "c_PermiteApenasUmPeriodoParaMesmaEscala{"
                        + voluntario
                        + ", " + voluntarioNaMesmaEscala
                        + "}");

        liberaPeriodo.forEach(periodo -> constr.setCoefficient(periodo.variavel, 1));
    }
}
