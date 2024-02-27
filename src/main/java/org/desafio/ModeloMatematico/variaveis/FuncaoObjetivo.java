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
        for (AlocacaoDeVoluntarioNaTurma alocacao : alocacoes.getAllAlocacoes()) {
            objective.setCoefficient(alocacao.variavel, 1);
        }
        objective.setMaximization();
    }
}
