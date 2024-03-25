package org.desafio.ModeloMatematico;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPSolver;
import org.desafio.ModeloDeDados.dao.TurmaDao;
import org.desafio.ModeloDeDados.dao.VoluntarioDao;
import org.desafio.ModeloMatematico.utils.PrintModeloMatematico;

public class ModeloMatematico {
    MPSolver solver;
    MPSolver.ResultStatus resultStatus;

    VoluntarioDao voluntarios;
    TurmaDao turmas;

    public Variaveis variaveis;
    //Restricoes restricoes;

    public String exportModelAsLpFormat() {
        return solver.exportModelAsLpFormat();
    }

    public boolean resultadoEhFactivel() {
        return (resultStatus == MPSolver.ResultStatus.OPTIMAL);
    }

    public double getSolutionValue() {
        return solver.objective().value();
    }

    public long getTempoDeExecucao() {
        return solver.wallTime();
    }

    public ModeloMatematico(VoluntarioDao voluntarios, TurmaDao turmas) {
        System.out.println("Iniciando modelo!");

        this.voluntarios = voluntarios;
        this.turmas = turmas;

        Loader.loadNativeLibraries();
        this.solver = MPSolver.createSolver("SCIP");

        this.variaveis = new Variaveis(solver, voluntarios, turmas);
        new Restricoes(solver, variaveis, voluntarios, turmas);

        variaveis.adicionarVariaveisNaFuncaoObjetivo();
        this.resultStatus = solver.solve();

        new PrintModeloMatematico(this);
        System.out.println("Finalizando modelo!");
    }
}
