package org.desafio.ModeloMatematico.variaveis;

import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import org.desafio.ModeloMatematico.dao.AlocacaoDao;

public class FuncaoObjetivo {

    MPObjective objective;

    public MPObjective getObjective() {
        return objective;
    }

    public FuncaoObjetivo(MPSolver solver, AlocacaoDao alocacoes) {

        this.objective = solver.objective();
        for (AlocacaoDeVoluntarioNaTurma alocacao : alocacoes.getAllAlocacoesComDominio()) {
            objective.setCoefficient(alocacao.variavel, alocacao.voluntario.getComprometimento());
        }
        objective.setMaximization();
    }
}
