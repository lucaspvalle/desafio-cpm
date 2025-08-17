package org.desafio.ModeloMatematico.restricoes;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPSolver;
import org.desafio.ModeloDeDados.Voluntario;
import org.desafio.ModeloDeDados.enums.PreferenciaDePeriodo;
import org.desafio.ModeloMatematico.variaveis.LiberaPeriodoParaMesmaEscala;

import java.util.HashMap;

/**
 * <p>Para cada VOLUNTARIO e VOLUNTARIO_NA_ESCALA, permite que apenas um período seja habilitado.</p>
 *
 * <p>
 *     sum(PERIODO, v_LiberaPeriodoParaMesmaEscala(VOLUNTARIO, VOLUNTARIO_NA_ESCALA, PERIODO)) <= 1
 * </p>
 * */
public class PermiteApenasUmaMesmaEscala {
    public PermiteApenasUmaMesmaEscala(
            MPSolver solver,
            Voluntario voluntario,
            Voluntario voluntarioNaMesmaEscala,
            HashMap<PreferenciaDePeriodo, LiberaPeriodoParaMesmaEscala> periodosLiberados
    ) {
        MPConstraint constr = solver.makeConstraint(
                0, 1, "c_PermiteApenasUmPeriodoParaMesmaEscala{"
                        + voluntario + ", " + voluntarioNaMesmaEscala + "}"
        );
        periodosLiberados.forEach((peri, liberaPeriodo) -> constr.setCoefficient(liberaPeriodo.variavel, 1));
    }
}
