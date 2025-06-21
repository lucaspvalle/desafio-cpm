package org.desafio.ModeloMatematico;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPSolver;
import org.desafio.ModeloDeDados.dao.TurmaDao;
import org.desafio.ModeloDeDados.dao.VoluntarioDao;
import org.desafio.ModeloMatematico.variaveis.AlocacaoDeVoluntarioNaTurma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ModeloMatematico {
    MPSolver solver;
    public Variaveis variaveis;

    public void exportModelAsLpFormat() {
        Path path = Paths.get("lpModel.txt");

        try {
            Files.write(path, solver.exportModelAsLpFormat().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void printStatistics(MPSolver.ResultStatus resultStatus) {
        System.out.println();
        System.out.println("Model Statistics");
        System.out.println("Result Status: " + resultStatus);

        if (resultStatus == MPSolver.ResultStatus.OPTIMAL) {
            System.out.println("Objective Function: " + solver.objective().value() + " (" + solver.wallTime() + " ms)");
        }
    }

    private void printSolution() {
        System.out.println();

        String formatter = "%-15s %-15s%n";
        System.out.printf(formatter, "Volunteer", "Class");
        System.out.printf(formatter, "---------", "-----");

        for (AlocacaoDeVoluntarioNaTurma alocacao : this.variaveis.alocacoes.getAlocacoes()) {
            if (alocacao.getSolucao() >= 1) {
                System.out.printf(formatter, alocacao.voluntario.getId(), alocacao.turma.getNome());
            }
        }
    }

    public ModeloMatematico(VoluntarioDao voluntarios, TurmaDao turmas, boolean debug) {
        Loader.loadNativeLibraries();
        this.solver = MPSolver.createSolver("SCIP");

        this.variaveis = new Variaveis(solver, voluntarios, turmas);
        new Restricoes(solver, variaveis, voluntarios, turmas);

        variaveis.adicionarVariaveisNaFuncaoObjetivo();
        solver.objective().setMaximization();

        MPSolver.ResultStatus resultStatus = solver.solve();

        printStatistics(resultStatus);
        printSolution();

        if (debug) {
            exportModelAsLpFormat();
        }
    }
}
