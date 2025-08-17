package org.desafio.ModeloMatematico.restricoes;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPSolver;
import org.desafio.ModeloDeDados.Turma;
import org.desafio.ModeloDeDados.Voluntario;
import org.desafio.ModeloDeDados.enums.PreferenciaDePeriodo;
import org.desafio.ModeloMatematico.variaveis.AlocacaoDeVoluntarioNaTurma;
import org.desafio.ModeloMatematico.variaveis.LiberaPeriodoParaMesmaEscala;

import java.util.HashMap;
import java.util.Map;

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
            HashMap<Turma, AlocacaoDeVoluntarioNaTurma> alocacoesDoVoluntario,
            HashMap<Turma, AlocacaoDeVoluntarioNaTurma> alocacoesDoVoluntarioNaMesmaEscala,
            HashMap<PreferenciaDePeriodo, LiberaPeriodoParaMesmaEscala> periodosLiberados
    ) {
        double inf = Double.POSITIVE_INFINITY;
        for (LiberaPeriodoParaMesmaEscala liberaPeriodo : periodosLiberados.values()) {
            MPConstraint constr = solver.makeConstraint(
                    -inf, 0, "c_ControlaAlocacaoParaPeriodoNaMesmaEscala{"
                            + voluntario + ", " + voluntarioNaMesmaEscala + ", " + liberaPeriodo.periodo + "}"
                    );
            constr.setCoefficient(liberaPeriodo.variavel, -2);

            for (Map.Entry<Turma, AlocacaoDeVoluntarioNaTurma> entrada : alocacoesDoVoluntario.entrySet()) {
                Turma turma = entrada.getKey();
                if (!liberaPeriodo.periodo.equals(turma.getPeriodo())) {
                    continue;
                }

                AlocacaoDeVoluntarioNaTurma alocacao = entrada.getValue();
                constr.setCoefficient(alocacao.variavel, 1);
            }

            for (Map.Entry<Turma, AlocacaoDeVoluntarioNaTurma> entrada : alocacoesDoVoluntarioNaMesmaEscala.entrySet()) {
                Turma turma = entrada.getKey();
                if (!liberaPeriodo.periodo.equals(turma.getPeriodo())) {
                    continue;
                }

                AlocacaoDeVoluntarioNaTurma alocacao = entrada.getValue();
                constr.setCoefficient(alocacao.variavel, 1);
            }
        }
    }
}
