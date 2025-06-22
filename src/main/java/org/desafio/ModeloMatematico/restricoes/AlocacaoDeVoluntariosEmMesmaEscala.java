package org.desafio.ModeloMatematico.restricoes;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPSolver;
import org.desafio.ModeloDeDados.Voluntario;
import org.desafio.ModeloMatematico.variaveis.AlocacaoDeVoluntarioNaTurma;
import org.desafio.ModeloMatematico.variaveis.LiberaPeriodoParaMesmaEscala;

import java.util.ArrayList;

/**
 * Se o período for permitido, ambos os voluntários podem ser alocados nas turmas deste período.
 * Caso contrário, a alocação deve ser proibida se a turma for em outro período.</p>
 *
 * <p>Para isso, há uma restrição de big M. Como estamos tratando sempre de voluntários na mesma escala em duplas
 * e é permitido apenas uma alocação para cada voluntário, o nosso M tem valor igual a 2.
 * Então, para cada VOLUNTARIO, VOLUNTARIO_NA_ESCALA e PERIODO, temos:</p>
 *
 * <p>
 *     sum(TURMA_DO_PERIODO,
 *          v_Alocacao(VOLUNTARIO, TURMA_DO_PERIODO)
 *          + v_Alocacao(VOLUNTARIO_NA_ESCALA, TURMA_DO_PERIODO))
 * </p>
 * <p><= 2 * v_LiberaPeriodoParaMesmaEscala(VOLUNTARIO, VOLUNTARIO_NA_ESCALA, PERIODO)</p>
 * */
public class AlocacaoDeVoluntariosEmMesmaEscala {
    public AlocacaoDeVoluntariosEmMesmaEscala(
            MPSolver solver,
            Voluntario voluntario,
            Voluntario voluntarioNaMesmaEscala,
            ArrayList<AlocacaoDeVoluntarioNaTurma> alocacoesDoVoluntario,
            ArrayList<AlocacaoDeVoluntarioNaTurma> alocacoesDoVoluntarioNaMesmaEscala,
            ArrayList<LiberaPeriodoParaMesmaEscala> periodosLiberados
    ) {
        double inf = Double.POSITIVE_INFINITY;
        for (LiberaPeriodoParaMesmaEscala liberaPeriodo : periodosLiberados) {
            MPConstraint constr = solver.makeConstraint(
                    -inf, 0, "c_ControlaAlocacaoParaPeriodoNaMesmaEscala{"
                            + voluntario + ", " + voluntarioNaMesmaEscala + ", " + liberaPeriodo.periodo + "}"
                    );
            constr.setCoefficient(liberaPeriodo.variavel, -2);
        }

        // TODO: pegar as turmas do período
        // alocacoesDoVoluntario.forEach(alocacao -> adicionaVariavelDeAlocacaoNaRestricaoDeControle(solver, alocacao));
        // alocacoesDoVoluntarioNaMesmaEscala.forEach(alocacao -> adicionaVariavelDeAlocacaoNaRestricaoDeControle(solver, alocacao));
    }
}
